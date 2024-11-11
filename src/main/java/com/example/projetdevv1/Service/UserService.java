package com.example.projetdevv1.Service;

import com.example.projetdevv1.DTO.JwtResponse;
import com.example.projetdevv1.Entities.User;
import com.example.projetdevv1.Enum.Role_Enum;
import com.example.projetdevv1.Repository.UserRepository;
import com.example.projetdevv1.Security.Jwt.JwtUtils;
import com.example.projetdevv1.Security.Services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    JwtUtils jwtUtils;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setFirstname(updatedUser.getFirstname());
                    user.setLastname(updatedUser.getLastname());
                    user.setEmail(updatedUser.getEmail());
                    user.setPassword(updatedUser.getPassword());
                    user.setSex(updatedUser.getSex());
                    user.setPhoneNumber(updatedUser.getPhoneNumber());
                    user.setDateOfBirth(updatedUser.getDateOfBirth());
                    user.setRole(updatedUser.getRole());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User assignUserRole(Long id, Role_Enum newRole) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Utilisateur non trouvé.");
        }

        User user = optionalUser.get();
        user.setRole(newRole);
        return userRepository.save(user);
    }
    public boolean verifyUser(String token) {
        Optional<User> optionalUser = userRepository.findByVerificationToken(token);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setVerified(true);
            user.setVerificationToken(null); // Clear the verification token after verification
            userRepository.save(user);
            return true;
        }
        return false;
    }


    public String Login(String email , String password){
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            return jwt;

    }
}

