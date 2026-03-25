package com.example.cardapio.food;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record FoodRequestDTO(
    @NotBlank(message = "Título é obrigatório")
    String title,
    @NotBlank(message = "URL da imagem é obrigatória")
    String image,
    @NotNull(message = "Preço é obrigatório")
    @Positive(message = "Preço deve ser positivo")
    Integer price
) {}
