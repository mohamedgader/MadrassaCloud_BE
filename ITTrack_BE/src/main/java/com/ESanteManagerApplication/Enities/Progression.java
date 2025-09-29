package com.ESanteManagerApplication.Enities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Entity
@RequiredArgsConstructor
@Data
public class Progression {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sourate;
    private int versetDebut;
    private int versetFin;
    private LocalDate dateApprentissage;

    @ManyToOne
    private Etudiant etudiant;
}
