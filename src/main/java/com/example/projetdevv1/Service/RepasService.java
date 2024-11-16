package com.example.projetdevv1.Service;

import com.example.projetdevv1.Entities.Categorie;
import com.example.projetdevv1.Entities.Repas;
import com.example.projetdevv1.Entities.User;
import com.example.projetdevv1.Repository.CategorieRepository;
import com.example.projetdevv1.Repository.RepasRepository;
import com.example.projetdevv1.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RepasService {

    @Autowired
    private RepasRepository repasRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategorieRepository categorieRepository;

    public List<Repas> getAllRepas() {
        return repasRepository.findAll();
    }

    public List<Repas> getRepasByCategorie(int idC) {
        return repasRepository.findByCategorie_IdC(idC);
    }

    public Repas createRepas(Repas repas, Long user_id, int categorie_id) {
        User user = userRepository.findById(user_id).orElseThrow(() -> new RuntimeException("User not found"));
        repas.setCreatedBy(user);

        Categorie categorie = categorieRepository.findById(categorie_id)
                .orElseThrow(() -> new IllegalArgumentException("Catégorie non trouvée"));

        repas.setCategorie(categorie);

        return repasRepository.save(repas);
    }

    public Optional<Repas> updateRepas(int id, Repas repas) {
        Optional<Repas> existingRepas = repasRepository.findById(id);

        if (existingRepas.isPresent()) {
            Repas repasToUpdate = existingRepas.get();
            repasToUpdate.setTitre(repas.getTitre());
            repasToUpdate.setRecette(repas.getRecette());
            repasToUpdate.setPrix(repas.getPrix());
            repasToUpdate.setIngrédients(repas.getIngrédients());
            repasToUpdate.setCommentaire(repas.getCommentaire());

            Categorie categorie = repas.getCategorie();
            if (categorie != null) {
                Optional<Categorie> existingCategory = categorieRepository.findById(categorie.getIdC());
                if (existingCategory.isEmpty()) {
                    categorie = categorieRepository.save(categorie);
                }
                repasToUpdate.setCategorie(categorie);
            }

            return Optional.of(repasRepository.save(repasToUpdate));
        }

        return Optional.empty();
    }

    public void deleteRepas(int id) {
        repasRepository.deleteById(id);
    }
}
