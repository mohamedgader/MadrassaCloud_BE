package com.ESanteManagerApplication.Repositories;


import com.ESanteManagerApplication.Enities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    // Tous les enseignants d’un site
    List<Enseignant> findBySite_Id(Long siteId);
    Optional<Enseignant> findByEmailAndSite_Id(String email, Long siteId);
}
