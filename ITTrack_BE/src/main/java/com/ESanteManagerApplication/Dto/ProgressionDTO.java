package com.ESanteManagerApplication.Dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ProgressionDTO {
    private Long id;
    private String sourate;
    private int versetDebut;
    private int versetFin;
    private LocalDate dateApprentissage;
}
