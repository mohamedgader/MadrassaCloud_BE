package com.ESanteManagerApplication.Repositories;

import com.ESanteManagerApplication.Enities.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EtudiantRepository extends JpaRepository<Etudiant, Long> {
    // Tous les étudiants d’un site
    List<Etudiant> findBySite_Id(Long siteId);

    // Tous les étudiants d’une classe d’un site
    List<Etudiant> findByClasse_IdAndClasse_Site_Id(Long classeId, Long siteId);
}
