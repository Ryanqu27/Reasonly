package com.reasonly.backend.Question.CodeWriting;

import com.reasonly.backend.User.UserSettings.UserLanguage;

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
