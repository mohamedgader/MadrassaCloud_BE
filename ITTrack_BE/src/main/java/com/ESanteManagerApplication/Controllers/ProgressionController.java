package com.ESanteManagerApplication.Controllers;

import com.ESanteManagerApplication.Dto.ProgressionDTO;
import com.ESanteManagerApplication.Services.ProgressionService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import com.ESanteManagerApplication.Dto.ClasseDTO;
import com.ESanteManagerApplication.Enities.Request.EntityResponse;
import com.ESanteManagerApplication.Exceptions.ResourceNotFoundException;
import com.ESanteManagerApplication.Services.ClasseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.ResponseEntity;

import java.util.List;


@RestController
@RequestMapping("/api/progressions")
public class ProgressionController {

    private final ProgressionService service;

    public ProgressionController(ProgressionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityResponse<ProgressionDTO>> create(@RequestParam Long etudiantId, @RequestParam Long siteId, @RequestBody ProgressionDTO dto) {
        try {
            ProgressionDTO saved = service.ajouter(etudiantId, siteId, dto);
            return ResponseEntity.ok(new EntityResponse<>(200, "Progression ajoutée avec succès", Optional.of(saved)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<ProgressionDTO>> update(@PathVariable Long id, @RequestBody ProgressionDTO dto, @RequestParam Long siteId) {
        try {
            ProgressionDTO updated = service.modifier(id, dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Progression modifiée avec succès", Optional.of(updated)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<Void>> delete(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            service.supprimer(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Progression supprimée avec succès", Optional.empty()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<ProgressionDTO>> getById(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            ProgressionDTO dto = service.chercherParId(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Progression trouvée", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/etudiant/{etudiantId}/site/{siteId}")
    public ResponseEntity<List<ProgressionDTO>> listByEtudiant(@PathVariable Long etudiantId, @PathVariable Long siteId) {
        return ResponseEntity.ok(service.listerParEtudiant(etudiantId, siteId));
    }
}

