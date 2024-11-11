package com.example.projetdevv1.Service;

import com.example.projetdevv1.Entities.Ingrédient;
import com.example.projetdevv1.Repository.IngredientRepository;
import com.example.projetdevv1.Repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingrédientRepository;


    public List<Ingrédient> getAllIngredients() {
        return ingrédientRepository.findAll();
    }


    public Ingrédient getIngredientById(int id) {
        return ingrédientRepository.findById(id).orElse(null);
    }


    public Ingrédient createIngredient(Ingrédient ingrédient) {
        return ingrédientRepository.save(ingrédient);
    }


    public Ingrédient updateIngredient(int id, Ingrédient ingrédientDetails) {
        Ingrédient ingrédient = ingrédientRepository.findById(id).orElse(null);
        if (ingrédient != null) {
            ingrédient.setNom(ingrédientDetails.getNom());
            ingrédient.setQuantité(ingrédientDetails.getQuantité());
            ingrédient.setPrix(ingrédientDetails.getPrix());
            return ingrédientRepository.save(ingrédient);
        }
        return null;
    }


    public void deleteIngredient(int id) {
        ingrédientRepository.deleteById(id);
    }
}
