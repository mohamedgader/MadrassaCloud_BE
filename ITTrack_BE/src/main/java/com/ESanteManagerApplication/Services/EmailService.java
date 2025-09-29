package com.ESanteManagerApplication.Services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Vérification de votre compte");
        message.setText("Votre code de vérification est : " + code);
        mailSender.send(message);
    }
    public void sendResetCodeEmail(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Réinitialisation de mot de passe - Code OTP");
        message.setText("Bonjour,\n\n" +
                "Voici votre code de réinitialisation : " + code + "\n" +
                "⚠️ Ce code est valable uniquement pendant 250 secondes.\n\n" +
                "Si vous n'avez pas demandé la réinitialisation, ignorez cet email.\n\n" +
                "Cordialement,\n" +
                "L'équipe ESanteManager");

        mailSender.send(message);
    }
}