package com.reasonly.backend.User;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void incrementStreak(Long id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        LocalDate today = LocalDate.now();
        LocalDate lastCompleted = currentUser.getLastCompletedDate();

        if (lastCompleted != null && today.equals(lastCompleted)) {
            return;
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
        userRepository.save(currentUser);
    }

    @Transactional
    public void checkStreak(Long id) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        LocalDate today = LocalDate.now();
        LocalDate lastCompleted = currentUser.getLastCompletedDate();
        if (lastCompleted != null && (lastCompleted.equals(today.minusDays(1)) || lastCompleted.equals(today))) {
            return;
        }
        currentUser.setCurrentStreak(0);
        userRepository.save(currentUser);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    public UserProfile getUserProfile(String email) {
        User user = getUserByEmail(email);
        return new UserProfile(user.getEmail(), user.getCurrentStreak(), 
        user.getLongestStreak(), user.getRating(), user.getCreatedAt(), 
        user.getQuestionsAnsweredCorrectly(), user.getQuestionsAnsweredIncorrectly(), 
        user.getAccuracy());
    }

    @Transactional
    public void setExperience(Long id, UserExperience experience) {
        User currentUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        if (currentUser.getExperience() != null) {
            return;
        }
        currentUser.setExperience(experience);
        if (experience == UserExperience.BEGINNER) {
            currentUser.setRating(0);
        } 
        else if (experience == UserExperience.INTERMEDIATE) {
            currentUser.setRating(500);
        } 
        else if (experience == UserExperience.ADVANCED) {
            currentUser.setRating(1000);
        } 
        else if (experience == UserExperience.EXPERT) {
            currentUser.setRating(1500);
        }
        userRepository.save(currentUser);
    }
}