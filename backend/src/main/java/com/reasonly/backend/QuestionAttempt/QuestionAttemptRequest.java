package com.reasonly.backend.QuestionAttempt;

import lombok.Data;

@Data
public class QuestionAttemptRequest {
    private Long userId;
    private Long questionId;
    private String answer;
}
