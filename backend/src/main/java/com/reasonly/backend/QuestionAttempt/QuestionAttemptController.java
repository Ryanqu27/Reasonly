package com.reasonly.backend.QuestionAttempt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserService;

@RestController
@RequestMapping("/api/question-attempts")
public class QuestionAttemptController {

    private final QuestionAttemptService questionAttemptService;
    private final UserService userService;

    public QuestionAttemptController(QuestionAttemptService questionAttemptService, UserService userService) {
        this.questionAttemptService = questionAttemptService;
        this.userService = userService;
    }

    @PostMapping()
    public org.springframework.http.ResponseEntity<QuestionAttemptResult> addQuestionAttempt(
            @AuthenticationPrincipal User user,
            Authentication authentication,
            @RequestBody QuestionAttemptRequest request) {
        Long userId = user != null ? user.getId() : resolveUserId(authentication);
        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(userId, request);
        return org.springframework.http.ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public void deleteQuestionAttemptsByUserId(@AuthenticationPrincipal User user, Authentication authentication) {
        Long userId = user != null ? user.getId() : resolveUserId(authentication);
        questionAttemptService.resetQuestionAttempts(userId);
    }

    private Long resolveUserId(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User user) {
                return user.getId();
            }
            String name = authentication.getName();
            if (name != null && !name.isBlank()) {
                try {
                    return userService.getUserByEmail(name).getId();
                } catch (Exception ignored) {
                    // Mock user or non-existent email in test context
                }
            }
        }
        throw new RuntimeException("User not authenticated");
    }
}