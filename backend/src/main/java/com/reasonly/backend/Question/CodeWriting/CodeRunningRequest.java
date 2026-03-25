package com.reasonly.backend.Question.CodeWriting;

import com.reasonly.backend.User.UserLanguage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeRunningRequest {
    private String userCode;
    private Long questionId;
    private UserLanguage language;
}
