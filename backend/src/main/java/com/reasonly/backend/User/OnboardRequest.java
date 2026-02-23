package com.reasonly.backend.User;

import java.util.List;

// Get initial information from user like experience, preferences, age, etc.
public record OnboardRequest(
        UserExperience experience,
        UserMotivation motivation,
        UserLanguage preferredLanguage,
        List<UserInterest> interests) {
}
