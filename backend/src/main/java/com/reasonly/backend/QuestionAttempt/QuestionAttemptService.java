package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.User.User;

@Service
public class QuestionAttemptService {
    private final QuestionAttemptRepository questionAttemptRepository;

    public QuestionAttemptService(QuestionAttemptRepository questionAttemptRepository) {
        this.questionAttemptRepository = questionAttemptRepository;
    }

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttemptRepository.findAll();
    }

    public QuestionAttempt getQuestionAttemptById(Long id) {
        return questionAttemptRepository.findById(id)
            .orElseThrow(() -> new IllegalStateException("Question attempt not found with id: " + id));
    }

    public List<QuestionAttempt> getQuestionAttemptsByUser(User user) {
        return questionAttemptRepository.findByUser(user);
    }

    public List<QuestionAttempt> getQuestionAttemptsByQuestion(Question question) {
        return questionAttemptRepository.findByQuestion(question);
    }

    public List<QuestionAttempt> getQuestionAttemptsByUserAndQuestion(User user, Question question) {
        return questionAttemptRepository.findByUserAndQuestion(user, question);
    }

    public void insertQuestionAttempt(QuestionAttempt newQuestionAttempt) {
        questionAttemptRepository.save(newQuestionAttempt);
    }

    public void deleteQuestionAttempt(Long id) {
        questionAttemptRepository.deleteById(id);
    }

    public void updateQuestionAttempt(Long id, QuestionAttempt updatedQuestionAttempt) {
        QuestionAttempt existingQuestionAttempt = questionAttemptRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Question attempt not found with id: " + id));
        existingQuestionAttempt.setUser(updatedQuestionAttempt.getUser());
        existingQuestionAttempt.setQuestion(updatedQuestionAttempt.getQuestion());
        existingQuestionAttempt.setAnswer(updatedQuestionAttempt.getAnswer());
        existingQuestionAttempt.setCorrect(updatedQuestionAttempt.isCorrect());
        existingQuestionAttempt.setTimeTakenMillis(updatedQuestionAttempt.getTimeTakenMillis());
        existingQuestionAttempt.setAttemptedAt(updatedQuestionAttempt.getAttemptedAt());
        questionAttemptRepository.save(existingQuestionAttempt);
    }
}