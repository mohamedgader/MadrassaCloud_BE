package com.ESanteManagerApplication.Enities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "role")
@RequiredArgsConstructor
@Data
public abstract class Utilisateur {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomComplet;
    private String email;
    private String motDePasseHash;

    @Enumerated(EnumType.STRING)
    private RoleUtilisateur role;
    private boolean isActive;
    @Column
    private String verificationCode;
    private LocalDateTime verificationCodeExpiry;


    private String resetCode;
    private LocalDateTime resetCodeExpiry;


    @ManyToOne
    private Site site;
}