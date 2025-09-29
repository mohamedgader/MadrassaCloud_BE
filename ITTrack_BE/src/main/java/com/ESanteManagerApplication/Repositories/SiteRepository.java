package com.ESanteManagerApplication.Repositories;

import com.ESanteManagerApplication.Enities.Site;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findByNom(String nom);
}