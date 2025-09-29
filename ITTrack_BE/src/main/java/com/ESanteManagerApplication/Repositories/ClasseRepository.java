package com.ESanteManagerApplication.Repositories;


import com.ESanteManagerApplication.Enities.Classe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe, Long> {
    // Toutes les classes d’un site
    List<Classe> findBySite_Id(Long siteId);
}