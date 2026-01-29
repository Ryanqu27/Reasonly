package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/question-attempts")
public class QuestionAttemptController {

    private final QuestionAttemptService questionAttemptService;

    public QuestionAttemptController(QuestionAttemptService questionAttemptService) {
        this.questionAttemptService = questionAttemptService;
    }

    @GetMapping()
    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttemptService.getQuestionAttempts();
    }

    @GetMapping("{id}")
    public QuestionAttempt getQuestionAttemptById(@PathVariable Long id) {
        return questionAttemptService.getQuestionAttemptById(id);
    }

    @PostMapping()
    public org.springframework.http.ResponseEntity<QuestionAttemptResult> addQuestionAttempt(
            @RequestBody QuestionAttemptRequest request) {
        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);
        return org.springframework.http.ResponseEntity.ok(result);
    }

    @DeleteMapping("{id}")
    public void deleteQuestionAttempt(@PathVariable Long id) {
        questionAttemptService.deleteQuestionAttempt(id);
    }

    @PutMapping("{id}")
    public void updateQuestionAttempt(@PathVariable Long id, @RequestBody QuestionAttempt updatedQuestionAttempt) {
        questionAttemptService.updateQuestionAttempt(id, updatedQuestionAttempt);
    }
}