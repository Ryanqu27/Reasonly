package com.reasonly.backend.QuestionAttempt;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionType;
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
        return questionAttemptRepository.findByUserId(user.getId());
    }

    public List<QuestionAttempt> getQuestionAttemptsByQuestion(Question question) {
        return questionAttemptRepository.findByQuestion(question);
    }

    public List<QuestionAttempt> getQuestionAttemptsByUserAndQuestion(User user, Question question) {
        return questionAttemptRepository.findByUserIdAndQuestion(user.getId(), question);
    }

    public boolean validateAnswer(List<String> correctAnswer, List<String> userAnswer, QuestionType questionType) {
        if (questionType == QuestionType.MULTIPLE_CHOICE || questionType == QuestionType.SELECT_ALL || questionType == QuestionType.FIND_THE_BUG) {
            return correctAnswer.size() == userAnswer.size() && correctAnswer.containsAll(userAnswer);
        } else {
            // Implement other question type logics here in future
            return correctAnswer.equals(userAnswer);
        }
    }

    @Transactional
    public QuestionAttemptResult insertQuestionAttempt(QuestionAttemptRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));

        List<QuestionAttempt> questionAttempts = questionAttemptRepository.findByUserIdAndQuestion(user.getId(),
                question);
        if (!questionAttempts.isEmpty()) {
            // Update existing attempt if it exists
            if (questionAttempts.get(0).getQuestion().getCorrectAnswer().equals(request.getAnswer())) {
                questionAttempts.get(0).setInterval(questionAttempts.get(0).getInterval() * 2);
                questionAttempts.get(0)
                        .setNextReviewDate(LocalDate.now().plusDays(questionAttempts.get(0).getInterval()));
            } else {
                questionAttempts.get(0).setInterval(1);
                questionAttempts.get(0).setNextReviewDate(LocalDate.now().plusDays(1));
            }

            questionAttemptRepository.save(questionAttempts.get(0));
        } else {
            QuestionAttempt attempt = new QuestionAttempt();
            attempt.setUserId(user.getId());
            attempt.setQuestion(question);
            attempt.setAnswer(request.getAnswer());
            questionAttemptRepository.save(attempt);
        }

        boolean isCorrect = validateAnswer(question.getCorrectAnswer(), request.getAnswer(), question.getType());
        user.setQuestionsAnsweredCorrectly(user.getQuestionsAnsweredCorrectly() + (isCorrect ? 1 : 0));
        user.setQuestionsAnsweredIncorrectly(user.getQuestionsAnsweredIncorrectly() + (isCorrect ? 0 : 1));
        user.setAccuracy((double) user.getQuestionsAnsweredCorrectly()
                / (user.getQuestionsAnsweredCorrectly() + user.getQuestionsAnsweredIncorrectly()));
        userRepository.save(user);
        QuestionAttemptResult result = updateUserRating(user, question, isCorrect);
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
                ratingChange = 20;
            } else {
                ratingChange = -60;
            }
        } else if (questionDiff.getValue() > userLevel.getValue()) {
            // Harder question
            if (isCorrect) {
                ratingChange = 60;
            } else {
                ratingChange = -20;
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
        existingQuestionAttempt.setUserId(updatedQuestionAttempt.getUserId());
        existingQuestionAttempt.setQuestion(updatedQuestionAttempt.getQuestion());
        existingQuestionAttempt.setAnswer(updatedQuestionAttempt.getAnswer());
        questionAttemptRepository.save(existingQuestionAttempt);
    }

    @Transactional
    public void resetQuestionAttempts(Long userId) {
        questionAttemptRepository.deleteAllByUserId(userId);
    }
}