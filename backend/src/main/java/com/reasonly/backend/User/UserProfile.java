package com.reasonly.backend.User;

import java.time.LocalDate;

public record UserProfile(String email, int currentStreak, int longestStreak, 
    int rating, LocalDate createdAt, int questionsAnsweredCorrectly, 
    int questionsAnsweredIncorrectly, double accuracy) {}
