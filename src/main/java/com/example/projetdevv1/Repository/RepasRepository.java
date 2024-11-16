package com.example.projetdevv1.Repository;



import com.example.projetdevv1.Entities.Repas; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepasRepository extends JpaRepository<Repas, Integer> {
    List<Repas> findByCategorie_IdC(int idC);

}
