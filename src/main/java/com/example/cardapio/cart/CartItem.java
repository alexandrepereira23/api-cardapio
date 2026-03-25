package com.example.cardapio.cart;

import com.example.cardapio.food.Food;
import com.example.cardapio.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cart_items")
@Table(name = "cart_items")
@Getter
@Setter
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer quantity;

    public CartItem(Food food, User user, Integer quantity) {
        this.food = food;
        this.user = user;
        this.quantity = quantity;
    }

    public Double getSubtotal() {
        return food.getPrice() * quantity;
    }
}
