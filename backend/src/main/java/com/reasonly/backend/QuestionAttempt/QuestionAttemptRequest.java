package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import lombok.Data;

@Data
public class QuestionAttemptRequest {
    private Long questionId;
    private List<String> answer;
}
