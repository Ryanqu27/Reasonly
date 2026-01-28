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

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping()
    public ResponseEntity<List<Question>> getAllQuestions(
            @RequestParam(required = false) QuestionType type) {
        List<Question> questions = questionService.getQuestions(type);
        return ResponseEntity.ok(questions);
    }

    @GetMapping("/play")
    public ResponseEntity<List<Question>> getPlayQuestions() {
        return ResponseEntity.ok(questionService.getPlayQuestions(getCurrentUser()));
    }

    private com.reasonly.backend.User.User getCurrentUser() {
        org.springframework.security.core.Authentication authentication = org.springframework.security.core.context.SecurityContextHolder
                .getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof com.reasonly.backend.User.User) {
            return (com.reasonly.backend.User.User) authentication.getPrincipal();
        }
        throw new RuntimeException("User not authenticated");
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