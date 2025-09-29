package com.ESanteManagerApplication.Services;

import com.ESanteManagerApplication.Enities.UserDetailsImpl;
import com.ESanteManagerApplication.Enities.Utilisateur;
import com.ESanteManagerApplication.Repositories.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé avec l'email: " + email));

        return new UserDetailsImpl(utilisateur); // ⚠️ on doit aussi créer UserDetailsImpl
    }
}