package com.ESanteManagerApplication.Enities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "site")
@Data
@RequiredArgsConstructor
public class Site {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String adresse;

    @OneToMany(mappedBy = "site")
    private List<Utilisateur> utilisateurs = new ArrayList<>();

    @OneToMany(mappedBy = "site")
    private List<Classe> classes = new ArrayList<>();

    @OneToMany(mappedBy = "site")
    private List<Etudiant> etudiants = new ArrayList<>();
}
