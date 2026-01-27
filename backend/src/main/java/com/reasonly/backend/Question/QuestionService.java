package com.reasonly.backend.Question;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<Question> getQuestions(QuestionType type) {
        if (type == null) {
            return questionRepository.findAll();
        }
        return questionRepository.findByType(type);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Question not found with id: " + id));
    }

    public void insertQuestion(Question newQuestion) {
        questionRepository.save(newQuestion);
    }

    public void deleteQuestion(Long id) {
        questionRepository.deleteById(id);
    }

    public void updateQuestion(Long id, Question updatedQuestion) {
        Question existingQuestion = questionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));
        existingQuestion.setType(updatedQuestion.getType());
        existingQuestion.setDifficulty(updatedQuestion.getDifficulty());
        existingQuestion.setQuestion(updatedQuestion.getQuestion());
        existingQuestion.setAnswers(updatedQuestion.getAnswers());
        existingQuestion.setCorrectAnswer(updatedQuestion.getCorrectAnswer());
        questionRepository.save(existingQuestion);
    }
}