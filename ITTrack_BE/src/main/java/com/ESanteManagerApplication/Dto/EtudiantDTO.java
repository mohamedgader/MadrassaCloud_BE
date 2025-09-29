package com.ESanteManagerApplication.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class EtudiantDTO {
    private Long id;
    private String nomComplet;
    private LocalDate dateNaissance;
    private Long siteId;
    private Long classeId;
    private List<ProgressionDTO> progressions;
}
