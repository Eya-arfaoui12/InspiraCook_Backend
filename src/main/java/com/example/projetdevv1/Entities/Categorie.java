package com.example.projetdevv1.Entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "idC")
public class Categorie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categ")
    private int idC;

    @Column(name = "titreC") // S'assurer que le nom de la colonne est bien "titreC"
    private String titreC;

    @Column(name = "image") // L'attribut image pour l'URL ou le chemin de l'image
    private String image;

    @Column(name = "couleur") // L'attribut couleur pour le bouton
    private String couleur;

    @OneToMany(mappedBy = "categorie", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonManagedReference
    private List<Repas> repas;
}
