package com.example.cardapio.cart;

public record CartItemRequestDTO(
    Long foodId,
    Integer quantity
) {}
