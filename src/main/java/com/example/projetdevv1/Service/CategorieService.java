package com.example.projetdevv1.Service;

import com.example.projetdevv1.Entities.Categorie;
import com.example.projetdevv1.Repository.CategorieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategorieService {

    @Autowired
    private CategorieRepository categorieRepository;

    public List<Categorie> getAllCategories() {
        return categorieRepository.findAll();
    }

    public Categorie createCategorie(Categorie categorie) {
        return categorieRepository.save(categorie);
    }

    public Optional<Categorie> getCategorieById(int id) {
        return categorieRepository.findById(id);
    }

    public Optional<Categorie> updateCategorie(int id, Categorie updatedCategorie) {
        Optional<Categorie> existingCategorie = categorieRepository.findById(id);

        if (existingCategorie.isPresent()) {
            Categorie categorieToUpdate = existingCategorie.get();
            categorieToUpdate.setTitreC(updatedCategorie.getTitreC());
            categorieToUpdate.setImage(updatedCategorie.getImage());
            categorieToUpdate.setCouleur(updatedCategorie.getCouleur());

            return Optional.of(categorieRepository.save(categorieToUpdate));
        }

        return Optional.empty();
    }

    public void deleteCategorie(int id) {
        Categorie categorie = categorieRepository.findById(id).orElseThrow(() -> new RuntimeException("Categorie non trouvée"));
        categorieRepository.delete(categorie);
    }
}
