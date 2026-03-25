package com.reasonly.backend.Question.CodeWriting;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reasonly.backend.User.UserLanguage;

// Responsible for executing the a user's code in a Docker container.
// All test cases are batched into a single container run for performance.
@Service
public class CodeExecutionService {

    private final ObjectMapper mapper = new ObjectMapper();

    public CodeExecutionResult executeCode(String userCode, List<String> inputs, List<String> expectedOutputs, String methodName, UserLanguage language) {
        CodeExecutionResult result = new CodeExecutionResult();

        if (inputs == null || inputs.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMessage("No test cases found for this question.");
            return result;
        }

        result.setTotalTestCases(inputs.size());
        int passed = 0;

        try {
            String finalUserCode = userCode;
            String runnerCode = "";
            
            if (language == UserLanguage.JAVA) {
                if (!finalUserCode.contains("class ")) {
                    finalUserCode = "public class Solution {\n" + finalUserCode + "\n}";
                }
                runnerCode = generateJavaRunner(methodName);
            } else if (language == UserLanguage.PYTHON) {
                runnerCode = generatePythonRunner(methodName);
            } else if (language == UserLanguage.JAVASCRIPT) {
                runnerCode = generateJavaScriptRunner(methodName);
            } else {
                throw new UnsupportedOperationException("Language " + language + " is not supported yet.");
            }

            // Combine ALL inputs into a single JSON array: [[2,3],[10,-5],[0,0],...]
            StringBuilder allInputs = new StringBuilder("[");
            for (int i = 0; i < inputs.size(); i++) {
                allInputs.append(inputs.get(i));
                if (i < inputs.size() - 1) allInputs.append(",");
            }
            allInputs.append("]");

            // Run a single Docker container for all test cases
            ExecutionOutcome outcome = runDockerContainer(finalUserCode, runnerCode, language, allInputs.toString());
            
            if (!outcome.isSuccess) {
                result.setSuccess(false);
                result.setErrorMessage("Execution Error:\n" + outcome.errorMessage);
                result.setConsoleOutput(outcome.consoleOutput);
                return result;
            }

            // Parse the JSON array of results from the runner
            JsonNode resultsArray = mapper.readTree(outcome.consoleOutput.trim());

            for (int i = 0; i < inputs.size(); i++) {
                String expectedJson = expectedOutputs.get(i).trim();
                JsonNode resultNode = resultsArray.get(i);

                // Check if the runner reported a per-test-case runtime error
                if (resultNode != null && resultNode.isObject() && resultNode.has("error")) {
                    result.setSuccess(false);
                    result.setErrorMessage("Runtime error on test case " + (i + 1) + " with input " + inputs.get(i) + ":\n" + resultNode.get("error").asText());
                    result.setConsoleOutput(outcome.consoleOutput);
                    result.setTestCasesPassed(passed);
                    return result;
                }

                String actualOutput = resultNode != null ? resultNode.asText() : "";
                if (actualOutput.equals(expectedJson)) {
                    passed++;
                } else {
                    result.setSuccess(false);
                    result.setErrorMessage("Test case " + (i + 1) + " failed with input " + inputs.get(i) + ".\nExpected: " + expectedJson + "\nGot: " + actualOutput);
                    result.setConsoleOutput(outcome.consoleOutput);
                    result.setTestCasesPassed(passed);
                    return result;
                }
            }

            result.setSuccess(true);
            result.setTestCasesPassed(passed);
            result.setConsoleOutput("All test cases passed!");
            return result;

        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMessage("System Error: " + e.getMessage());
            return result;
        }
    }

