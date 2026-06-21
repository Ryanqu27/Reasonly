package com.reasonly.backend.Question;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping()
    public ResponseEntity<List<Question>> getAllQuestions(
            @RequestParam(required = false) QuestionTopic topic) {
        List<Question> questions = questionService.getQuestions(topic);
        return ResponseEntity.ok(questions);
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
        // Extract the email/username from the principal and fetch a FRESH copy from
        // the database so that any recently-saved settings (e.g. preferredLanguage)
        // are always reflected — the Spring Security principal is loaded once at
        // login time and would otherwise be stale.
        String email = authentication.getName();
        return userService.getUserByEmail(email);
    }

    @GetMapping("{id}")
    public Question getQuestionById(@PathVariable Long id) {
        return questionService.getQuestionById(id);
    }

    @PostMapping()
    public void addQuestion(@RequestBody Question newQuestion) {
        questionService.insertQuestion(newQuestion);
    }

    @DeleteMapping("{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestion(id);
    }

    @PutMapping("{id}")
    public void updateQuestion(@PathVariable Long id, @RequestBody Question updatedQuestion) {
        questionService.updateQuestion(id, updatedQuestion);
    }
}