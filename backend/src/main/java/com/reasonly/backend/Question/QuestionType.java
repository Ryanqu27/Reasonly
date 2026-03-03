package com.reasonly.backend.Question;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionType {
    MULTIPLE_CHOICE, // Standard pick-one-answer format
    SELECT_ALL, // Select all correct answers
    CODE_WRITING, // Write code to solve a problem
    FIND_THE_BUG, // Identify the incorrect line in a code snippet
    ORDER_CODE, // Arrange code blocks in the correct order
    FILL_IN_THE_BLANK; // Fill in missing keywords or values

    @JsonCreator
    public static QuestionType fromString(String value) {
        for (QuestionType type : QuestionType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
