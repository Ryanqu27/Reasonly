package com.reasonly.backend;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionType {
    LOGIC, 
    PATTERNS, 
    MATH,
    SPATIAL,
    VERBAL,
    ALGORITHMIC,
    CRITICAL;


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
