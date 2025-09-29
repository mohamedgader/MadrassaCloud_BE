package com.ESanteManagerApplication.Repositories;

import com.ESanteManagerApplication.Enities.Progression;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgressionRepository extends JpaRepository<Progression, Long> {
    // Progressions d’un étudiant
    List<Progression> findByEtudiant_Id(Long etudiantId);

    // Progressions d’un étudiant dans un site
    List<Progression> findByEtudiant_IdAndEtudiant_Site_Id(Long etudiantId, Long siteId);
}