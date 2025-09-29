package com.ESanteManagerApplication.Controllers;




import com.ESanteManagerApplication.Enities.Administrateur;
import com.ESanteManagerApplication.Enities.Request.*;
import com.ESanteManagerApplication.Enities.RoleUtilisateur;
import com.ESanteManagerApplication.Enities.Utilisateur;

import com.ESanteManagerApplication.Exceptions.ResourceNotFoundException;
import com.ESanteManagerApplication.Repositories.UtilisateurRepository;

import com.ESanteManagerApplication.Security.JwtUtils;
import com.ESanteManagerApplication.Services.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final ModelMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<EntityResponse<Utilisateur>> registerUser(@RequestBody RegisterRequest signUpRequest) {
        EntityResponse<Utilisateur> response = new EntityResponse<>();

        try {
            if (utilisateurRepository.existsByEmail(signUpRequest.getEmail())) {
                response.setCode(400);
                response.setMessage("Erreur: l'email existe déjà !");
                response.setEntite(Optional.empty());
                return ResponseEntity.badRequest().body(response);
            }

            // ici tu peux choisir le type concret selon le rôle
            Utilisateur user = new Administrateur();
            user.setNomComplet(signUpRequest.getNomComplet());
            user.setEmail(signUpRequest.getEmail());

            String verificationCode = String.valueOf((int)((Math.random() * 900000) + 100000));
            user.setVerificationCode(verificationCode);
            user.setVerificationCodeExpiry(LocalDateTime.now().plusSeconds(250));

            user.setActive(false);
            user.setMotDePasseHash(passwordEncoder.encode(signUpRequest.getMotDePasse()));
            user.setRole(RoleUtilisateur.valueOf(signUpRequest.getRole()));

            utilisateurRepository.save(user);

            response.setCode(200);
            response.setMessage("Utilisateur enregistré avec succès !");
            response.setEntite(Optional.of(user));

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Erreur interne: " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<EntityResponse<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {
        EntityResponse<JwtResponse> response = new EntityResponse<>();

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getMotDePasse())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateJwtToken(userDetails);

            // Récupération du rôle depuis l’entité Utilisateur
            Utilisateur utilisateur = utilisateurRepository.findByEmail(loginRequest.getEmail())
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur introuvable avec l'email " + loginRequest.getEmail()));
            if (!utilisateur.isActive()) {
                response.setCode(403);
                response.setMessage("Compte désactivé. Veuillez contacter l’administrateur.");
                response.setEntite(Optional.empty());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
            }

            JwtResponse jwtResponse = new JwtResponse(
                    jwt,
                    "Bearer",
                    utilisateur.getEmail(),
                    utilisateur.getRole().name() // renvoie ADMIN, ENSEIGNANT ou ETUDIANT
            );

            response.setCode(200);
            response.setMessage("Authentification réussie");
            response.setEntite(Optional.of(jwtResponse));

            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(401);
            response.setMessage("Échec de l'authentification: " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<EntityResponse<String>> logoutUser(HttpServletRequest request) {
        EntityResponse<String> response = new EntityResponse<>();

        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // Ici tu peux ajouter ton token dans une blacklist si tu veux l'invalider
            }

            SecurityContextHolder.clearContext();

            response.setCode(200);
            response.setMessage("Déconnexion réussie");
            response.setEntite(Optional.of("Utilisateur déconnecté"));
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Erreur lors de la déconnexion : " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/reset-password/{id}")
    public ResponseEntity<EntityResponse<String>> resetPassword(
            @PathVariable Long id,
            @RequestBody String newPassword) {

        EntityResponse<String> response = new EntityResponse<>();
        try {
            Utilisateur user = utilisateurRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

            user.setMotDePasseHash(passwordEncoder.encode(newPassword));
            utilisateurRepository.save(user);

            response.setCode(200);
            response.setMessage("Mot de passe réinitialisé avec succès");
            response.setEntite(Optional.of("OK"));
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Erreur lors de la réinitialisation : " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/activate/{id}")
    public ResponseEntity<EntityResponse<String>> activateUser(@PathVariable Long id) {
        EntityResponse<String> response = new EntityResponse<>();
        try {
            Utilisateur user = utilisateurRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

            user.setActive(true);  // ✅ pas setIsActive()
            utilisateurRepository.save(user);

            response.setCode(200);
            response.setMessage("Compte activé avec succès");
            response.setEntite(Optional.of("ACTIVE"));
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Erreur lors de l'activation : " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<EntityResponse<String>> deactivateUser(@PathVariable Long id) {
        EntityResponse<String> response = new EntityResponse<>();
        try {
            Utilisateur user = utilisateurRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));

            user.setActive(false);  // ✅
            utilisateurRepository.save(user);

            response.setCode(200);
            response.setMessage("Compte désactivé avec succès");
            response.setEntite(Optional.of("INACTIVE"));
            return ResponseEntity.ok(response);

        } catch (Exception ex) {
            response.setCode(500);
            response.setMessage("Erreur lors de la désactivation : " + ex.getMessage());
            response.setEntite(Optional.empty());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyCode(@RequestBody VerifyRequest verifyRequest) {
        Utilisateur user = utilisateurRepository.findByEmail(verifyRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (user.getVerificationCode() == null || user.getVerificationCodeExpiry() == null) {
            return ResponseEntity.badRequest().body("Aucun code de vérification trouvé.");
        }

        if (LocalDateTime.now().isAfter(user.getVerificationCodeExpiry())) {
            return ResponseEntity.badRequest().body("Le code a expiré.");
        }

        System.out.println("1:"+user.getVerificationCode());
        System.out.println("2:"+verifyRequest.getVerificationCode());
        if (!user.getVerificationCode().equals(verifyRequest.getVerificationCode())) {
            return ResponseEntity.badRequest().body("Code invalide.");
        }

        // Activer le compte
        user.setActive(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);
        utilisateurRepository.save(user);

        return ResponseEntity.ok("Compte activé avec succès !");
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        String resetCode = String.valueOf((int)((Math.random() * 900000) + 100000));
        user.setResetCode(resetCode);
        user.setResetCodeExpiry(LocalDateTime.now().plusSeconds(250));
        utilisateurRepository.save(user);

        emailService.sendResetCodeEmail(user.getEmail(), resetCode);

        return ResponseEntity.ok("Code de réinitialisation envoyé par email.");
    }
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String code,
                                           @RequestParam String newPassword) {
        Utilisateur user = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (user.getResetCode() == null || LocalDateTime.now().isAfter(user.getResetCodeExpiry())) {
            return ResponseEntity.badRequest().body("Code expiré ou inexistant.");
        }

        if (!user.getResetCode().equals(code)) {
            return ResponseEntity.badRequest().body("Code invalide.");
        }

        user.setMotDePasseHash(passwordEncoder.encode(newPassword));
        user.setResetCode(null);
        user.setResetCodeExpiry(null);
        utilisateurRepository.save(user);

        return ResponseEntity.ok("Mot de passe réinitialisé avec succès !");
    }



}

