package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.ClasseDTO;

import java.util.List;

public interface ClasseService {
    ClasseDTO creer(ClasseDTO dto, Long siteId);
    ClasseDTO modifier(Long id, ClasseDTO dto, Long siteId);
    void supprimer(Long id, Long siteId);
    ClasseDTO chercherParId(Long id, Long siteId);
    List<ClasseDTO> listerParSite(Long siteId);
}
