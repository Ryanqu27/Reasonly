package com.reasonly.backend.Question;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionType {
    DATA_STRUCTURES_AND_ALGORITHMS,     // Recursion, greedy, DP intuitionArrays, stacks, trees, graphs, hash tables
    SYSTEMS,                            // OS, memory, processes, threads, scheduling
    NETWORKING,                         // HTTP, TCP/IP, latency, reliability
    DATABASES,                          // SQL, indexing, normalization, transactions
    CONCURRENCY,                        // Locks, deadlocks, race conditions
    SOFTWARE_DESIGN,                    // OOP, design patterns, abstractions
    DEBUGGING,                          // Code tracing, finding logical errors
    CODE_REASONING;                     // Read code, predict output, edge cases


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
