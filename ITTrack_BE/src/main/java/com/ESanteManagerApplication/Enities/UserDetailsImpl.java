package com.ESanteManagerApplication.Enities;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private final Utilisateur utilisateur;

    public UserDetailsImpl(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(utilisateur.getRole().name()));
    }

    @Override
    public String getPassword() {
        return utilisateur.getMotDePasseHash();
    }

    @Override
    public String getUsername() {
        return utilisateur.getEmail(); // ou nomComplet selon ton besoin
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // à gérer si tu veux expiration
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // idem
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.isActive(); // 🔹 ici on prend ton champ isActive
    }
}
