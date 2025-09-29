package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.ProgressionDTO;
import com.ESanteManagerApplication.Enities.Etudiant;
import com.ESanteManagerApplication.Enities.Progression;
import com.ESanteManagerApplication.Enities.Site;
import com.ESanteManagerApplication.Repositories.EtudiantRepository;
import com.ESanteManagerApplication.Repositories.ProgressionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressionServiceImpl implements ProgressionService {

    private final ProgressionRepository repo;
    private final EtudiantRepository etudiantRepo;
    private final ModelMapper mapper;

    public ProgressionServiceImpl(ProgressionRepository repo, EtudiantRepository etudiantRepo, ModelMapper mapper) {
        this.repo = repo;
        this.etudiantRepo = etudiantRepo;
        this.mapper = mapper;
    }

    @Override
    public ProgressionDTO ajouter(Long etudiantId, Long siteId, ProgressionDTO dto) {
        Etudiant etudiant = etudiantRepo.findById(etudiantId)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Étudiant introuvable"));
        Progression progression = mapper.map(dto, Progression.class);
        progression.setEtudiant(etudiant);
        Progression saved = repo.save(progression);
        return mapper.map(saved, ProgressionDTO.class);
    }

    @Override
    public ProgressionDTO modifier(Long id, ProgressionDTO dto, Long siteId) {
        Progression progression = repo.findById(id)
                .filter(p -> p.getEtudiant().getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Progression introuvable"));
        mapper.map(dto, progression);
        Progression updated = repo.save(progression);
        return mapper.map(updated, ProgressionDTO.class);
    }

    @Override
    public void supprimer(Long id, Long siteId) {
        Progression progression = repo.findById(id)
                .filter(p -> p.getEtudiant().getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Progression introuvable"));
        repo.delete(progression);
    }

    @Override
    public ProgressionDTO chercherParId(Long id, Long siteId) {
        Progression progression = repo.findById(id)
                .filter(p -> p.getEtudiant().getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Progression introuvable"));
        return mapper.map(progression, ProgressionDTO.class);
    }

    @Override
    public List<ProgressionDTO> listerParEtudiant(Long etudiantId, Long siteId) {
        return repo.findByEtudiant_IdAndEtudiant_Site_Id(etudiantId, siteId)
                .stream()
                .map(p -> mapper.map(p, ProgressionDTO.class))
                .toList();
    }
}


