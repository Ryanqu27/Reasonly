package com.reasonly.backend.Question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.reasonly.backend.User.User;
import com.reasonly.backend.User.UserRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class QuestionIntegrationTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private UserRepository userRepository;

   @BeforeEach
   void setUp() {
      userRepository.deleteAll(); // Clean up before each test to prevent unique constraint violations

      User testUser = new User();
      testUser.setEmail("test@gmail.com");
      testUser.setPasswordHash("password");
      testUser = userRepository.save(testUser); // Save and get the generated ID
   }

   @Test
   @WithMockUser
   void getAllQuestions_ReturnsSuccessAndData() throws Exception {
      mockMvc.perform(get("/api/questions"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").isArray());
   }

   @Test
   void getPlayQuestions_RequiresAuthentication() throws Exception {
      // Without any authentication, the endpoint should return 403 Forbidden
      mockMvc.perform(get("/api/questions/play"))
         .andExpect(status().isForbidden());
   }

   @Test
   @WithMockUser
   void getPlayQuestions_ReturnsSuccessAndData() throws Exception {
      User testUser = userRepository.findByEmail("test@gmail.com").orElseThrow();
      SecurityContextHolder.getContext().setAuthentication(
         new UsernamePasswordAuthenticationToken(testUser, null, testUser.getAuthorities()));
      mockMvc.perform(get("/api/questions/play"))
         .andExpect(status().isOk())
         .andExpect(jsonPath("$").isArray());
   }
}
