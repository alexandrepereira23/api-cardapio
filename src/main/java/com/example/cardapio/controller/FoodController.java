package com.example.cardapio.controller;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.food.FoodRequestDTO;
import com.example.cardapio.food.FoodResponseDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

    @PostMapping
    public ResponseEntity<Void> saveFood(@Valid @RequestBody FoodRequestDTO data){
        Food foodDate = new Food(data);
        foodRepository.save(foodDate);
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public List<FoodResponseDTO> getAllFood() {
        List<FoodResponseDTO> foodList = foodRepository.findAll()
                .stream()
                .map(FoodResponseDTO::new)
                .toList();
        return foodList;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        foodRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateFood(@PathVariable Long id, @Valid @RequestBody FoodRequestDTO data) {
        Food food = foodRepository.findById(id).orElse(null);
        if (food != null) {
            food.setTitle(data.title());
            food.setImage(data.image());
            food.setPrice(data.price());
            foodRepository.save(food);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
