package com.reasonly.backend.User;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")  
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private int currentStreak;
    private int longestStreak;

    private LocalDate lastCompetedDate;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getCurrentStreak() {
        return currentStreak;
    }

    public int getLongestStreak() {
        return longestStreak;
    }

    public LocalDate getLastCompletedDate() {
        return lastCompetedDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long newId) {
        id = newId;
    } 

    public void setEmail(String newEmail) {
        email = newEmail;
    }

    public void setPasswordHash(String newPasswordHash) {
        passwordHash = newPasswordHash;
    }

    public void setCurrentStreak(int newCurrentStreak) {
        currentStreak = newCurrentStreak;
    }

    public void setLongestStreak(int newLongestStreak) {
        longestStreak = newLongestStreak;
    }

    public void setLastCompletedDate(LocalDate newLastCompletedDate) {
        lastCompetedDate = newLastCompletedDate;
    }

    public void setCreatedAt(LocalDateTime newCreatedAt) {
        createdAt = newCreatedAt;
    }

    
}
