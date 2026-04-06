package com.reasonly.backend.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reasonly.backend.User.User;
import com.reasonly.backend.Question.CodeWriting.CodeExecutionService;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;
    
    @Mock
    private CodeExecutionService codeExecutionService;

    private QuestionService questionService;

    // Helper: creates a Question with the given difficulty
    private Question questionOf(QuestionDifficulty difficulty) {
        Question q = new Question();
        q.setDifficulty(difficulty);
        return q;
    }

    // Helper: sets up mocks so every difficulty tier has exactly one question
    // available
    private void mockAllDifficultiesAvailable() {
        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            when(questionRepository.findUnansweredByDifficultyAndUserId(eq(d), anyLong()))
                            .thenReturn(List.of(questionOf(d)));
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ─── Fallback Logic ──────────────────────────────────────────────────────────

    @Test
    void getPlayQuestions_FallbackToAny_WhenTargetEmpty() {
        // Target difficulty (EASY) is empty → falls back to BASIC
        questionService = new QuestionService(questionRepository, codeExecutionService);
        User user = new User();
        user.setId(1L);
        user.setRating(1500); // EASY
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.EASY), anyLong()))
                        .thenReturn(Collections.emptyList());
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.BASIC), anyLong()))
                        .thenReturn(List.of(questionOf(QuestionDifficulty.BASIC)));

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.BASIC, result.get(0).getDifficulty());
    }

    @Test
    void getPlayQuestions_AllQuestionsAnswered_ReturnsEmptyList() {
        // No unanswered questions anywhere, no review questions → empty result
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.50;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);

        User user = new User();
        user.setId(1L);
        user.setRating(1500);
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        for (QuestionDifficulty d : QuestionDifficulty.values()) {
            when(questionRepository.findUnansweredByDifficultyAndUserId(eq(d), anyLong()))
                            .thenReturn(Collections.emptyList());
        }
        when(questionRepository.findRandomDueReview(anyLong()))
                        .thenReturn(java.util.Optional.empty());

        List<Question> result = questionService.getPlayQuestions(user);

        assertEquals(0, result.size());
    }

    // ─── Target Difficulty Mapping ───────────────────────────────────────────────

    @Test
    void getPlayQuestions_SelectsCorrectDifficulty_ForEachRatingBracket() {
        // Roll = 0.50 → always lands in the 70% "target" branch
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.50;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);
        mockAllDifficultiesAvailable();

        User user = new User();
        user.setId(1L);
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        user.setRating(800); // BASIC (≤1000)
        assertEquals(QuestionDifficulty.BASIC, questionService.getPlayQuestions(user).get(0).getDifficulty());

        user.setRating(1200); // EASY (≤2000)
        assertEquals(QuestionDifficulty.EASY, questionService.getPlayQuestions(user).get(0).getDifficulty());

        user.setRating(2200); // MEDIUM (≤3000)
        assertEquals(QuestionDifficulty.MEDIUM, questionService.getPlayQuestions(user).get(0).getDifficulty());

        user.setRating(3200); // HARD (≤4000)
        assertEquals(QuestionDifficulty.HARD, questionService.getPlayQuestions(user).get(0).getDifficulty());

        user.setRating(4500); // EXTREME (>4000)
        assertEquals(QuestionDifficulty.EXTREME, questionService.getPlayQuestions(user).get(0).getDifficulty());
    }

    // ─── Probabilistic Branch Tests ──────────────────────────────────────────────

    @Test
    void getPlayQuestions_Roll50_SelectsTargetDifficulty() {
        // Roll = 0.50 → 50 < 70 → target branch
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.50;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);
        mockAllDifficultiesAvailable();

        User user = new User();
        user.setId(1L);
        user.setRating(2200); // MEDIUM target
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.MEDIUM, result.get(0).getDifficulty());
    }

    @Test
    void getPlayQuestions_Roll75_SelectsEasierDifficulty() {
        // Roll = 0.75 → 75 is >= 70 and < 85 → easier branch
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.75;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);

        User user = new User();
        user.setId(1L);
        user.setRating(2200); // MEDIUM target → easier = EASY
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.EASY), anyLong()))
            .thenReturn(List.of(questionOf(QuestionDifficulty.EASY)));
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.MEDIUM), anyLong()))
            .thenReturn(Collections.emptyList());

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.EASY, result.get(0).getDifficulty());
    }

    @Test
    void getPlayQuestions_Roll90_SelectsHarderDifficulty() {
        // Roll = 0.90 → 90 >= 85 → harder branch
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.90;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);

        User user = new User();
        user.setId(1L);
        user.setRating(2200); // MEDIUM target → harder = HARD
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.HARD), anyLong()))
            .thenReturn(List.of(questionOf(QuestionDifficulty.HARD)));
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.MEDIUM), anyLong()))
            .thenReturn(Collections.emptyList());

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.HARD, result.get(0).getDifficulty());
    }

    // ─── Boundary Clamp Tests ────────────────────────────────────────────────────

    @Test
    void getPlayQuestions_AtLowestDifficulty_EasierRollClampsToBasic() {
        // Roll tries to go easier than BASIC — should clamp and still return BASIC
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.75;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);

        User user = new User();
        user.setId(1L);
        user.setRating(500); // BASIC — already lowest
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.BASIC), anyLong()))
            .thenReturn(List.of(questionOf(QuestionDifficulty.BASIC)));

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.BASIC, result.get(0).getDifficulty());
    }

    @Test
    void getPlayQuestions_AtHighestDifficulty_HarderRollClampsToExtreme() {
        // Roll tries to go harder than EXTREME — should clamp and still return EXTREME
        Random fixedRandom = new Random() {
            @Override
            public double nextDouble() {
                return 0.90;
            }
        };
        questionService = new QuestionService(questionRepository, fixedRandom, codeExecutionService);

        User user = new User();
        user.setId(1L);
        user.setRating(5000); // EXTREME — already highest
        com.reasonly.backend.User.UserSettings.UserSettings settingsTest = new com.reasonly.backend.User.UserSettings.UserSettings();
        settingsTest.setInterests(List.of(QuestionTopic.DATABASES));
        user.setUserSettings(settingsTest);

        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.EXTREME), anyLong()))
            .thenReturn(List.of(questionOf(QuestionDifficulty.EXTREME)));

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.EXTREME, result.get(0).getDifficulty());
    }
}
