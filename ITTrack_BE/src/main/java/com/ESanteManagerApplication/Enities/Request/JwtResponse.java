package com.ESanteManagerApplication.Enities.Request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type;
    private String username;
    private String roles;
}
