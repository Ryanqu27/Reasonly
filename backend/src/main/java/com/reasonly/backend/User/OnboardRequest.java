package com.reasonly.backend.User;

// Get initial information from user like experience, preferences, age, etc.
public record OnboardRequest(UserExperience experience) {}
