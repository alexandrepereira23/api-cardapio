package com.example.cardapio.auth;

import com.example.cardapio.user.UserRole;

public record AuthResponseDTO(
    String token,
    Long userId,
    String login,
    UserRole role
) {}
