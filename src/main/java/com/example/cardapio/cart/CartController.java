package com.example.cardapio.cart;

import com.example.cardapio.food.Food;
import com.example.cardapio.food.FoodRepository;
import com.example.cardapio.user.User;
import com.example.cardapio.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartRepository cartRepository;
    private final FoodRepository foodRepository;
    private final UserRepository userRepository;

    public CartController(CartRepository cartRepository, FoodRepository foodRepository, UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.foodRepository = foodRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponseDTO>> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        List<CartItem> items = cartRepository.findByUser(user);
        List<CartItemResponseDTO> response = items.stream()
                .map(CartItemResponseDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CartItemResponseDTO> addToCart(@AuthenticationPrincipal UserDetails userDetails,
                                                         @RequestBody CartItemRequestDTO data) {
        User user = (User) userDetails;
        Food food = foodRepository.findById(data.foodId()).orElse(null);
        
        if (food == null) {
            return ResponseEntity.badRequest().build();
        }

        List<CartItem> existingItems = cartRepository.findByUser(user);
        CartItem cartItem = existingItems.stream()
                .filter(item -> item.getFood().getId() == data.foodId())
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + data.quantity());
        } else {
            cartItem = new CartItem(food, user, data.quantity());
        }

        cartRepository.save(cartItem);
        return ResponseEntity.ok(new CartItemResponseDTO(cartItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartItemResponseDTO> updateQuantity(@AuthenticationPrincipal UserDetails userDetails,
                                                             @PathVariable Long id,
                                                             @RequestBody CartItemRequestDTO data) {
        User user = (User) userDetails;
        CartItem cartItem = cartRepository.findById(id).orElse(null);
        
        if (cartItem == null || cartItem.getUser().getId() != user.getId()) {
            return ResponseEntity.notFound().build();
        }

        cartItem.setQuantity(data.quantity());
        cartRepository.save(cartItem);
        return ResponseEntity.ok(new CartItemResponseDTO(cartItem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeFromCart(@AuthenticationPrincipal UserDetails userDetails,
                                               @PathVariable Long id) {
        User user = (User) userDetails;
        CartItem cartItem = cartRepository.findById(id).orElse(null);
        
        if (cartItem == null || cartItem.getUser().getId() != user.getId()) {
            return ResponseEntity.notFound().build();
        }

        cartRepository.delete(cartItem);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal UserDetails userDetails) {
        User user = (User) userDetails;
        cartRepository.deleteByUser(user);
        return ResponseEntity.noContent().build();
    }
}
