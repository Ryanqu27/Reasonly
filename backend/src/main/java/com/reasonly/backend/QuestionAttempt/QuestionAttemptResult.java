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
}
