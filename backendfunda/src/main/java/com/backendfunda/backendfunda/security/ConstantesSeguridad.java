package com.backendfunda.backendfunda.security;


public class ConstantesSeguridad {
    // Tiempo de expiración del token JWT en milisegundos (5 minutos en este caso)
    public static final long JWT_EXPIRATION_TOKEN = 300000; //equivaler a 5 min, donde 60000 = a 1 min
    // Firma utilizada para verificar la autenticidad del token JWT
    public static final String JWT_FIRMA = "firma";
}
