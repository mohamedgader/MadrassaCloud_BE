package com.ESanteManagerApplication.Enities.Request;


import lombok.Data;



@Data
public class RegisterRequest {
    private String nomComplet;
    private String email;
    private String motDePasse;
    private String role;   // ADMIN, ENSEIGNANT, ETUDIANT...
    private Long siteId;
}
