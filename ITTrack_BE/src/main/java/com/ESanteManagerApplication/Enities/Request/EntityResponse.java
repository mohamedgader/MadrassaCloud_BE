package com.ESanteManagerApplication.Enities.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EntityResponse<S> {
    private int code;
    private String message;
    private Optional<Object> entite;
}
