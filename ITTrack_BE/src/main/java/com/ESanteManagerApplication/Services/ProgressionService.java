package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.ProgressionDTO;

import java.util.List;

public interface ProgressionService {
    ProgressionDTO ajouter(Long etudiantId, Long siteId, ProgressionDTO dto);
    ProgressionDTO modifier(Long id, ProgressionDTO dto, Long siteId);
    void supprimer(Long id, Long siteId);
    ProgressionDTO chercherParId(Long id, Long siteId);
    List<ProgressionDTO> listerParEtudiant(Long etudiantId, Long siteId);
}