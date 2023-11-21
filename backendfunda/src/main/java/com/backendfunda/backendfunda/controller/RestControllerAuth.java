package com.backendfunda.backendfunda.controller;
import com.backendfunda.backendfunda.dtos.DtoLogin;
import com.backendfunda.backendfunda.dtos.DtoPerfil;
import com.backendfunda.backendfunda.dtos.DtoRegistro;
import com.backendfunda.backendfunda.model.Roles;
import com.backendfunda.backendfunda.model.Usuarios;
import com.backendfunda.backendfunda.repository.RoleRepository;
import com.backendfunda.backendfunda.repository.UsuariosRepository;
import com.backendfunda.backendfunda.security.JwtGenerador;
import com.backendfunda.backendfunda.services.Authservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/")
public class RestControllerAuth {

    @Autowired
    private Authservice authservice;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private RoleRepository rolesRepository;
    private UsuariosRepository usuariosRepository;
    private JwtGenerador jwtGenerador;
    @Autowired

    public RestControllerAuth(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, RoleRepository rolesRepository, UsuariosRepository usuariosRepository, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usuariosRepository = usuariosRepository;
        this.jwtGenerador = jwtGenerador;
    }
    //Método para poder registrar usuarios con role "user"
    @PostMapping("register")
    public ResponseEntity<?> registrar(@RequestBody DtoRegistro dtoRegistro){
        return this.authservice.registrar(dtoRegistro);
    }
    //Método para poder guardar usuarios de tipo ADMIN
    @PostMapping("registerAdm")
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro){
        return this.authservice.registrarAdmin(dtoRegistro);
    }
    //Método para poder logear un usuario y obtener un token
    @PostMapping("ingresar")
    public ResponseEntity<?> login(@RequestBody DtoLogin dtoLogin){
        return this.authservice.login(dtoLogin);
    }
    //listar todos los usuarios
    @GetMapping("/usuariosver")
    public List<Usuarios> getAllUsers() {
        return authservice.getAllUsers();
    }
    @GetMapping("/usuariosver/{id}")
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        return authservice.getEmployee(id);
    }
    @GetMapping("/perfil")
    public ResponseEntity<DtoPerfil> getPerfil(Principal principal) {
        return authservice.getPerfil(principal);
    }
    @PutMapping("/perfil/editar")
    public ResponseEntity<Object> editarPerfil(@RequestBody DtoPerfil dtoPerfil, Principal principal){
        return authservice.editarPerfil(dtoPerfil, principal);
    }
    //Método para poder guardar usuarios de tipo instructor
    @PostMapping("registerIns")
    public ResponseEntity<?> registrarInst(@RequestBody DtoRegistro dtoRegistro){
        return this.authservice.registrarInst(dtoRegistro);
    }


}

