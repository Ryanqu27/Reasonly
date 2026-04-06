package com.reasonly.backend.User;
 
import java.util.List;

import com.reasonly.backend.Question.QuestionTopic;
import com.reasonly.backend.User.UserSettings.UserExperience;
import com.reasonly.backend.User.UserSettings.UserLanguage;
import com.reasonly.backend.User.UserSettings.UserMotivation;

// Get initial information from user like experience, preferences, age, etc.
public record OnboardRequest(
        UserExperience experience,
        UserMotivation motivation,
        UserLanguage preferredLanguage,
        List<QuestionTopic> interests) {
}
