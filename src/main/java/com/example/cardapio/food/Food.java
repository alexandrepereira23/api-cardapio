package com.example.cardapio.food;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "foods")
@Entity(name = "foods")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    @Column(length = 1000)
    private String image;
    private Double price;

    public Food(FoodRequestDTO data) {
        this.image = data.image();
        this.title = data.title();
        this.price = data.price();
    }
}
