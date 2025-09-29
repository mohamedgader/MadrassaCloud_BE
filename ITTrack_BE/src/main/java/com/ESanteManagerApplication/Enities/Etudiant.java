package com.ESanteManagerApplication.Enities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@RequiredArgsConstructor
@Data
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomComplet;
    private LocalDate dateNaissance;

    @ManyToOne
    private Site site;

    @ManyToOne
    private Classe classe;

    @OneToMany(mappedBy = "etudiant")
    private List<Progression> progressions = new ArrayList<>();
}
