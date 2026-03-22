package com.reasonly.backend.Question.CodeWriting;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.reasonly.backend.User.UserLanguage;

// Responsible for executing the a user's code in a Docker container.
@Service
public class CodeExecutionService {

    public CodeExecutionResult executeCode(String userCode, List<String> inputs, List<String> expectedOutputs, String methodName, UserLanguage language) {
        CodeExecutionResult result = new CodeExecutionResult();
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

            // Loop through each test case
            for (int i = 0; i < inputs.size(); i++) {
                String inputJson = inputs.get(i);
                String expectedJson = expectedOutputs.get(i);

                ExecutionOutcome outcome = runDockerContainer(finalUserCode, runnerCode, language, inputJson);
                
                if (!outcome.isSuccess) {
                    result.setSuccess(false);
                    result.setErrorMessage("Error on test case " + (i + 1) + ":\n" + outcome.errorMessage);
                    result.setConsoleOutput(outcome.consoleOutput);
                    result.setTestCasesPassed(passed);
                    return result;
                }

                if (outcome.consoleOutput.trim().equals(expectedJson.trim())) {
                    passed++;
                } else {
                    result.setSuccess(false);
                    result.setErrorMessage("Test case " + (i + 1) + " failed with input " + inputJson + ".\nExpected: " + expectedJson + "\nGot: " + outcome.consoleOutput.trim());
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
        
        boolean finished = process.waitFor(10, TimeUnit.SECONDS);
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

        // Expect the runner to just print the JSON result to stdout.
        return new ExecutionOutcome(true, stdout, stderr);
    }

    // Generates a Runner.java class that uses Jackson (pre-installed in the reasonly-java Docker image)
    // to deserialize JSON input into the exact Java types the user's method expects.
    private String generateJavaRunner(String methodName) {
        return "import java.util.*;\n" +
               "import java.lang.reflect.*;\n" +
               "import com.fasterxml.jackson.databind.*;\n" +
               "import com.fasterxml.jackson.databind.type.*;\n" +
               "\n" +
               "public class Runner {\n" +
               "    public static void main(String[] args) throws Exception {\n" +
               "        // Read the JSON input array from stdin (e.g. \"[2, 3]\" or \"[[1,2,3], 5]\")\n" +
               "        Scanner scanner = new Scanner(System.in);\n" +
               "        StringBuilder sb = new StringBuilder();\n" +
               "        while (scanner.hasNextLine()) sb.append(scanner.nextLine());\n" +
               "        String input = sb.toString().trim();\n" +
               "\n" +
               "        ObjectMapper mapper = new ObjectMapper();\n" +
               "        // Parse the top-level JSON array into a list of raw JsonNodes\n" +
               "        JsonNode rootArray = mapper.readTree(input);\n" +
               "\n" +
               "        // Find the target method on the user's Solution class using reflection\n" +
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
               "        // Use Jackson's TypeFactory to convert each JSON element into the exact Java type\n" +
               "        Type[] genericTypes = target.getGenericParameterTypes();\n" +
               "        TypeFactory tf = mapper.getTypeFactory();\n" +
               "        Object[] methodArgs = new Object[genericTypes.length];\n" +
               "\n" +
               "        for (int i = 0; i < genericTypes.length; i++) {\n" +
               "            JavaType javaType = tf.constructType(genericTypes[i]);\n" +
               "            methodArgs[i] = mapper.convertValue(rootArray.get(i), javaType);\n" +
               "        }\n" +
               "\n" +
               "        // Invoke the method and print the result as JSON\n" +
               "        Object result = target.invoke(sol, methodArgs);\n" +
               "        // For simple types (int, String), just print directly\n" +
               "        if (result instanceof Number || result instanceof Boolean) {\n" +
               "            System.out.print(result);\n" +
               "        } else if (result instanceof String) {\n" +
               "            System.out.print(result);\n" +
               "        } else {\n" +
               "            // For complex types (List, Map, Set, arrays), serialize as JSON\n" +
               "            System.out.print(mapper.writeValueAsString(result));\n" +
               "        }\n" +
               "    }\n" +
               "}\n";
    }

    private String generatePythonRunner(String methodName) {
        return "import sys, json\n" +
               "import solution\n" +
               "if __name__ == '__main__':\n" +
               "    input_data = sys.stdin.read().strip()\n" +
               "    args = json.loads(input_data)\n" +
               "    func = getattr(solution, '" + methodName + "')\n" +
               "    res = func(*args)\n" +
               "    print(json.dumps(res), end='')\n";
    }

    private String generateJavaScriptRunner(String methodName) {
        return "const fs = require('fs');\n" +
               "const solution = require('./solution');\n" +
               "const inputStr = fs.readFileSync(0, 'utf-8').trim();\n" +
               "const args = JSON.parse(inputStr);\n" +
               "const res = solution." + methodName + "(...args);\n" +
               "process.stdout.write(JSON.stringify(res));\n";
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
