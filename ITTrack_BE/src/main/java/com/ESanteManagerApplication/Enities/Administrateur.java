package com.ESanteManagerApplication.Enities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@DiscriminatorValue("ADMIN")
@RequiredArgsConstructor
@Data
public class Administrateur extends Utilisateur {
    private boolean estAdminGlobal;
}