    private ExecutionOutcome runDockerContainer(String userCode, String runnerCode, UserLanguage language, String inputJson) throws IOException, InterruptedException {
        String[] command;

        if (language == UserLanguage.JAVA) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "256m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "reasonly-java",
                "sh", "-c", "printf '%s' \"$SOLUTION_CODE\" > Solution.java && printf '%s' \"$RUNNER_CODE\" > Runner.java && printf '%s' \"$INPUT_JSON\" > input.txt && javac -cp '/libs/*:.' Solution.java Runner.java && java -cp '/libs/*:.' Runner < input.txt"
            };
        } else if (language == UserLanguage.PYTHON) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "128m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "python:3.11-slim",
                "sh", "-c", "printf '%s' \"$SOLUTION_CODE\" > solution.py && printf '%s' \"$RUNNER_CODE\" > runner.py && printf '%s' \"$INPUT_JSON\" > input.txt && python runner.py < input.txt"
            };
        } else if (language == UserLanguage.JAVASCRIPT) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "128m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "node:20-alpine",
                "sh", "-c", "printf '%s' \"$SOLUTION_CODE\" > solution.js && printf '%s' \"$RUNNER_CODE\" > runner.js && printf '%s' \"$INPUT_JSON\" > input.txt && node runner.js < input.txt"
            };
        } else {
            throw new RuntimeException("Unsupported language");
        }

        ProcessBuilder pb = new ProcessBuilder(command);
        Process process = pb.start();
        
        boolean finished = process.waitFor(15, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            return new ExecutionOutcome(false, "", "Execution Timed Out (Possible Infinite Loop)");
        }

        String stdout = new String(process.getInputStream().readAllBytes());
        String stderr = new String(process.getErrorStream().readAllBytes());

        int exitCode = process.exitValue();
        if (exitCode != 0) {
            return new ExecutionOutcome(false, stdout, stderr);
        }

        return new ExecutionOutcome(true, stdout, stderr);
    }

    private String generateJavaRunner(String methodName) {
        return "import java.util.*;\n" +
               "import java.lang.reflect.*;\n" +
               "import com.fasterxml.jackson.databind.*;\n" +
               "import com.fasterxml.jackson.databind.type.*;\n" +
               "import com.fasterxml.jackson.databind.node.*;\n" +
               "\n" +
               "public class Runner {\n" +
               "    public static void main(String[] args) throws Exception {\n" +
               "        Scanner scanner = new Scanner(System.in);\n" +
               "        StringBuilder sb = new StringBuilder();\n" +
               "        while (scanner.hasNextLine()) sb.append(scanner.nextLine());\n" +
               "        String input = sb.toString().trim();\n" +
               "\n" +
               "        ObjectMapper mapper = new ObjectMapper();\n" +
               "        JsonNode allCases = mapper.readTree(input);\n" +
               "\n" +
               "        Solution sol = new Solution();\n" +
               "        Method target = null;\n" +
               "        for (Method m : Solution.class.getDeclaredMethods()) {\n" +
               "            if (m.getName().equals(\"" + methodName + "\")) {\n" +
               "                target = m;\n" +
               "                break;\n" +
               "            }\n" +
               "        }\n" +
               "        if (target == null) throw new RuntimeException(\"Method '" + methodName + "' not found in Solution class\");\n" +
               "\n" +
               "        Type[] genericTypes = target.getGenericParameterTypes();\n" +
               "        TypeFactory tf = mapper.getTypeFactory();\n" +
               "        ArrayNode results = mapper.createArrayNode();\n" +
               "\n" +
               "        for (int i = 0; i < allCases.size(); i++) {\n" +
               "            JsonNode testCase = allCases.get(i);\n" +
               "            try {\n" +
               "                Object[] methodArgs = new Object[genericTypes.length];\n" +
               "                for (int j = 0; j < genericTypes.length; j++) {\n" +
               "                    JavaType javaType = tf.constructType(genericTypes[j]);\n" +
               "                    methodArgs[j] = mapper.convertValue(testCase.get(j), javaType);\n" +
               "                }\n" +
               "                Object result = target.invoke(sol, methodArgs);\n" +
               "                if (result instanceof Number || result instanceof Boolean) {\n" +
               "                    results.add(String.valueOf(result));\n" +
               "                } else if (result instanceof String) {\n" +
               "                    results.add((String) result);\n" +
               "                } else {\n" +
               "                    results.add(mapper.writeValueAsString(result));\n" +
               "                }\n" +
               "            } catch (Exception e) {\n" +
               "                ObjectNode errNode = mapper.createObjectNode();\n" +
               "                String msg = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();\n" +
               "                errNode.put(\"error\", msg != null ? msg : \"Unknown error\");\n" +
               "                results.add(errNode);\n" +
               "            }\n" +
               "        }\n" +
               "        System.out.print(mapper.writeValueAsString(results));\n" +
               "    }\n" +
               "}\n";
    }

    private String generatePythonRunner(String methodName) {
        return "import sys, json\n" +
               "import solution\n" +
               "if __name__ == '__main__':\n" +
               "    input_data = sys.stdin.read().strip()\n" +
               "    all_cases = json.loads(input_data)\n" +
               "    func = getattr(solution, '" + methodName + "')\n" +
               "    results = []\n" +
               "    for case in all_cases:\n" +
               "        try:\n" +
               "            res = func(*case)\n" +
               "            if isinstance(res, (int, float, bool)):\n" +
               "                results.append(str(res))\n" +
               "            elif isinstance(res, str):\n" +
               "                results.append(res)\n" +
               "            else:\n" +
               "                results.append(json.dumps(res))\n" +
               "        except Exception as e:\n" +
               "            results.append({'error': str(e)})\n" +
               "    print(json.dumps(results), end='')\n";
    }

    private String generateJavaScriptRunner(String methodName) {
        return "const fs = require('fs');\n" +
               "const solution = require('./solution');\n" +
               "const inputStr = fs.readFileSync(0, 'utf-8').trim();\n" +
               "const allCases = JSON.parse(inputStr);\n" +
               "const results = [];\n" +
               "for (const testCase of allCases) {\n" +
               "    try {\n" +
               "        const res = solution." + methodName + "(...testCase);\n" +
               "        if (typeof res === 'number' || typeof res === 'boolean') {\n" +
               "            results.push(String(res));\n" +
               "        } else if (typeof res === 'string') {\n" +
               "            results.push(res);\n" +
               "        } else {\n" +
               "            results.push(JSON.stringify(res));\n" +
               "        }\n" +
               "    } catch (e) {\n" +
               "        results.push({error: e.message || 'Unknown error'});\n" +
               "    }\n" +
               "}\n" +
               "process.stdout.write(JSON.stringify(results));\n";
    }

    private static class ExecutionOutcome {
        boolean isSuccess;
        String consoleOutput;
        String errorMessage;

        ExecutionOutcome(boolean isSuccess, String consoleOutput, String errorMessage) {
            this.isSuccess = isSuccess;
            this.consoleOutput = consoleOutput;
            this.errorMessage = errorMessage;
        }
    }
}
