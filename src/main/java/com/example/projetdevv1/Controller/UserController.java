package com.example.projetdevv1.Controller;

import com.example.projetdevv1.Entities.User;
import com.example.projetdevv1.Enum.Role_Enum;
import com.example.projetdevv1.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ADMIN: Peut récupérer tous les comptes
    @GetMapping
    @PreAuthorize("hasRole('Administrateur')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ADMIN, CHEF, CLIENT, VISITEUR: Peut créer un compte
    @PostMapping("/create")
    @PreAuthorize("hasRole('Administrateur')")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // ADMIN, CHEF, CLIENT: Peut modifier un compte
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('Administrateur', 'Chef', 'Client')")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // ADMIN: Peut supprimer un compte
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Administrateur')")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully!";
    }

    //  ADMIN: Peut récupérer un compte spécifique
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('Administrateur')")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }
    // Endpoint to assign a role to a user (only accessible by Administrateur)
    @PutMapping("/assign-role/{id}")
    @PreAuthorize("hasRole('Administrateur')")
    public ResponseEntity<?> assignUserRole(@PathVariable Long id, @RequestBody Role_Enum newRole) {
        try {
            User updatedUser = userService.assignUserRole(id, newRole);
            return ResponseEntity.ok("Rôle de l'utilisateur " + updatedUser.getFirstname() + " mis à jour avec succès !");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }
}

