package com.ESanteManagerApplication.Enities.Request;

import lombok.Data;

@Data
public class VerifyRequest {
    private String email;
    private String verificationCode;
}
