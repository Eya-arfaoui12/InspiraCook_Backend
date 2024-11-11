package com.example.projetdevv1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Entity
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int idCateg;
    private String titre_c;


    public Categorie(int idCateg, String titre_c) {
        this.idCateg = idCateg;
        this.titre_c = titre_c;
    }

    public Categorie() {

    }
}
