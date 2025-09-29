package com.ESanteManagerApplication.Controllers;

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
@RequestMapping("/api/classes")
public class ClasseController {

    private final ClasseService service;

    public ClasseController(ClasseService service) {
        this.service = service;
    }

    @PostMapping("/{siteId}")
    public ResponseEntity<EntityResponse<ClasseDTO>> create(@RequestBody ClasseDTO dto, @PathVariable Long siteId) {
        try {
            ClasseDTO saved = service.creer(dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Classe créée avec succès", Optional.of(saved)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<ClasseDTO>> update(@PathVariable Long id, @RequestBody ClasseDTO dto, @RequestParam Long siteId) {
        try {
            ClasseDTO updated = service.modifier(id, dto, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Classe modifiée avec succès", Optional.of(updated)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<Void>> delete(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            service.supprimer(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Classe supprimée avec succès", Optional.empty()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<ClasseDTO>> getById(@PathVariable Long id, @RequestParam Long siteId) {
        try {
            ClasseDTO dto = service.chercherParId(id, siteId);
            return ResponseEntity.ok(new EntityResponse<>(200, "Classe trouvée", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<ClasseDTO>> listBySite(@PathVariable Long siteId) {
        return ResponseEntity.ok(service.listerParSite(siteId));
    }
}

