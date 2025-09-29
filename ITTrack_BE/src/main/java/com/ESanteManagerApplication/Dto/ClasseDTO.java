package com.ESanteManagerApplication.Dto;

import lombok.Data;

import java.util.List;
@Data
public class ClasseDTO {
    private Long id;
    private String nom;
    private Long siteId;
    private List<Long> enseignantsIds;
    private List<Long> etudiantsIds;
}
