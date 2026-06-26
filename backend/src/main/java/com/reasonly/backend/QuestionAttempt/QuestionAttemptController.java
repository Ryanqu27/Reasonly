package com.reasonly.backend.QuestionAttempt;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.reasonly.backend.User.User;

@RestController
@RequestMapping("/api/question-attempts")
public class QuestionAttemptController {

    private final QuestionAttemptService questionAttemptService;

    public QuestionAttemptController(QuestionAttemptService questionAttemptService) {
        this.questionAttemptService = questionAttemptService;
    }

    @PostMapping()
    public org.springframework.http.ResponseEntity<QuestionAttemptResult> addQuestionAttempt(
            @RequestBody QuestionAttemptRequest request) {
        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);
        return org.springframework.http.ResponseEntity.ok(result);
    }

    @DeleteMapping("/reset")
    public void deleteQuestionAttemptsByUserId(@AuthenticationPrincipal User user) {
        questionAttemptService.resetQuestionAttempts(user.getId());
    }
}