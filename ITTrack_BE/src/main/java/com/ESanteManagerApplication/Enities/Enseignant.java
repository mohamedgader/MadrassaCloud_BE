package com.ESanteManagerApplication.Enities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ENSEIGNANT")
@RequiredArgsConstructor
@Data
public class Enseignant extends Utilisateur {
    @ManyToMany(mappedBy = "enseignants")
    private List<Classe> classes = new ArrayList<>();
}