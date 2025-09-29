package com.ESanteManagerApplication.Controllers;

import com.ESanteManagerApplication.Dto.EtudiantDTO;
import com.ESanteManagerApplication.Services.EtudiantService;
import org.springframework.web.bind.annotation.*;
import com.ESanteManagerApplication.Dto.ClasseDTO;
import com.ESanteManagerApplication.Enities.Request.EntityResponse;
import com.ESanteManagerApplication.Exceptions.ResourceNotFoundException;
import com.ESanteManagerApplication.Services.ClasseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final EtudiantService service;

    public EtudiantController(EtudiantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityResponse<EtudiantDTO>> create(@RequestBody EtudiantDTO dto, @RequestParam Long siteId) {
        try {
            EtudiantDTO saved = service.creer(dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Étudiant créé avec succès", Optional.of(saved)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<EtudiantDTO>> update(@PathVariable Long id, @RequestBody EtudiantDTO dto, @RequestParam Long siteId) {
        try {
            EtudiantDTO updated = service.modifier(id, dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Étudiant modifié avec succès", Optional.of(updated)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<Void>> delete(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            service.supprimer(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Étudiant supprimé avec succès", Optional.empty()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<EtudiantDTO>> getById(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            EtudiantDTO dto = service.chercherParId(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Étudiant trouvé", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<EtudiantDTO>> listBySite(@PathVariable Long siteId) {
        return ResponseEntity.ok(service.listerParSite(siteId));
    }

    @GetMapping("/classe/{classeId}/site/{siteId}")
    public ResponseEntity<List<EtudiantDTO>> listByClasse(@PathVariable Long classeId, @PathVariable Long siteId) {
        return ResponseEntity.ok(service.listerParClasse(classeId, siteId));
    }
}

