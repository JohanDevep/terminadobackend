package com.backendfunda.backendfunda.controller;

import com.backendfunda.backendfunda.model.Usuarios;
import com.backendfunda.backendfunda.repository.UsuariosRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TokenExpirationChecker {

    private final UsuariosRepository usuariosRepository;
    // Constructor que inyecta la dependencia del repositorio de usuarios

    public TokenExpirationChecker(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }
    // Método programado para ejecutarse periódicamente cada 2 minutos

    @Scheduled(fixedRate = 120000) // 120000 ms = 2 minutos
    public void checkTokenExpiration() {
        LocalDateTime now = LocalDateTime.now();
        // Obtiene la lista de usuarios que tienen un token de recuperación

        List<Usuarios> usuariosConTokens = usuariosRepository.findByTokenRecuperacionIsNotNull();
        // Itera sobre cada usuario con token

        for (Usuarios usuario : usuariosConTokens) {
            LocalDateTime tokenExpiracion = usuario.getTokenExpiracion();
            // Verifica si el token ha expirado

            if (tokenExpiracion != null && now.isAfter(tokenExpiracion)) {
                // El token ha expirado, elimínalo
                usuario.setTokenRecuperacion(null);
                usuario.setTokenExpiracion(null);
                usuariosRepository.save(usuario);
            }
        }
    }
}
