package com.reasonly.backend.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reasonly.backend.User.UserSettings.UserSettings;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public User getCurrentUser(@AuthenticationPrincipal User user) {
        return userService.getUserByEmail(user.getEmail());
    }

    @GetMapping("/profile")
    public UserProfile getUserProfile(@AuthenticationPrincipal User user) {
        return userService.getUserProfile(user.getEmail());
    }

    @GetMapping("/settings")
    public UserSettings getUserSettings(@AuthenticationPrincipal User user) {
        return userService.getUserSettings(user.getEmail());
    }

    @PutMapping("/settings")
    public void updateUserSettings(@AuthenticationPrincipal User user, @RequestBody UserSettings userSettings) {
        userService.updateUserSettings(user.getEmail(), userSettings);
    }

    @PostMapping("/check-streak")
    public void checkStreak(@AuthenticationPrincipal User user) {
        userService.checkStreak(user.getId());
    }

    @PutMapping("/complete-today")
    public void updateCompletedDate(@AuthenticationPrincipal User user) {
        userService.incrementStreak(user.getId());
    }

    @PutMapping("/onboard")
    public void onboardUser(@AuthenticationPrincipal User user, @RequestBody OnboardRequest request) {
        userService.onboardUser(user.getId(), request);
    }
}
