package com.reasonly.backend.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    void incrementStreak_NewDay_IncrementsStreak() {
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(5);
        user.setLastCompletedDate(LocalDate.now().minusDays(1));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(6, user.getCurrentStreak());
        assertEquals(LocalDate.now(), user.getLastCompletedDate());
    }

    @Test
    void incrementStreak_MissedDay_ResetsStreak() {
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(5);
        user.setLastCompletedDate(LocalDate.now().minusDays(2));

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(1, user.getCurrentStreak());
        assertEquals(LocalDate.now(), user.getLastCompletedDate());
    }

    @Test
    void incrementStreak_SameDay_DoesNothing() {
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(5);
        user.setLastCompletedDate(LocalDate.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(5, user.getCurrentStreak());
    }
}
