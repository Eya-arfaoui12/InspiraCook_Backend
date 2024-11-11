package com.example.projetdevv1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Ingrédient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idi;

    private String nom;
    private int quantité;
    private int prix;

    @ManyToOne
    @JoinColumn(name = "repas_id")
    private Repas repas;

    public Ingrédient(int idi, String nom, int quantité, int prix) {
        this.idi = idi;
        this.nom = nom;
        this.quantité = quantité;
        this.prix = prix;
    }

    public Ingrédient() {}
}
