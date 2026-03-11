package com.reasonly.backend.QuestionAttempt;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reasonly.backend.Question.Question;
import com.reasonly.backend.Question.QuestionDifficulty;
import com.reasonly.backend.Question.QuestionRepository;
import com.reasonly.backend.Question.QuestionType;
import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionAttemptIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionAttemptRepository questionAttemptRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Question testQuestion;

    @BeforeEach
    void setUp() {
        questionAttemptRepository.deleteAll();
        userRepository.deleteAll();
        testUser = new User();
        testUser.setEmail("test@gmail.com");
        testUser.setPasswordHash("password");
        testUser = userRepository.save(testUser);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities()));

    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_CorrectAnswer_ReturnsSuccessAndData() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        testQuestion.setCorrectAnswer(List.of("correct"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("correct"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_IncorrectAnswer_ReturnsSuccessAndData() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        testQuestion.setCorrectAnswer(List.of("correct"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("incorrect"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(false))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_SelectAll_ReturnsSuccess() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.SELECT_ALL);
        testQuestion.setCorrectAnswer(List.of("correct1", "correct2"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("correct1", "correct2"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }


    @Test
    @WithMockUser
    void insertQuestionAttempt_SelectAllOutOfOrder_ReturnsSuccess() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.SELECT_ALL);
        testQuestion.setCorrectAnswer(List.of("correct1", "correct2"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("correct2", "correct1"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }



    @Test
    @WithMockUser
    void insertQuestionAttempt_SelectAllIncorrect_ReturnsFailure() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.SELECT_ALL);
        testQuestion.setCorrectAnswer(List.of("correct1", "correct2"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("incorrect1", "correct2"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(false))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_FindTheBugCorrect_ReturnsSuccess() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.FIND_THE_BUG);
        testQuestion.setCorrectAnswer(List.of("2"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("2"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_FindTheBugIncorrect_ReturnsFailure() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.FIND_THE_BUG);
        testQuestion.setCorrectAnswer(List.of("2"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("1"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(false))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_FillInTheBlankCorrect_ReturnsSuccess() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.FILL_IN_THE_BLANK);
        testQuestion.setCorrectAnswer(List.of("i"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("i"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(true))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }

    @Test
    @WithMockUser
    void insertQuestionAttempt_FillInTheBlankIncorrect_ReturnsFailure() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.FILL_IN_THE_BLANK);
        testQuestion.setCorrectAnswer(List.of("i"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("j"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(false))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());
    }
    
    @Test
    @WithMockUser
    void resetQuestionAttempts_ReturnsSuccess() throws Exception {
        testQuestion = new Question();
        testQuestion.setDifficulty(QuestionDifficulty.EASY);
        testQuestion.setType(QuestionType.MULTIPLE_CHOICE);
        testQuestion.setCorrectAnswer(List.of("correct"));
        testQuestion = questionRepository.save(testQuestion);

        QuestionAttemptRequest request = new QuestionAttemptRequest();
        request.setUserId(testUser.getId());
        request.setQuestionId(testQuestion.getId());
        request.setAnswer(List.of("invalid"));

        mockMvc.perform(post("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.correct").value(false))
            .andExpect(jsonPath("$.ratingChange").isNumber())
            .andExpect(jsonPath("$.newRating").isNumber());

        mockMvc.perform(delete("/api/question-attempts/reset/" + testUser.getId())
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk());

        mockMvc.perform(get("/api/question-attempts")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isEmpty());
    }
}
