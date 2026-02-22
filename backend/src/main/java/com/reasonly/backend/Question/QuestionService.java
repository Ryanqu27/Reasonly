package com.reasonly.backend.Question;

import java.util.List;
import java.util.Random;

import com.reasonly.backend.User.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final Random random;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this(questionRepository, new Random());
    }

    public QuestionService(QuestionRepository questionRepository, Random random) {
        this.questionRepository = questionRepository;
        this.random = random;
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

    public List<Question> getPlayQuestions(User user) {
        int rating = user.getRating();
        QuestionDifficulty targetDifficulty = getDifficultyFromRating(rating);
        QuestionDifficulty selectedDifficulty = selectProbabilisticDifficulty(targetDifficulty);
        if (Math.random() < 0.5) {
            Question result = getReviewQuestion(user);
            if (result != null) {
                return List.of(result);
            }
        }
        List<Question> potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserId(selectedDifficulty,
                user.getId());

        if (!potentialQuestions.isEmpty()) {
            java.util.Collections.shuffle(potentialQuestions);
            return List.of(potentialQuestions.get(0));
        }

        // Fallback 1: Try exact target difficulty if we diverted
        if (selectedDifficulty != targetDifficulty) {
            potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserId(targetDifficulty, user.getId());
            if (!potentialQuestions.isEmpty()) {
                java.util.Collections.shuffle(potentialQuestions);
                return List.of(potentialQuestions.get(0));
            }
        }

        // Fallback 2: Try any difficulty, prioritizing closest to target
        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            if (d == selectedDifficulty || d == targetDifficulty)
                continue;
            potentialQuestions = questionRepository.findUnansweredByDifficultyAndUserId(d, user.getId());
            if (!potentialQuestions.isEmpty()) {
                java.util.Collections.shuffle(potentialQuestions);
                return List.of(potentialQuestions.get(0));
            }
        }
        // If no unanswered questions left, give review question. If no review question,
        // return empty list.
        Question result = getReviewQuestion(user);
        if (result != null) {
            return List.of(result);
        }
        return List.of();
    }

    private QuestionDifficulty getDifficultyFromRating(int rating) {
        if (rating <= 1000)
            return QuestionDifficulty.BASIC;
        if (rating <= 2000)
            return QuestionDifficulty.EASY;
        if (rating <= 3000)
            return QuestionDifficulty.MEDIUM;
        if (rating <= 4000)
            return QuestionDifficulty.HARD;
        return QuestionDifficulty.EXTREME;
    }

    private QuestionDifficulty selectProbabilisticDifficulty(QuestionDifficulty target) {
        int roll = (int) (random.nextDouble() * 100);

        // 70% chance for target difficulty
        if (roll < 70) {
            return target;
        }

        // 15% chance for easier (if possible)
        if (roll < 85) {
            return getEasierDifficulty(target);
        }

        // 15% chance for harder (if possible)
        return getHarderDifficulty(target);
    }

    private QuestionDifficulty getEasierDifficulty(QuestionDifficulty current) {
        int val = current.getValue() - 1;
        if (val < 1)
            return current; // already lowest
        return getDifficultyByValue(val);
    }

    private QuestionDifficulty getHarderDifficulty(QuestionDifficulty current) {
        int val = current.getValue() + 1;
        if (val > 5)
            return current; // already highest
        return getDifficultyByValue(val);
    }

    private QuestionDifficulty getDifficultyByValue(int value) {
        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            if (d.getValue() == value)
                return d;
        }
        return QuestionDifficulty.BASIC;
    }

    private Question getReviewQuestion(User user) {
        return questionRepository.findRandomDueReview(user.getId()).orElse(null);
    }
}