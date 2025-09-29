package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.SiteDTO;

import java.util.List;

public interface SiteService {
    SiteDTO creer(SiteDTO dto);
    SiteDTO modifier(Long id, SiteDTO dto);
    void supprimer(Long id);
    SiteDTO chercherParId(Long id);
    SiteDTO chercherParNom(String nom);
    List<SiteDTO> lister();
}
