package com.example.projetdevv1.Controller;

import com.example.projetdevv1.DTO.JwtResponse;
import com.example.projetdevv1.DTO.LoginRequest;
import com.example.projetdevv1.Entities.User;
import com.example.projetdevv1.Enum.Role_Enum;
import com.example.projetdevv1.Repository.UserRepository;
import com.example.projetdevv1.Security.Jwt.JwtUtils;
import com.example.projetdevv1.Security.Services.UserDetailsImpl;
import com.example.projetdevv1.Service.EmailService;
import com.example.projetdevv1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;


    @PostMapping("/signin")
    public ResponseEntity<String> Login(@RequestBody LoginRequest loginRequest) {
       String jwt = userService.Login(loginRequest.getEmail(), loginRequest.getPassword());
       return ResponseEntity.ok(jwt);
    }
    // Registration method
//    @PostMapping("/register")
//    public ResponseEntity<?> registerUser(@RequestBody User user) {
//        if (userRepository.existsByEmail(user.getEmail())) {
//            return ResponseEntity.badRequest().body("Error: Email is already in use!");
//        }
//
//        // Check if the role is either CLIENT or CHEF
//        if (user.getRole() != Role_Enum.Client && user.getRole() != Role_Enum.Chef) {
//            return ResponseEntity.badRequest().body("Error: Invalid role selected! Only 'Client' or 'Chef' roles are allowed.");
//        }
//
//        // Encode the password
//        user.setPassword(encoder.encode(user.getPassword()));
//
//        // Save the user with the chosen role
//        userRepository.save(user);
//
//        return ResponseEntity.ok("User registered successfully!");
//    }
    // Registration method with email verification
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        if (userService.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body("Error: Email is already in use!");
        }

        // Check if the role is either CLIENT or CHEF
       if (user.getRole() != Role_Enum.ROLE_CLIENT && user.getRole() != Role_Enum.ROLE_CHEF) {
         return ResponseEntity.badRequest().body("Error: Invalid role selected! Only 'Client' or 'Chef' roles are allowed.");
       }

        // Encode the password
        user.setPassword(encoder.encode(user.getPassword()));

        // Generate verification token
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setVerified(false); // Account is initially not verified

        // Save the user with the chosen role and verification token
        userService.saveUser(user);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), token);

        return ResponseEntity.ok("User registered successfully! Check your email for verification.");
    }

    // Endpoint to handle account verification
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("token") String token) {
        boolean verified = userService.verifyUser(token);
        if (verified) {
            return ResponseEntity.ok("Account verified successfully!");
        } else {
            return ResponseEntity.badRequest().body("Error: Invalid verification token.");
        }
    }
}