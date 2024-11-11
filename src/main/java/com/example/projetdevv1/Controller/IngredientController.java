package com.example.projetdevv1.Controller;

import com.example.projetdevv1.Entities.Ingrédient;
import com.example.projetdevv1.Service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;


    @GetMapping
    public List<Ingrédient> getAllIngredients() {
        return ingredientService.getAllIngredients();
    }


    @GetMapping("/{id}")
    public ResponseEntity<Ingrédient> getIngredientById(@PathVariable int id) {
        Ingrédient ingrédient = ingredientService.getIngredientById(id);
        return ingrédient != null ? ResponseEntity.ok(ingrédient) : ResponseEntity.notFound().build();
    }


    @PostMapping
    public Ingrédient createIngredient(@RequestBody Ingrédient ingrédient) {
        return ingredientService.createIngredient(ingrédient);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Ingrédient> updateIngredient(@PathVariable int id, @RequestBody Ingrédient ingrédientDetails) {
        Ingrédient updatedIngredient = ingredientService.updateIngredient(id, ingrédientDetails);
        return updatedIngredient != null ? ResponseEntity.ok(updatedIngredient) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable int id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}
