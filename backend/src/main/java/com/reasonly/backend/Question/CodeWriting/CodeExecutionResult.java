package com.reasonly.backend.Question.CodeWriting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeExecutionResult {
    private boolean isSuccess;
    private String consoleOutput;
    private String errorMessage;

    private int testCasesPassed;
    private int totalTestCases;
}
