package com.reasonly.backend.QuestionAttempt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.reasonly.backend.User.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionAttemptResult {
    private boolean correct;
    private int ratingChange;
    private int newRating;
    private String errorMessage;
    private String consoleOutput;
    private User user;

    public QuestionAttemptResult(boolean correct, int ratingChange, int newRating, User user) {
        this.correct = correct;
        this.ratingChange = ratingChange;
        this.newRating = newRating;
        this.user = user;
    }
}
