package com.reasonly.backend.QuestionAttempt;

import java.util.List;

import org.springframework.stereotype.Service;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserRepository;

@Service
public class QuestionAttemptService {
    private final QuestionAttemptRepository questionAttemptRepository;
    private final UserRepository userRepository;

    private final com.reasonly.backend.Question.QuestionRepository questionRepository;

    public QuestionAttemptService(QuestionAttemptRepository questionAttemptRepository, UserRepository userRepository,
            com.reasonly.backend.Question.QuestionRepository questionRepository) {
        this.questionAttemptRepository = questionAttemptRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
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

    public QuestionAttemptResult insertQuestionAttempt(QuestionAttemptRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));

        QuestionAttempt attempt = new QuestionAttempt();
        attempt.setUser(user);
        attempt.setQuestion(question);
        attempt.setAnswer(request.getAnswer());

        boolean isCorrect = request.getAnswer().equals(question.getCorrectAnswer());

        QuestionAttemptResult result = updateUserRating(user, question, isCorrect);
        if (isCorrect) {
            questionAttemptRepository.save(attempt);
        }
        return result;
    }

    private QuestionAttemptResult updateUserRating(User user, Question question, boolean isCorrect) {
        int currentRating = user.getRating();
        QuestionDifficulty questionDiff = question.getDifficulty();
        QuestionDifficulty userLevel = getDifficultyFromRating(currentRating);

        int ratingChange = 0;

        if (questionDiff.getValue() < userLevel.getValue()) {
            // Easier question
            if (isCorrect) {
                ratingChange = 20; // Small reward
            } else {
                ratingChange = -60; // Large penalty
            }
        } else if (questionDiff.getValue() > userLevel.getValue()) {
            // Harder question
            if (isCorrect) {
                ratingChange = 60; // Large reward
            } else {
                ratingChange = -20; // Small penalty
            }
        } else {
            // Matched difficulty
            if (isCorrect) {
                ratingChange = 40;
            } else {
                ratingChange = -40;
            }
        }

        int newRating = Math.max(0, currentRating + ratingChange); // Prevent negative rating
        user.setRating(newRating);
        userRepository.save(user);

        return new QuestionAttemptResult(isCorrect, ratingChange, newRating);
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

    public void deleteQuestionAttempt(Long id) {
        questionAttemptRepository.deleteById(id);
    }

    public void updateQuestionAttempt(Long id, QuestionAttempt updatedQuestionAttempt) {
        QuestionAttempt existingQuestionAttempt = questionAttemptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question attempt not found with id: " + id));
        existingQuestionAttempt.setUser(updatedQuestionAttempt.getUser());
        existingQuestionAttempt.setQuestion(updatedQuestionAttempt.getQuestion());
        existingQuestionAttempt.setAnswer(updatedQuestionAttempt.getAnswer());
        questionAttemptRepository.save(existingQuestionAttempt);
    }
}