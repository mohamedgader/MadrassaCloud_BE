package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.SiteDTO;
import com.ESanteManagerApplication.Enities.Classe;
import com.ESanteManagerApplication.Enities.Site;
import com.ESanteManagerApplication.Enities.Utilisateur;
import com.ESanteManagerApplication.Repositories.SiteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SiteServiceImpl implements SiteService {

    private final SiteRepository repo;
    private final ModelMapper mapper;

    public SiteServiceImpl(SiteRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public SiteDTO creer(SiteDTO dto) {
        Site site = mapper.map(dto, Site.class);
        Site saved = repo.save(site);
        return mapper.map(saved, SiteDTO.class);
    }

    @Override
    public SiteDTO modifier(Long id, SiteDTO dto) {
        Site site = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Site introuvable"));
        mapper.map(dto, site);
        Site updated = repo.save(site);
        return mapper.map(updated, SiteDTO.class);
    }

    @Override
    public void supprimer(Long id) {
        Site site = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Site introuvable"));
        repo.delete(site);
    }

    @Override
    public SiteDTO chercherParId(Long id) {
        Site site = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Site introuvable"));
        SiteDTO dto = mapper.map(site, SiteDTO.class);

        dto.setClassesIds(
                site.getClasses().stream()
                        .map(Classe::getId)
                        .toList()
        );

        dto.setUtilisateursIds(
                site.getUtilisateurs().stream()
                        .map(Utilisateur::getId)
                        .toList()
        );
        return dto;
    }

    @Override
    public SiteDTO chercherParNom(String nom) {
        Site site = repo.findByNom(nom)
                .orElseThrow(() -> new RuntimeException("Site introuvable"));
        SiteDTO dto = mapper.map(site, SiteDTO.class);
        dto.setClassesIds(
                site.getClasses().stream()
                        .map(Classe::getId)
                        .toList()
        );

        dto.setUtilisateursIds(
                site.getUtilisateurs().stream()
                        .map(Utilisateur::getId)
                        .toList()
        );
        return dto;
    }

    @Override
    public List<SiteDTO> lister() {
        return repo.findAll().stream()
                .map(site -> {
                    SiteDTO dto = mapper.map(site, SiteDTO.class);
                    dto.setClassesIds(
                            site.getClasses().stream()
                                    .map(Classe::getId)
                                    .toList()
                    );
                    dto.setUtilisateursIds(
                            site.getUtilisateurs().stream()
                                    .map(Utilisateur::getId)
                                    .toList()
                    );
                    return dto;
                })
                .toList();
    }
}
