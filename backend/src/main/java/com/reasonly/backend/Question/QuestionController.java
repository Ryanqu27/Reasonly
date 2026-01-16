package com.reasonly.backend.Question;

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
@RequestMapping("api/v1/questions")
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping()
    public List<Question> getQuestions() {
        return questionService.getQuestions();
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