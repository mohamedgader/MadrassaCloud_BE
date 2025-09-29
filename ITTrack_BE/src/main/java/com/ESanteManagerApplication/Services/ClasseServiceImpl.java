package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Dto.ClasseDTO;
import com.ESanteManagerApplication.Enities.Classe;
import com.ESanteManagerApplication.Enities.Site;
import com.ESanteManagerApplication.Repositories.ClasseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClasseServiceImpl implements ClasseService {

    private final ClasseRepository repo;
    private final ModelMapper mapper;

    public ClasseServiceImpl(ClasseRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public ClasseDTO creer(ClasseDTO dto, Long siteId) {
        Classe classe = mapper.map(dto, Classe.class);
        classe.setSite(new Site());
        classe.getSite().setId(siteId);
        Classe saved = repo.save(classe);
        return mapper.map(saved, ClasseDTO.class);
    }

    @Override
    public ClasseDTO modifier(Long id, ClasseDTO dto, Long siteId) {
        Classe classe = repo.findById(id)
                .filter(c -> c.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Classe introuvable"));
        mapper.map(dto, classe);
        classe.setSite(new Site());
        classe.getSite().setId(siteId);
        Classe updated = repo.save(classe);
        return mapper.map(updated, ClasseDTO.class);
    }

    @Override
    public void supprimer(Long id, Long siteId) {
        Classe classe = repo.findById(id)
                .filter(c -> c.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Classe introuvable"));
        repo.delete(classe);
    }

    @Override
    public ClasseDTO chercherParId(Long id, Long siteId) {
        Classe classe = repo.findById(id)
                .filter(c -> c.getSite().getId().equals(siteId))
                .orElseThrow(() -> new RuntimeException("Classe introuvable"));
        return mapper.map(classe, ClasseDTO.class);
    }

    @Override
    public List<ClasseDTO> listerParSite(Long siteId) {
        return repo.findBySite_Id(siteId)
                .stream()
                .map(c -> mapper.map(c, ClasseDTO.class))
                .toList();
    }
}

