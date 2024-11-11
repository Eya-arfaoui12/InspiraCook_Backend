package com.example.projetdevv1.Service;

import com.example.projetdevv1.Entities.Repas;
import com.example.projetdevv1.Entities.User;
import com.example.projetdevv1.Repository.RepasRepository;
import com.example.projetdevv1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class RepasService {


    @Autowired
    private RepasRepository repasRepository;
    @Autowired
    private UserRepository userRepository;


    public List<Repas> getAllRepas() {
        return repasRepository.findAll();
    }


    public Repas createRepas(Repas repas, Long user_id) {

        User user= (User) userRepository.findById(user_id).orElseThrow(()-> new RuntimeException("User not found"));
        repas.setCreatedBy(user);
        Repas savedRepas = repasRepository.save(repas);
        return savedRepas;
    }
    public Optional<Repas> updateRepas(int id, Repas repas) {
        // Trouver le repas existant par ID
        Optional<Repas> existingRepas = repasRepository.findById(id);

        if (existingRepas.isPresent()) {
            Repas repasToUpdate = existingRepas.get();

            // Mettre à jour les attributs du repas
            repasToUpdate.setTitre(repas.getTitre());
            repasToUpdate.setRecette(repas.getRecette());
            repasToUpdate.setIngrédients(repas.getIngrédients());
            repasToUpdate.setCategorie(repas.getCategorie());
            repasToUpdate.setCommentaire(repas.getCommentaire());

            // Sauvegarder les modifications
            repasRepository.save(repasToUpdate);

            // Retourner le repas mis à jour
            return Optional.of(repasToUpdate);
        } else {
            // Si le repas n'existe pas, retourner Optional.empty()
            return Optional.empty();
        }
    }

    public void deleteRepas(int id) {
//        Optional<Repas> repas = repasRepository.findById(id);
//
//        if (repas.isPresent()) {
//            // Vérifier si l'utilisateur est le créateur du repas ou s'il a un rôle de CHEF
//            if (repas.get().getCreatedBy().getId().equals(userId) || userRole.equals("CHEF")) {
//                repasRepository.deleteById(id);
//                return true; // Supprimé avec succès
//            } else {
//                // L'utilisateur n'a pas les droits nécessaires pour supprimer ce repas
//                return false;
//            }
//        }
//
//        return false; // Repas non trouvé
        repasRepository.deleteById((id));
    }


}




