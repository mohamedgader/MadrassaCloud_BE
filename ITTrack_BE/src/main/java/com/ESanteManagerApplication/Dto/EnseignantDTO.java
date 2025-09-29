package com.ESanteManagerApplication.Dto;

import lombok.Data;

import java.util.List;
@Data
public class EnseignantDTO {
    private Long id;
    private String nomComplet;
    private String email;
    private Long siteId;
    private List<Long> classesIds;
}
