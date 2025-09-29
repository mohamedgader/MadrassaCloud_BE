package com.ESanteManagerApplication.Enities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @ManyToOne
    private Site site;

    @ManyToMany
    private List<Enseignant> enseignants = new ArrayList<>();

    @OneToMany(mappedBy = "classe")
    private List<Etudiant> etudiants = new ArrayList<>();
}