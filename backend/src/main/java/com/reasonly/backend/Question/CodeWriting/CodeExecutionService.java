package com.reasonly.backend.Question.CodeWriting;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.reasonly.backend.User.UserSettings.UserLanguage;

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
            } else if (language == UserLanguage.C_PLUS_PLUS) {
                runnerCode = generateCppRunner(methodName);
            } else if (language == UserLanguage.C_SHARP) {
                runnerCode = generateCSharpRunner(methodName);
            } else if (language == UserLanguage.GO) {
                runnerCode = generateGoRunner(methodName);
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
        } else if (language == UserLanguage.C_PLUS_PLUS) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "256m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "gcc:latest",
                "sh", "-c",
                "printf '%s' \"$SOLUTION_CODE\" > solution.cpp && " +
                "printf '%s' \"$RUNNER_CODE\" > runner.cpp && " +
                "printf '%s' \"$INPUT_JSON\" > input.txt && " +
                "g++ -std=c++17 -O2 -o runner runner.cpp && " +
                "./runner < input.txt"
            };
        } else if (language == UserLanguage.C_SHARP) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "256m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "mcr.microsoft.com/dotnet/sdk:8.0",
                "sh", "-c",
                "mkdir -p /app/proj && " +
                "printf '%s' \"$SOLUTION_CODE\" > /app/proj/Solution.cs && " +
                "printf '%s' \"$RUNNER_CODE\" > /app/proj/Runner.cs && " +
                "printf '%s' \"$INPUT_JSON\" > /app/proj/input.txt && " +
                "dotnet new console -o /app/proj --force --no-restore -f net8.0 > /dev/null 2>&1 && " +
                "cp /app/proj/Solution.cs /app/proj/Solution.cs.bak && cp /app/proj/Runner.cs /app/proj/Runner.cs.bak && " +
                "rm -f /app/proj/Program.cs && " +
                "cp /app/proj/Solution.cs.bak /app/proj/Solution.cs && cp /app/proj/Runner.cs.bak /app/proj/Runner.cs && " +
                "dotnet run --project /app/proj --no-build 2>&1 || " +
                "(dotnet build /app/proj -o /app/proj/out > /dev/null 2>&1 && dotnet /app/proj/out/proj.dll < /app/proj/input.txt)"
            };
        } else if (language == UserLanguage.GO) {
            command = new String[]{
                "docker", "run", "--rm", "--memory", "256m", "--network", "none",
                "-e", "SOLUTION_CODE=" + userCode,
                "-e", "RUNNER_CODE=" + runnerCode,
                "-e", "INPUT_JSON=" + inputJson,
                "-w", "/app", "golang:1.22-alpine",
                "sh", "-c",
                "printf '%s' \"$SOLUTION_CODE\" > solution.go && " +
                "printf '%s' \"$RUNNER_CODE\" > main.go && " +
                "printf '%s' \"$INPUT_JSON\" > input.txt && " +
                "go run solution.go main.go < input.txt"
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

    private String generateCppRunner(String methodName) {
        return "#include <iostream>\n" +
               "#include <string>\n" +
               "#include <vector>\n" +
               "#include <sstream>\n" +
               "#include <tuple>\n" +
               "#include <type_traits>\n" +
               "\n" +
               "// User code included directly\n" +
               "#include \"solution.cpp\"\n" +
               "\n" +
               "// --- Minimal JSON Parser ---\n" +
               "static std::string trim(const std::string& s) {\n" +
               "    auto b = s.find_first_not_of(\" \\t\\r\\n\");\n" +
               "    auto e = s.find_last_not_of(\" \\t\\r\\n\");\n" +
               "    return b == std::string::npos ? \"\" : s.substr(b, e-b+1);\n" +
               "}\n" +
               "\n" +
               "static std::vector<std::string> splitTopLevel(const std::string& s) {\n" +
               "    std::vector<std::string> res;\n" +
               "    int depth=0; bool inStr=false; int start=0;\n" +
               "    for (int i=0; i<(int)s.size(); i++) {\n" +
               "        char c=s[i];\n" +
               "        if (c=='\"' && (i==0||s[i-1]!='\\\\')) inStr=!inStr;\n" +
               "        if (!inStr) { if (c=='['||c=='{'||c=='(') depth++; else if (c==']'||c=='}'||c==')') depth--; }\n" +
               "        if (!inStr && depth==0 && c==',' ) { res.push_back(trim(s.substr(start,i-start))); start=i+1; }\n" +
               "    }\n" +
               "    if (start<(int)s.size()) res.push_back(trim(s.substr(start)));\n" +
               "    return res;\n" +
               "}\n" +
               "\n" +
               "template <typename T> struct ArgParser;\n" +
               "template <> struct ArgParser<int> { static int parse(const std::string& s) { return std::stoi(s); } };\n" +
               "template <> struct ArgParser<bool> { static bool parse(const std::string& s) { return s == \"true\"; } };\n" +
               "template <> struct ArgParser<std::string> { static std::string parse(const std::string& s) { \n" +
               "    if (s.size()>=2 && s.front()=='\"' && s.back()=='\"') return s.substr(1, s.size()-2);\n" +
               "    return s;\n" +
               "} };\n" +
               "template <typename T> struct ArgParser<std::vector<T>> {\n" +
               "    static std::vector<T> parse(const std::string& s) {\n" +
               "        std::vector<T> res;\n" +
               "        std::string inner = trim(s);\n" +
               "        if (inner.size()>=2 && inner.front()=='[') inner = inner.substr(1, inner.size()-2);\n" +
               "        auto parts = splitTopLevel(inner);\n" +
               "        for (auto& p : parts) { if(!p.empty()) res.push_back(ArgParser<T>::parse(p)); }\n" +
               "        return res;\n" +
               "    }\n" +
               "};\n" +
               "\n" +
               "// Format output\n" +
               "template <typename T> std::string toJson(const std::vector<T>& val);\n" +
               "static std::string toJson(int val) { return std::to_string(val); }\n" +
               "static std::string toJson(bool val) { return val ? \"true\" : \"false\"; }\n" +
               "static std::string toJson(const std::string& val) { return \"\\\"\" + val + \"\\\"\"; }\n" +
               "template <typename T> std::string toJson(const std::vector<T>& val) {\n" +
               "    std::string res = \"[\";\n" +
               "    for(size_t i=0; i<val.size(); i++) {\n" +
               "        if(i>0) res+=\",\";\n" +
               "        res += toJson(val[i]);\n" +
               "    }\n" +
               "    return res + \"]\";\n" +
               "}\n" +
               "\n" +
               "// Dispatch magic\n" +
               "template<typename R, typename... Args, std::size_t... Is>\n" +
               "std::string callFunc(R (*f)(Args...), const std::vector<std::string>& argStrs, std::index_sequence<Is...>) {\n" +
               "    if constexpr (std::is_void_v<R>) {\n" +
               "        f(ArgParser<std::decay_t<Args>>::parse(argStrs[Is])...);\n" +
               "        return \"null\";\n" +
               "    } else {\n" +
               "        auto res = f(ArgParser<std::decay_t<Args>>::parse(argStrs[Is])...);\n" +
               "        return toJson(res);\n" +
               "    }\n" +
               "}\n" +
               "\n" +
               "template<typename R, typename... Args>\n" +
               "std::string dispatchWrapper(R (*f)(Args...), const std::string& argsJson) {\n" +
               "    std::string inner = trim(argsJson);\n" +
               "    if(inner.size()>=2 && inner.front()=='[') inner = inner.substr(1,inner.size()-2);\n" +
               "    auto argStrs = splitTopLevel(inner);\n" +
               "    return callFunc(f, argStrs, std::index_sequence_for<Args...>{});\n" +
               "}\n" +
               "\n" +
               "int main() {\n" +
               "    std::string line, all;\n" +
               "    while (std::getline(std::cin, line)) all += line;\n" +
               "    std::string inner = trim(all);\n" +
               "    if(inner.size()>=2 && inner.front()=='[') inner = inner.substr(1,inner.size()-2);\n" +
               "    auto cases = splitTopLevel(inner);\n" +
               "    \n" +
               "    std::cout << '[';\n" +
               "    for (size_t i=0; i<cases.size(); i++) {\n" +
               "        if (i>0) std::cout<<',';\n" +
               "        try {\n" +
               "            std::cout << dispatchWrapper(" + methodName + ", cases[i]);\n" +
               "        } catch(const std::exception& e) {\n" +
               "            std::cout << \"{\\\"error\\\":\\\"\" << e.what() << \"\\\"}\";\n" +
               "        }\n" +
               "    }\n" +
               "    std::cout << ']' << std::endl;\n" +
               "}\n";
    }

    private String generateCSharpRunner(String methodName) {
        return "using System;\n" +
               "using System.Reflection;\n" +
               "using System.Text.Json;\n" +
               "using System.Text.Json.Nodes;\n" +
               "\n" +
               "class Runner {\n" +
               "    static void Main() {\n" +
               "        string json = Console.In.ReadToEnd().Trim();\n" +
               "        var allCases = JsonNode.Parse(json)!.AsArray();\n" +
               "        var results = new JsonArray();\n" +
               "        \n" +
               "        MethodInfo method = typeof(Solution).GetMethod(\"" + methodName + "\", BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance | BindingFlags.Static | BindingFlags.IgnoreCase);\n" +
               "        if (method == null) method = typeof(Solution).GetMethod(\"" + methodName + "\", BindingFlags.Public | BindingFlags.NonPublic | BindingFlags.Instance | BindingFlags.Static);\n" +
               "        if (method == null) {\n" +
               "            Console.Write(\"[{\\\"error\\\":\\\"Method not found\\\"}]\");\n" +
               "            return;\n" +
               "        }\n" +
               "        \n" +
               "        var parameters = method.GetParameters();\n" +
               "        object instance = method.IsStatic ? null : Activator.CreateInstance(typeof(Solution));\n" +
               "        \n" +
               "        foreach (var testCase in allCases) {\n" +
               "            try {\n" +
               "                var argsArr = testCase!.AsArray();\n" +
               "                object[] args = new object[parameters.Length];\n" +
               "                for (int j = 0; j < parameters.Length; j++) {\n" +
               "                    args[j] = argsArr[j].Deserialize(parameters[j].ParameterType);\n" +
               "                }\n" +
               "                object res = method.Invoke(instance, args);\n" +
               "                results.Add(JsonSerializer.SerializeToNode(res));\n" +
               "            } catch (Exception e) {\n" +
               "                results.Add(JsonNode.Parse($\"{{{\\\"error\\\":\\\"{e.InnerException?.Message ?? e.Message}\\\"}}}\"));\n" +
               "            }\n" +
               "        }\n" +
               "        Console.Write(results.ToJsonString());\n" +
               "    }\n" +
               "}\n";
    }

    private String generateGoRunner(String methodName) {
        return "package main\n" +
               "\n" +
               "import (\n" +
               "    \"encoding/json\"\n" +
               "    \"fmt\"\n" +
               "    \"os\"\n" +
               "    \"reflect\"\n" +
               ")\n" +
               "\n" +
               "func main() {\n" +
               "    buf := make([]byte, 1<<20)\n" +
               "    n, _ := os.Stdin.Read(buf)\n" +
               "    var allCases []json.RawMessage\n" +
               "    if err := json.Unmarshal(buf[:n], &allCases); err != nil {\n" +
               "        fmt.Fprintln(os.Stderr, \"parse error:\", err)\n" +
               "        os.Exit(1)\n" +
               "    }\n" +
               "    \n" +
               "    fn := reflect.ValueOf(" + methodName + ")\n" +
               "    fnType := fn.Type()\n" +
               "    \n" +
               "    results := make([]interface{}, 0, len(allCases))\n" +
               "    for _, raw := range allCases {\n" +
               "        func() {\n" +
               "            defer func() {\n" +
               "                if r := recover(); r != nil {\n" +
               "                    results = append(results, map[string]string{\"error\": fmt.Sprintf(\"%v\", r)})\n" +
               "                }\n" +
               "            }()\n" +
               "            \n" +
               "            var argsRaw []json.RawMessage\n" +
               "            json.Unmarshal(raw, &argsRaw)\n" +
               "            \n" +
               "            in := make([]reflect.Value, fnType.NumIn())\n" +
               "            for j := 0; j < fnType.NumIn(); j++ {\n" +
               "                argType := fnType.In(j)\n" +
               "                argPtr := reflect.New(argType)\n" +
               "                json.Unmarshal(argsRaw[j], argPtr.Interface())\n" +
               "                in[j] = argPtr.Elem()\n" +
               "            }\n" +
               "            \n" +
               "            out := fn.Call(in)\n" +
               "            if len(out) > 0 {\n" +
               "                results = append(results, out[0].Interface())\n" +
               "            } else {\n" +
               "                results = append(results, nil)\n" +
               "            }\n" +
               "        }()\n" +
               "    }\n" +
               "    \n" +
               "    out, _ := json.Marshal(results)\n" +
               "    fmt.Print(string(out))\n" +
               "}\n";
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
