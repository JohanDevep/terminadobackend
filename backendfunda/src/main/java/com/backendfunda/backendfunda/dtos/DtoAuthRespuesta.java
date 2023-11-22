package com.backendfunda.backendfunda.dtos;

import lombok.Data;

@Data
//devolvera la información con el token y el tipo que tenga
public class DtoAuthRespuesta{
    // El token de acceso que se devolverá en la respuesta
    private String accessToken;
    // El tipo de token, que casi siempre es "Bearer" en la parte de autorizacion
    private String tokenType = "Bearer ";
    // Constructor que toma el token de acceso como parámetro al crear una instancia de la clase
    public DtoAuthRespuesta(String accessToken) {
        this.accessToken = accessToken;
    }
}