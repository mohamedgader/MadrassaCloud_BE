package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.EtudiantDTO;
import com.ESanteManagerApplication.Enities.Etudiant;
import com.ESanteManagerApplication.Enities.Site;
import com.ESanteManagerApplication.Repositories.EtudiantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantServiceImpl implements EtudiantService {

    private final EtudiantRepository repo;
    private final ModelMapper mapper;

    public EtudiantServiceImpl(EtudiantRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public EtudiantDTO creer(EtudiantDTO dto, Long siteId) {
        Etudiant etudiant = mapper.map(dto, Etudiant.class);
        etudiant.setSite(new Site());
        etudiant.getSite().setId(siteId);
        Etudiant saved = repo.save(etudiant);
        return mapper.map(saved, EtudiantDTO.class);
    }

    @Override
    public EtudiantDTO modifier(Long id, EtudiantDTO dto, Long siteId) {
        Etudiant etudiant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
        mapper.map(dto, etudiant);
        etudiant.setSite(new Site());
        etudiant.getSite().setId(siteId);
        Etudiant updated = repo.save(etudiant);
        return mapper.map(updated, EtudiantDTO.class);
    }

    @Override
    public void supprimer(Long id, Long siteId) {
        Etudiant etudiant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
        repo.delete(etudiant);
    }

    @Override
    public EtudiantDTO chercherParId(Long id, Long siteId) {
        Etudiant etudiant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
        return mapper.map(etudiant, EtudiantDTO.class);
    }

    @Override
    public List<EtudiantDTO> listerParSite(Long siteId) {
        return repo.findBySite_Id(siteId)
                .stream()
                .map(e -> mapper.map(e, EtudiantDTO.class))
                .toList();
    }

    @Override
    public List<EtudiantDTO> listerParClasse(Long classeId, Long siteId) {
        return repo.findByClasse_IdAndClasse_Site_Id(classeId, siteId)
                .stream()
                .map(e -> mapper.map(e, EtudiantDTO.class))
                .toList();
    }
}
