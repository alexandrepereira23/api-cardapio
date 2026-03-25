package com.example.cardapio.cart;

public record CartItemResponseDTO(
    Long id,
    Long foodId,
    String foodTitle,
    Double foodPrice,
    String foodImage,
    Integer quantity,
    Double subtotal
) {
    public CartItemResponseDTO(CartItem item) {
        this(
            item.getId(),
            item.getFood().getId(),
            item.getFood().getTitle(),
            item.getFood().getPrice(),
            item.getFood().getImage(),
            item.getQuantity(),
            item.getSubtotal()
        );
    }
}
