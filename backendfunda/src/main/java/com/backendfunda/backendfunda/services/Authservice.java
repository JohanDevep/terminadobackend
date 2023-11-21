package com.backendfunda.backendfunda.services;

import com.backendfunda.backendfunda.dtos.DtoLogin;
import com.backendfunda.backendfunda.dtos.DtoPerfil;
import com.backendfunda.backendfunda.dtos.DtoRegistro;
import com.backendfunda.backendfunda.model.Usuarios;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

public interface Authservice {
    ResponseEntity<?> registrar(DtoRegistro dtoRegistro);
    ResponseEntity<?> registrarInst(DtoRegistro dtoRegistro);
    ResponseEntity<?> login(DtoLogin dtoLogin);
    ResponseEntity<String> registrarAdmin(DtoRegistro dtoRegistro);
    List<Usuarios> getAllUsers();
    ResponseEntity<?> getEmployee(Long id);
    ResponseEntity<DtoPerfil> getPerfil(Principal principal);
    ResponseEntity<Object> editarPerfil(DtoPerfil dtoPerfil, Principal principal);


}
