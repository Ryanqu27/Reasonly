package com.reasonly.backend.User;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    User testUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        testUser = new User();
        testUser.setEmail("test@gmail.com");
        testUser.setPasswordHash("password");
        testUser = userRepository.save(testUser);
        SecurityContextHolder.getContext().setAuthentication(
            new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities()));
    }

    @Test
    void getCurrentUser_ReturnsCurrentUser() throws Exception {
        mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"));
    }

    @Test
    void getUserProfile_ReturnsUserProfile() throws Exception {
        mockMvc.perform(get("/api/user/profile"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.currentStreak").value(0))
            .andExpect(jsonPath("$.createdAt").value(testUser.getCreatedAt().toString()))
            .andExpect(jsonPath("$.rating").value(0));
    }

    @Test
    void onBoardUser_UpdatesUser() throws Exception {
        OnboardRequest request = new OnboardRequest(
        UserExperience.BEGINNER,
        UserMotivation.INTERVIEW_PREP,
        UserLanguage.JAVA,
        List.of(UserInterest.DATA_STRUCTURES_AND_ALGORITHMS, UserInterest.DATABASES)
        );

        mockMvc.perform(put("/api/user/" + testUser.getId() + "/onboard")
            .contentType("application/json")
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk());

        mockMvc.perform(get("/api/user/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.email").value("test@gmail.com"))
            .andExpect(jsonPath("$.experience").value("BEGINNER"))
            .andExpect(jsonPath("$.motivation").value("INTERVIEW_PREP"))
            .andExpect(jsonPath("$.preferredLanguage").value("JAVA"))
            .andExpect(jsonPath("$.interests").isArray())
            .andExpect(jsonPath("$.interests.length()").value(2))
            .andExpect(jsonPath("$.interests[0]").value("DATA_STRUCTURES_AND_ALGORITHMS"))
            .andExpect(jsonPath("$.interests[1]").value("DATABASES"));
    }
}
