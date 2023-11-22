package com.backendfunda.backendfunda.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
//Clase para poder manejar las excepciones de tipo autenticación en nuestra app
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    // Método para manejar el inicio de la autenticación
    @Override

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // Envía una respuesta de error no autorizado (401) con el mensaje de la excepción
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
