package com.reasonly.backend.QuestionAttempt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionRepository;
import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserRepository;

class QuestionAttemptServiceTest {

    @Mock
    private QuestionAttemptRepository questionAttemptRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionRepository questionRepository;

    private QuestionAttemptService questionAttemptService;

    private User user;
    private Question question;
    private QuestionAttemptRequest request;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionAttemptService = new QuestionAttemptService(
                questionAttemptRepository, userRepository, questionRepository);

        user = new User();
        user.setId(1L);
        user.setRating(1500); // maps to EASY difficulty
        user.setQuestionsAnsweredCorrectly(0);
        user.setQuestionsAnsweredIncorrectly(0);
        user.setAccuracy(0);

        question = new Question();
        question.setDifficulty(QuestionDifficulty.EASY);
        question.setCorrectAnswer("correct");

        request = new QuestionAttemptRequest();
        request.setUserId(1L);
        request.setQuestionId(10L);
        request.setAnswer("correct");

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(questionRepository.findById(10L)).thenReturn(Optional.of(question));
    }

    // ─── Rating Change Tests ────────────────────────────────────────────────────

    @Test
    void insertQuestionAttempt_CorrectAnswer_MatchedDifficulty_Gains40Rating() {
        // User is EASY (rating 1500), question is EASY — matched difficulty
        // Correct answer → +40
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(true, result.isCorrect());
        assertEquals(40, result.getRatingChange());
        assertEquals(1540, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_WrongAnswer_MatchedDifficulty_Loses40Rating() {
        // User is EASY (1500), question is EASY — matched difficulty
        // Wrong answer → -40
        request.setAnswer("wrong");
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(false, result.isCorrect());
        assertEquals(-40, result.getRatingChange());
        assertEquals(1460, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_CorrectAnswer_HarderQuestion_Gains60Rating() {
        // User is EASY (1500), question is MEDIUM (harder)
        // Correct answer on harder question → +60
        question.setDifficulty(QuestionDifficulty.MEDIUM);
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(60, result.getRatingChange());
        assertEquals(1560, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_WrongAnswer_HarderQuestion_Loses20Rating() {
        // User is EASY (1500), question is MEDIUM (harder)
        // Wrong answer on harder question → -20
        question.setDifficulty(QuestionDifficulty.MEDIUM);
        request.setAnswer("wrong");
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(-20, result.getRatingChange());
        assertEquals(1480, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_CorrectAnswer_EasierQuestion_Gains20Rating() {
        // User is EASY (1500), question is BASIC (easier)
        // Correct answer on easier question → +20
        question.setDifficulty(QuestionDifficulty.BASIC);
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(20, result.getRatingChange());
        assertEquals(1520, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_WrongAnswer_EasierQuestion_Loses60Rating() {
        // User is EASY (1500), question is BASIC (easier)
        // Wrong answer on easier question → -60 (big penalty)
        question.setDifficulty(QuestionDifficulty.BASIC);
        request.setAnswer("wrong");
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(-60, result.getRatingChange());
        assertEquals(1440, result.getNewRating());
    }

    @Test
    void insertQuestionAttempt_RatingCannotGoBelowZero() {
        // User has very low rating, wrong answer on matched difficulty should floor at
        // 0
        user.setRating(20); // BASIC level (≤1000)
        question.setDifficulty(QuestionDifficulty.BASIC);
        request.setAnswer("wrong");
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        QuestionAttemptResult result = questionAttemptService.insertQuestionAttempt(request);

        assertEquals(0, result.getNewRating()); // should not go negative
    }

    @Test
    void insertQuestionAttempt_CorrectAnswer_UpdatesAccuracyCorrectly() {
        // 1 correct, 0 incorrect → accuracy = 1.0
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        questionAttemptService.insertQuestionAttempt(request);

        assertEquals(1, user.getQuestionsAnsweredCorrectly());
        assertEquals(0, user.getQuestionsAnsweredIncorrectly());
        assertEquals(1.0, user.getAccuracy(), 0.001);
    }

    @Test
    void insertQuestionAttempt_WrongAnswer_UpdatesAccuracyCorrectly() {
        // 0 correct, 1 incorrect → accuracy = 0.0
        request.setAnswer("wrong");
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        questionAttemptService.insertQuestionAttempt(request);

        assertEquals(0, user.getQuestionsAnsweredCorrectly());
        assertEquals(1, user.getQuestionsAnsweredIncorrectly());
        assertEquals(0.0, user.getAccuracy(), 0.001);
    }

    @Test
    void insertQuestionAttempt_MixedAnswers_AccuracyIsCorrectRatio() {
        // Simulate user with existing stats: 3 correct, 1 incorrect before this attempt
        user.setQuestionsAnsweredCorrectly(3);
        user.setQuestionsAnsweredIncorrectly(1);
        user.setAccuracy(0.75);

        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        // Another correct answer → 4 correct, 1 incorrect → 80%
        questionAttemptService.insertQuestionAttempt(request);

        assertEquals(4, user.getQuestionsAnsweredCorrectly());
        assertEquals(1, user.getQuestionsAnsweredIncorrectly());
        assertEquals(0.8, user.getAccuracy(), 0.001);
    }


    @Test
    void insertQuestionAttempt_FirstAttempt_SavesNewAttempt() {
        // No previous attempt — should save a brand new QuestionAttempt
        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(Collections.emptyList());

        questionAttemptService.insertQuestionAttempt(request);

        verify(questionAttemptRepository).save(any(QuestionAttempt.class));
    }

    @Test
    void insertQuestionAttempt_RepeatCorrectAnswer_DoublesInterval() {
        // Existing attempt with interval=2 → correct answer → interval becomes 4
        QuestionAttempt existingAttempt = new QuestionAttempt();
        existingAttempt.setQuestion(question);
        existingAttempt.setInterval(2);
        existingAttempt.setNextReviewDate(LocalDate.now());

        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(List.of(existingAttempt));

        questionAttemptService.insertQuestionAttempt(request);

        assertEquals(4, existingAttempt.getInterval());
        assertEquals(LocalDate.now().plusDays(4), existingAttempt.getNextReviewDate());
    }

    @Test
    void insertQuestionAttempt_RepeatWrongAnswer_ResetsIntervalTo1() {
        // Existing attempt with interval=8 → wrong answer → resets to 1
        QuestionAttempt existingAttempt = new QuestionAttempt();
        existingAttempt.setQuestion(question);
        existingAttempt.setInterval(8);
        existingAttempt.setNextReviewDate(LocalDate.now().plusDays(8));

        when(questionAttemptRepository.findByUserIdAndQuestion(1L, question))
                .thenReturn(List.of(existingAttempt));

        request.setAnswer("wrong");
        questionAttemptService.insertQuestionAttempt(request);

        assertEquals(1, existingAttempt.getInterval());
        assertEquals(LocalDate.now().plusDays(1), existingAttempt.getNextReviewDate());
    }

    @Test
    void insertQuestionAttempt_UserNotFound_ThrowsRuntimeException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        request.setUserId(99L);

        assertThrows(RuntimeException.class,
                () -> questionAttemptService.insertQuestionAttempt(request));
    }

    @Test
    void insertQuestionAttempt_QuestionNotFound_ThrowsRuntimeException() {
        when(questionRepository.findById(99L)).thenReturn(Optional.empty());
        request.setQuestionId(99L);

        assertThrows(RuntimeException.class,
                () -> questionAttemptService.insertQuestionAttempt(request));
    }
}
