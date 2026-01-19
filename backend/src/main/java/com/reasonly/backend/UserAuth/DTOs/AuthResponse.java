package com.reasonly.backend.UserAuth.DTOs;

import com.reasonly.backend.User.User;

public record AuthResponse(String token, User user) {}

