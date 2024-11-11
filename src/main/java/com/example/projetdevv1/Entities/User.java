package com.example.projetdevv1.Entities;

import com.example.projetdevv1.Enum.Role_Enum;
import jakarta.persistence.*;
import lombok.*;



import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String sex;
    private String phoneNumber;
    private String dateOfBirth;
    private Role_Enum role;
    private String verificationToken;
    private boolean verified;
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Repas> createdRepas;

}
