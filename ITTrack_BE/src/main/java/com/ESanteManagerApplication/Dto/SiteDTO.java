package com.ESanteManagerApplication.Dto;

import lombok.Data;

import java.util.List;

@Data
public class SiteDTO {
    private Long id;
    private String nom;
    private String adresse;
    private List<Long> classesIds;
    private List<Long> utilisateursIds;
}
