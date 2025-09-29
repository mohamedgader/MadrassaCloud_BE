package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.EtudiantDTO;

import java.util.List;

public interface EtudiantService {
    EtudiantDTO creer(EtudiantDTO dto, Long siteId);
    EtudiantDTO modifier(Long id, EtudiantDTO dto, Long siteId);
    void supprimer(Long id, Long siteId);
    EtudiantDTO chercherParId(Long id, Long siteId);
    List<EtudiantDTO> listerParSite(Long siteId);
    List<EtudiantDTO> listerParClasse(Long classeId, Long siteId);
}