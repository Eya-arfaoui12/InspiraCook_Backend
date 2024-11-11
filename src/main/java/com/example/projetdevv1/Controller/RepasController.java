package com.example.projetdevv1.Controller;

import com.example.projetdevv1.Entities.Repas;
import com.example.projetdevv1.Security.Jwt.JwtUtils;
import com.example.projetdevv1.Service.RepasService; // Assurez-vous que votre service est importé
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/repas")
public class RepasController {

    @Autowired
    private RepasService repasService;

    @Autowired
    private JwtUtils jwtUtils;


    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
     public ResponseEntity<List<Repas>> getAllRepas() {
        List<Repas> repasList = repasService.getAllRepas();
        if (repasList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(repasList);
    }


    @PostMapping("/createRepas")
   // @PreAuthorize("hasRole('Chef')")
    public ResponseEntity<Repas> createRepas(@RequestBody Repas repas, @RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtUtils.extractUserId(token);
        Repas createdRepas = repasService.createRepas(repas,userId);
        return ResponseEntity.status(201).body(createdRepas);
    }

    //@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @PostMapping("/updateRepas/{id}")
    public ResponseEntity<Repas> updateRepas(@PathVariable("id") int id, @RequestBody Repas repas, @RequestHeader("Authorization") String authHeader) {
        // Extraire le token JWT si nécessaire
        String token = authHeader.replace("Bearer ", "");
        Long userId = jwtUtils.extractUserId(token); // Utilisé si nécessaire pour vérifier l'utilisateur

        // Appeler le service pour mettre à jour le repas
        Optional<Repas> updatedRepas = repasService.updateRepas(id, repas);

        return updatedRepas.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @DeleteMapping("/deleteRepas/{id}")
    public void deleteRepas(@PathVariable("id") int id) {
//        // Extraire le token JWT de l'en-tête Authorization
//        String token = authHeader.replace("Bearer ", "");
//        Long userId = jwtUtils.extractUserId(token);
//        String userRole = jwtUtils.extractUserRole(token); // Utilisez maintenant cette méthode
//
//
//        // Appeler le service pour supprimer le repas
//        if (repasService.deleteRepas(id, userId, userRole)) {
//            return ResponseEntity.noContent().build(); // 204 No Content
//        } else {
//
//           return ResponseEntity.status(HttpStatus.FORBIDDEN).build(); // 403 Forbidden}
        repasService.deleteRepas(id);
        }
    }



