package com.ESanteManagerApplication.Controllers;

import com.ESanteManagerApplication.Dto.ClasseDTO;
import com.ESanteManagerApplication.Dto.EnseignantDTO;
import com.ESanteManagerApplication.Enities.Request.EntityResponse;
import com.ESanteManagerApplication.Exceptions.ResourceNotFoundException;
import com.ESanteManagerApplication.Services.ClasseService;
import com.ESanteManagerApplication.Services.EnseignantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/enseignants")
public class EnseignantController {

    private final EnseignantService service;

    public EnseignantController(EnseignantService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityResponse<EnseignantDTO>> create(@RequestBody EnseignantDTO dto, @RequestParam Long siteId) {
        try {
            EnseignantDTO saved = service.creer(dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Enseignant créé avec succès", Optional.of(saved)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<EnseignantDTO>> getById(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            EnseignantDTO dto = service.chercherParId(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Enseignant trouvé", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/email")
    public ResponseEntity<EntityResponse<EnseignantDTO>> getByEmail(@RequestParam String email, @RequestParam Long siteId) {
        try {
            EnseignantDTO dto = service.chercherParEmail(email, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Enseignant trouvé", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }
    @PutMapping("/{enseignantId}/affecter-classes")
    public ResponseEntity<EntityResponse<EnseignantDTO>> affecterClasses(
            @PathVariable Long enseignantId,
            @RequestBody List<Long> classesIds) {
        try {
            EnseignantDTO updated = service.affecterClasses(enseignantId, classesIds);
            return ResponseEntity.ok(new EntityResponse<>(200, "Affectation réussie", Optional.of(updated)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }
}

