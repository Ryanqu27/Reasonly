package com.reasonly.backend.Question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reasonly.backend.User.User;

class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        questionService = new QuestionService(questionRepository);
    }

    @Test
    void getPlayQuestions_FallbackToAny_WhenTargetEmpty() {
        User user = new User();
        user.setId(1L);
        user.setRating(1500); // EASY target

        // Mock empty for EASY
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.EASY), anyLong()))
                .thenReturn(Collections.emptyList());

        // Mock success for BASIC
        Question basicQuestion = new Question();
        basicQuestion.setDifficulty(QuestionDifficulty.BASIC);
        when(questionRepository.findUnansweredByDifficultyAndUserId(eq(QuestionDifficulty.BASIC), anyLong()))
                .thenReturn(List.of(basicQuestion));

        List<Question> result = questionService.getPlayQuestions(user);

        assertFalse(result.isEmpty());
        assertEquals(QuestionDifficulty.BASIC, result.get(0).getDifficulty());
    }
}
