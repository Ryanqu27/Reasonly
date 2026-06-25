package com.reasonly.backend.Question;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reasonly.backend.Question.CodeWriting.CodeExecutionResult;
import com.reasonly.backend.Question.CodeWriting.CodeRunningRequest;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final com.reasonly.backend.User.UserService userService;

    public QuestionController(QuestionService questionService, com.reasonly.backend.User.UserService userService) {
        this.questionService = questionService;
        this.userService = userService;
    }

    @GetMapping("/play")
    public ResponseEntity<List<Question>> getPlayQuestions() {
        return ResponseEntity.ok(questionService.getPlayQuestions(getCurrentUser()));
    }

    @PostMapping("/run")
    public ResponseEntity<CodeExecutionResult> runCode(@RequestBody CodeRunningRequest request) {
        return ResponseEntity.ok(questionService.runCode(request));
    }

    private com.reasonly.backend.User.User getCurrentUser() {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new RuntimeException("User not authenticated");
        }
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

    @GetMapping("{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }
}