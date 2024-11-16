package com.example.projetdevv1.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idR")
public class Repas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idR;

    private String titre;

    @Lob
    private String recette;


    private double prix;

    @OneToMany(mappedBy = "repas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingrédient> ingrédients;

    @ManyToOne
    @JoinColumn(name = "categorie_id", nullable = false, referencedColumnName = "id_categ")
    private Categorie categorie; // Relier le repas à une catégorie existante

    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
