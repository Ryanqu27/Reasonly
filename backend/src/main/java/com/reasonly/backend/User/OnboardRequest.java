package com.reasonly.backend.User;
 
import java.util.List;

import com.reasonly.backend.Question.QuestionTopic;
import com.reasonly.backend.User.UserSettings.UserExperience;
import com.reasonly.backend.User.UserSettings.UserLanguage;

// Get initial information from user like experience, preferences, age, etc.
public record OnboardRequest(
        UserExperience experience,
        UserLanguage preferredLanguage,
        List<QuestionTopic> interests) {
}
