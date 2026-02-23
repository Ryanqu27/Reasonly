package com.reasonly.backend.User;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

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

    @PostMapping("{id}/check-streak")
    public void checkStreak(@PathVariable Long id) {
        userService.checkStreak(id);
    }

    @PutMapping("/{id}/complete-today")
    public void updateCompletedDate(@PathVariable Long id) {
        userService.incrementStreak(id);
    }

    @PutMapping("/{id}/onboard")
    public void onboardUser(@PathVariable Long id, @RequestBody OnboardRequest request) {
        userService.onboardUser(id, request);
    }
}
