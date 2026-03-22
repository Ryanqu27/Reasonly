package com.reasonly.backend.QuestionAttempt;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionType;
import com.reasonly.backend.Question.CodeWriting.CodeExecutionResult;
import com.reasonly.backend.Question.CodeWriting.CodeExecutionService;
import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserLanguage;
import com.reasonly.backend.User.UserRepository;

@Service
public class QuestionAttemptService {
    private final QuestionAttemptRepository questionAttemptRepository;
    private final UserRepository userRepository;
    private final com.reasonly.backend.Question.QuestionRepository questionRepository;
    private final CodeExecutionService codeExecutionService;

    public QuestionAttemptService(QuestionAttemptRepository questionAttemptRepository, UserRepository userRepository,
            com.reasonly.backend.Question.QuestionRepository questionRepository, CodeExecutionService codeExecutionService) {
        this.questionAttemptRepository = questionAttemptRepository;
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
        this.codeExecutionService = codeExecutionService;
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

    public boolean validateAnswer(Question question, List<String> userAnswer) {
        if (question.getType() == QuestionType.ORDER_CODE) {
            for (int i = 0; i < question.getCorrectAnswer().size(); i++) {
                if (!question.getCorrectAnswer().get(i).equals(userAnswer.get(i))) {
                    return false;
                }
            }
            return true;
        } 
        else if (question.getType() == QuestionType.MULTIPLE_CHOICE || 
            question.getType() == QuestionType.SELECT_ALL || 
            question.getType() == QuestionType.FIND_THE_BUG ||
            question.getType() == QuestionType.FILL_IN_THE_BLANK) {
            return question.getCorrectAnswer().size() == userAnswer.size() && question.getCorrectAnswer().containsAll(userAnswer);
        } 
        else {
            return question.getCorrectAnswer().equals(userAnswer);
        }
    }

    @Transactional
    public QuestionAttemptResult insertQuestionAttempt(QuestionAttemptRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + request.getUserId()));
        Question question = questionRepository.findById(request.getQuestionId())
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + request.getQuestionId()));

        boolean isCorrect = false;
        String errorMessage = null;
        String consoleOutput = null;

        if (question.getType() == QuestionType.CODE_WRITING) {
            String userCode = request.getAnswer().get(0);
            UserLanguage language = UserLanguage.valueOf(request.getAnswer().get(1));

            CodeExecutionResult execResult = codeExecutionService.executeCode(
                userCode,
                question.getAnswers(),        // Test case inputs
                question.getCorrectAnswer(),  // Expected outputs
                question.getMethodName(),     // Target method name
                language
            );
            isCorrect = execResult.isSuccess();
            errorMessage = execResult.getErrorMessage();
            consoleOutput = execResult.getConsoleOutput();
        } else {
            isCorrect = validateAnswer(question, request.getAnswer());
        }

        List<QuestionAttempt> questionAttempts = questionAttemptRepository.findByUserIdAndQuestion(user.getId(),
                question);
        if (!questionAttempts.isEmpty()) {
            // Update existing attempt's spaced repetition interval
            if (isCorrect) {
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

        user.setQuestionsAnsweredCorrectly(user.getQuestionsAnsweredCorrectly() + (isCorrect ? 1 : 0));
        user.setQuestionsAnsweredIncorrectly(user.getQuestionsAnsweredIncorrectly() + (isCorrect ? 0 : 1));
        user.setAccuracy((double) user.getQuestionsAnsweredCorrectly()
                / (user.getQuestionsAnsweredCorrectly() + user.getQuestionsAnsweredIncorrectly()));
        userRepository.save(user);
        QuestionAttemptResult result = updateUserRating(user, question, isCorrect);
        result.setErrorMessage(errorMessage);
        result.setConsoleOutput(consoleOutput);
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