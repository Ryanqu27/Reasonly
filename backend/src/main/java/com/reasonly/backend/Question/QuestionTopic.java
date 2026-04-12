package com.reasonly.backend.Question;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum QuestionTopic {
    DATA_STRUCTURES_AND_ALGORITHMS, // Recursion, greedy, DP intuition, arrays, stacks, trees, graphs, hash tables
    SYSTEMS, // OS, memory, processes, threads, scheduling
    NETWORKING, // HTTP, TCP/IP, latency, reliability
    DATABASES, // SQL, indexing, normalization, transactions
    CONCURRENCY, // Multi-threading, race conditions, async
    SOFTWARE_DESIGN, // Design patterns, SOLID, UML, Architecture
    LANGUAGE_KNOWLEDGE; // Language specific trivia (e.g. Java, JS, Python)

    @JsonCreator
    public static QuestionTopic fromString(String value) {
        for (QuestionTopic topic : QuestionTopic.values()) {
            if (topic.name().equalsIgnoreCase(value)) {
                return topic;
            }
        }
        return null;
    }
}
