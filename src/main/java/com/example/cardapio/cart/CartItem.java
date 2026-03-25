package com.example.cardapio.cart;

import com.example.cardapio.food.Food;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cart_items")
@Table(name = "cart_items")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Integer quantity;

    public CartItem(Food food, Integer quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Integer getSubtotal() {
        return food.getPrice() * quantity;
    }
}
