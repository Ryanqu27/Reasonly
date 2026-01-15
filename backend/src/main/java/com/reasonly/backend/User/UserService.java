package com.reasonly.backend.User;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

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

    public void incrementStreak(Long id) {
        User currentUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        int newStreak = currentUser.getCurrentStreak() + 1;
        currentUser.setCurrentStreak(newStreak);
        if (newStreak > currentUser.getLongestStreak()) {
            currentUser.setLongestStreak(newStreak);
        }
        userRepository.save(currentUser);
    }

    public void updateCompletedDate(Long id) {
        User currentUser = userRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        currentUser.setLastCompletedDate(LocalDate.now());
        userRepository.save(currentUser);
    }
}
