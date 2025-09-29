package com.ESanteManagerApplication.Controllers;

import com.ESanteManagerApplication.Dto.SiteDTO;
import com.ESanteManagerApplication.Enities.Request.EntityResponse;
import com.ESanteManagerApplication.Exceptions.NotFoundEntityException;
import com.ESanteManagerApplication.Exceptions.NullIdEntityException;
import com.ESanteManagerApplication.Services.SiteService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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
@RequestMapping("/api/sites")
public class SiteController {

    private final SiteService service;

    public SiteController(SiteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EntityResponse<SiteDTO>> create(@RequestBody SiteDTO dto) {
        try {
            SiteDTO saved = service.creer(dto);
            return ResponseEntity.ok(new EntityResponse<>(200, "Site créé avec succès", Optional.of(saved)));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new EntityResponse<>(500, ex.getMessage(), Optional.empty()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityResponse<SiteDTO>> update(@PathVariable Long id, @RequestBody SiteDTO dto) {
        try {
            SiteDTO updated = service.modifier(id, dto);
            return ResponseEntity.ok(new EntityResponse<>(200, "Site modifié avec succès", Optional.of(updated)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntityResponse<Void>> delete(@PathVariable Long id) {
        try {
            service.supprimer(id);
            return ResponseEntity.ok(new EntityResponse<>(200, "Site supprimé avec succès", Optional.empty()));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityResponse<SiteDTO>> getById(@PathVariable Long id) {
        try {
            SiteDTO dto = service.chercherParId(id);
            return ResponseEntity.ok(new EntityResponse<>(200, "Site trouvé", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping("/nom/{nom}")
    public ResponseEntity<EntityResponse<SiteDTO>> getByNom(@PathVariable String nom) {
        try {
            SiteDTO dto = service.chercherParNom(nom);
            return ResponseEntity.ok(new EntityResponse<>(200, "Site trouvé", Optional.of(dto)));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new EntityResponse<>(404, ex.getMessage(), Optional.empty()));
        }
    }

    @GetMapping
    public ResponseEntity<List<SiteDTO>> listAll() {
        return ResponseEntity.ok(service.lister());
    }
}
