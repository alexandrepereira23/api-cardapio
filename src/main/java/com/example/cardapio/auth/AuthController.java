package com.example.cardapio.auth;

import com.example.cardapio.security.TokenService;
import com.example.cardapio.user.User;
import com.example.cardapio.user.UserRepository;
import com.example.cardapio.user.UserRole;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          PasswordEncoder passwordEncoder, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);
        
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        User user = (User) userDetails;
        
        String token = tokenService.generateToken(userDetails);
        
        return ResponseEntity.ok(new AuthResponseDTO(token, user.getId(), user.getLogin(), user.getRole()));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO data) {
        if (userRepository.findByLogin(data.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        UserRole role = UserRole.USER;
        if (data.role() != null && data.role().equalsIgnoreCase("ADMIN")) {
            role = UserRole.ADMIN;
        }

        User newUser = new User(data.login(), passwordEncoder.encode(data.password()), role);
        userRepository.save(newUser);

        String token = tokenService.generateToken(newUser);

        return ResponseEntity.ok(new AuthResponseDTO(token, newUser.getId(), newUser.getLogin(), newUser.getRole()));
    }
}
