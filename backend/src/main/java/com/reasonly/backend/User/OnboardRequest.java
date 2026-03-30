package com.reasonly.backend.User;
 
import java.util.List;

import com.reasonly.backend.Question.QuestionTopic;

// Get initial information from user like experience, preferences, age, etc.
public record OnboardRequest(
        UserExperience experience,
        UserMotivation motivation,
        UserLanguage preferredLanguage,
        List<QuestionTopic> interests) {
}
