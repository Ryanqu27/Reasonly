package com.reasonly.backend.QuestionAttempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAttemptResult {
    private boolean correct;
    private int ratingChange;
    private int newRating;
    private String errorMessage;
    private String consoleOutput;

    public QuestionAttemptResult(boolean correct, int ratingChange, int newRating) {
        this.correct = correct;
        this.ratingChange = ratingChange;
        this.newRating = newRating;
    }
}
