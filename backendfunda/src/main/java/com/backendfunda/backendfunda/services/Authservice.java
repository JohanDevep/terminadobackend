package com.backendfunda.backendfunda.services;

import com.backendfunda.backendfunda.dtos.DtoLogin;
import com.backendfunda.backendfunda.dtos.DtoPerfil;
import com.backendfunda.backendfunda.dtos.DtoRegistro;
import com.backendfunda.backendfunda.model.Usuarios;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface Authservice {
    // metodo para registrar un nuevo usuario
    ResponseEntity<?> registrar(DtoRegistro dtoRegistro);
    // metodo para registrar un nuevo instructor
    ResponseEntity<?> registrarInst(DtoRegistro dtoRegistro);
    // metodo para iniciar sesion
    ResponseEntity<?> login(DtoLogin dtoLogin);
    // metodo para registrar un nuevo administrador
    ResponseEntity<String> registrarAdmin(DtoRegistro dtoRegistro);
    // metodo para obtener todos los usuarios registrados
    List<Usuarios> getAllUsers();
    // metodo para obtener la información de un usuario específico
    ResponseEntity<?> getEmployee(Long id);
    // metodo para obtener el perfil de un usuario autenticado
    ResponseEntity<DtoPerfil> getPerfil(Principal principal);
    // metodo para editar el perfil de un usuario autenticado
    ResponseEntity<Object> editarPerfil(DtoPerfil dtoPerfil, Principal principal);
}
