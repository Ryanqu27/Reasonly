package com.reasonly.backend;

public enum QuestionDifficulty {
    BASIC(1),
    EASY(2),
    MEDIUM(3),
    HARD(4),
    EXTREME(5);

    private int value;

    private QuestionDifficulty(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
