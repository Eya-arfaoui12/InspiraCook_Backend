package com.example.projetdevv1.Repository;

import com.example.projetdevv1.Entities.Ingrédient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingrédient, Integer> {

}
