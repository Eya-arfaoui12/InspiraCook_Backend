package com.example.projetdevv1.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Entity
@Getter
@Setter
public class Repas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idR;

    private String titre;

    @Lob
    private String recette;

    
    @OneToMany(mappedBy = "repas", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ingrédient> ingrédients;

    @ManyToOne
    @JoinColumn(name = "categorie_id")
    private Categorie categorie;

    private String commentaire;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User createdBy;
}
