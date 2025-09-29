package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.EnseignantDTO;

import java.util.List;
import java.util.Optional;

public interface EnseignantService {
    EnseignantDTO creer(EnseignantDTO dto, Long siteId);
    EnseignantDTO modifier(Long id, EnseignantDTO dto, Long siteId);
    void supprimer(Long id, Long siteId);
    EnseignantDTO chercherParId(Long id, Long siteId);
    EnseignantDTO chercherParEmail(String email, Long siteId);
    List<EnseignantDTO> listerParSite(Long siteId);
    EnseignantDTO affecterClasses(Long enseignantId, List<Long> classesIds);

}
