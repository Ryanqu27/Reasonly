package com.reasonly.backend.User;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.reasonly.backend.User.UserSettings.UserExperience;
import com.reasonly.backend.User.UserSettings.UserSettings;

import jakarta.transaction.Transactional;
import org.springframework.lang.NonNull;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void addUser(@NonNull User user) {
        userRepository.save(user);
    }

    @Transactional
    public User incrementStreak(@NonNull Long id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        LocalDate today = LocalDate.now();
        LocalDate lastCompleted = currentUser.getLastCompletedDate();

        if (lastCompleted != null && today.equals(lastCompleted)) {
            return currentUser;
        }

        int newStreak;
        if (lastCompleted != null && lastCompleted.equals(today.minusDays(1))) {
            newStreak = currentUser.getCurrentStreak() + 1;
        } else {
            newStreak = 1;
        }

        currentUser.setCurrentStreak(newStreak);
        currentUser.setLastCompletedDate(today);
        if (newStreak > currentUser.getLongestStreak()) {
            currentUser.setLongestStreak(newStreak);
        }
        return userRepository.save(currentUser);
    }

    @Transactional
    public User checkStreak(@NonNull Long id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        LocalDate today = LocalDate.now();
        LocalDate lastCompleted = currentUser.getLastCompletedDate();
        if (lastCompleted != null && (lastCompleted.equals(today.minusDays(1)) || lastCompleted.equals(today))) {
            return currentUser;
        }
        currentUser.setCurrentStreak(0);
        return userRepository.save(currentUser);
    }

    public void deleteUser(@NonNull Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(@NonNull String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional
    public User onboardUser(@NonNull Long id, OnboardRequest request) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (currentUser.getUserSettings() != null) {
            return currentUser;
        }
        UserSettings settings = new UserSettings();
        settings.setExperience(request.experience());

        settings.setPreferredLanguage(request.preferredLanguage());
        settings.setInterests(request.interests());
        settings.setDarkMode(true);
        settings.setEditorFontSize(14);
        settings.setEditorTheme("vs-dark");
        settings.setEditorTabSize(4);
        settings.setUser(currentUser);
        
        currentUser.setUserSettings(settings);

        if (request.experience() == UserExperience.BEGINNER) {
            currentUser.setRating(0);
        } else if (request.experience() == UserExperience.INTERMEDIATE) {
            currentUser.setRating(500);
        } else if (request.experience() == UserExperience.ADVANCED) {
            currentUser.setRating(1000);
        } else if (request.experience() == UserExperience.EXPERT) {
            currentUser.setRating(1500);
        }
        return userRepository.save(currentUser);
    }

    public void updateUserSettings(@NonNull String email, UserSettings updatedSettings) {
        User user = getUserByEmail(email);
        UserSettings existingSettings = user.getUserSettings();
        
        existingSettings.setExperience(updatedSettings.getExperience());

        existingSettings.setPreferredLanguage(updatedSettings.getPreferredLanguage());
        existingSettings.setInterests(updatedSettings.getInterests());
        existingSettings.setDarkMode(updatedSettings.isDarkMode());
        existingSettings.setEditorFontSize(updatedSettings.getEditorFontSize());
        existingSettings.setEditorTheme(updatedSettings.getEditorTheme());
        existingSettings.setEditorTabSize(updatedSettings.getEditorTabSize());
        
        user.setUserSettings(existingSettings);
        userRepository.save(user);
    }

    public UserSettings getUserSettings(@NonNull String email) {
        User user = getUserByEmail(email);
        UserSettings settings = user.getUserSettings();
        return settings;
    }
}