package com.backendfunda.backendfunda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
// Configuración para permitir el intercambio de recursos entre dominios (CORS)

@Configuration
public class WebConfig {
    // Bean para configurar el manejo de CORS en la aplicación.

    @Bean
    public WebMvcConfigurer corsConfig() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Mapeo para todas las rutas de la aplicación
                        .allowedOrigins("*") // Orígenes permitidos (cualquier origen)
                        .allowedMethods(HttpMethod.GET.name(), // Métodos HTTP permitidos
                                HttpMethod.POST.name(),
                                HttpMethod.DELETE.name())
                        .allowedHeaders(HttpHeaders.CONTENT_TYPE, // Cabeceras permitidas tipo y autoricacion
                                HttpHeaders.AUTHORIZATION);
            }
        };
    }
}
