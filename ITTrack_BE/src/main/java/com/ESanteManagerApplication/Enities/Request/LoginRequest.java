package com.ESanteManagerApplication.Enities.Request;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String motDePasse;

}
