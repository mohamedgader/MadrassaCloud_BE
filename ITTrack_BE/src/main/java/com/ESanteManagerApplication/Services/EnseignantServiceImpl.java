package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.EnseignantDTO;
import com.ESanteManagerApplication.Enities.Classe;
import com.ESanteManagerApplication.Enities.Enseignant;
import com.ESanteManagerApplication.Enities.Site;
import com.ESanteManagerApplication.Repositories.ClasseRepository;
import com.ESanteManagerApplication.Repositories.EnseignantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnseignantServiceImpl implements EnseignantService {

    private final EnseignantRepository repo;
    private final ClasseRepository classeRepository;
    private final ModelMapper mapper;


    @Override
    public EnseignantDTO creer(EnseignantDTO dto, Long siteId) {
        Enseignant enseignant = mapper.map(dto, Enseignant.class);
        enseignant.setSite(new Site());
        enseignant.getSite().setId(siteId);
        Enseignant saved = repo.save(enseignant);
        return mapper.map(saved, EnseignantDTO.class);
    }

    @Override
    public EnseignantDTO modifier(Long id, EnseignantDTO dto, Long siteId) {
        Enseignant enseignant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        mapper.map(dto, enseignant);
        enseignant.setSite(new Site());
        enseignant.getSite().setId(siteId);
        Enseignant updated = repo.save(enseignant);
        return mapper.map(updated, EnseignantDTO.class);
    }

    @Override
    public void supprimer(Long id, Long siteId) {
        Enseignant enseignant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        repo.delete(enseignant);
    }

    @Override
    public EnseignantDTO chercherParId(Long id, Long siteId) {
//        Enseignant enseignant = repo.findById(id)
//                .filter(e -> e.getSite().getId().equals(siteId))
//                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
//        return mapper.map(enseignant, EnseignantDTO.class);
        Enseignant enseignant = repo.findById(id)
                .filter(e -> e.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        EnseignantDTO dto = mapper.map(enseignant, EnseignantDTO.class);

        // Forcer le mapping de la liste des classes vers les IDs
        dto.setClassesIds(
                enseignant.getClasses()
                        .stream()
                        .map(Classe::getId)
                        .collect(Collectors.toList())
        );

        return dto;
    }

    @Override
    public EnseignantDTO chercherParEmail(String email, Long siteId) {
        Enseignant enseignant = repo.findByEmailAndSite_Id(email, siteId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));
        return mapper.map(enseignant, EnseignantDTO.class);
    }

    @Override
    public List<EnseignantDTO> listerParSite(Long siteId) {
        return repo.findBySite_Id(siteId)
                .stream()
                .map(e -> mapper.map(e, EnseignantDTO.class))
                .toList();
    }
    @Transactional
    public EnseignantDTO affecterClasses(Long enseignantId, List<Long> classesIds) {
        Enseignant enseignant = repo.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant introuvable"));

        List<Classe> classes = classeRepository.findAllById(classesIds);

        for (Classe classe : classes) {
            if (!classe.getEnseignants().contains(enseignant)) {
                classe.getEnseignants().add(enseignant);
            }
        }

        // Sauvegarde côté propriétaire
        classeRepository.saveAll(classes);

        // Mise à jour du DTO
        EnseignantDTO dto = new EnseignantDTO();
        dto.setId(enseignant.getId());
        dto.setEmail(enseignant.getEmail());
        dto.setSiteId(enseignant.getSite().getId());
        dto.setClassesIds(
                classes.stream().map(Classe::getId).toList()
        );

        return dto;
    }



}
