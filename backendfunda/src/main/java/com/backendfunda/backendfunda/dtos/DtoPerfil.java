package com.backendfunda.backendfunda.dtos;

import lombok.Data;

@Data
public class DtoPerfil {
    // El accessToken permite al usuario hacer ciertas opciones es algo unico
    private String accessToken;
    // El tokenType es usado en la autenticacion para especificar el tipo de token.
    private String tokenType = "Bearer";
    private String nombre;
    private String apellido;
    private String telefono;
    private String correo;
    private String imagen;
    private String rol;
    // Cuando creamos un nuevo perfil, necesitamos proporcionar valores
    // para estas caracteristicas, y eso es lo que hace este constructor.
    public DtoPerfil(String accessToken, String nombre, String apellido, String telefono, String correo, String imagen, String rol) {
        this.accessToken = accessToken;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
        this.imagen = imagen;
        this.rol = rol;
    }
}
