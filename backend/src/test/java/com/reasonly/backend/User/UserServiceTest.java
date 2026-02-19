package com.reasonly.backend.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
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

    // Helper: creates a user with a given streak and last completed date
    private User userWithStreak(int streak, LocalDate lastCompleted) {
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(streak);
        user.setLongestStreak(streak);
        user.setLastCompletedDate(lastCompleted);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        return user;
    }


    @Test
    void incrementStreak_NewDay_IncrementsStreak() {
        User user = userWithStreak(5, LocalDate.now().minusDays(1));

        userService.incrementStreak(1L);

        assertEquals(6, user.getCurrentStreak());
        assertEquals(LocalDate.now(), user.getLastCompletedDate());
    }

    @Test
    void incrementStreak_MissedDay_ResetsStreakToOne() {
        User user = userWithStreak(5, LocalDate.now().minusDays(2));

        userService.incrementStreak(1L);

        assertEquals(1, user.getCurrentStreak());
        assertEquals(LocalDate.now(), user.getLastCompletedDate());
    }

    @Test
    void incrementStreak_SameDay_DoesNothing() {
        User user = userWithStreak(5, LocalDate.now());

        userService.incrementStreak(1L);

        assertEquals(5, user.getCurrentStreak());
    }

    @Test
    void incrementStreak_NewRecord_UpdatesLongestStreak() {
        // Current streak = 10 = longest streak → after increment, new longest = 11
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(10);
        user.setLongestStreak(10);
        user.setLastCompletedDate(LocalDate.now().minusDays(1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(11, user.getCurrentStreak());
        assertEquals(11, user.getLongestStreak());
    }

    @Test
    void incrementStreak_BelowRecord_DoesNotUpdateLongestStreak() {
        // Current streak = 3, longest streak = 10 → after increment, longest stays 10
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(3);
        user.setLongestStreak(10);
        user.setLastCompletedDate(LocalDate.now().minusDays(1));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(4, user.getCurrentStreak());
        assertEquals(10, user.getLongestStreak()); // unchanged
    }

    @Test
    void incrementStreak_NullLastCompletedDate_StartsStreakAtOne() {
        // First time user ever completes a question — no prior date
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(0);
        user.setLastCompletedDate(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.incrementStreak(1L);

        assertEquals(1, user.getCurrentStreak());
        assertEquals(LocalDate.now(), user.getLastCompletedDate());
    }

    @Test
    void incrementStreak_UserNotFound_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.incrementStreak(99L));
    }


    @Test
    void checkStreak_LastCompletedToday_StreakUnchanged() {
        User user = userWithStreak(5, LocalDate.now());

        userService.checkStreak(1L);

        assertEquals(5, user.getCurrentStreak());
    }

    @Test
    void checkStreak_LastCompletedYesterday_StreakUnchanged() {
        User user = userWithStreak(5, LocalDate.now().minusDays(1));

        userService.checkStreak(1L);

        assertEquals(5, user.getCurrentStreak()); // still valid — yesterday counts
    }

    @Test
    void checkStreak_LastCompletedTwoDaysAgo_ResetsStreakToZero() {
        User user = userWithStreak(5, LocalDate.now().minusDays(2));

        userService.checkStreak(1L);

        assertEquals(0, user.getCurrentStreak());
    }

    @Test
    void checkStreak_NullLastCompletedDate_ResetsStreakToZero() {
        User user = new User();
        user.setId(1L);
        user.setCurrentStreak(3);
        user.setLastCompletedDate(null);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.checkStreak(1L);

        assertEquals(0, user.getCurrentStreak());
    }


    @Test
    void getUserById_UserExists_ReturnsUser() {
        User mockUser = new User();
        mockUser.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(1L);

        assertEquals(1L, result.getId());
        verify(userRepository).findById(1L);
    }

    @Test
    void getUserById_UserNotFound_ThrowsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
    }
}
