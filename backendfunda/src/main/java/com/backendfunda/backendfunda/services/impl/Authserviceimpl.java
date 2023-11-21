package com.backendfunda.backendfunda.services.impl;

import com.backendfunda.backendfunda.dtos.DtoAuthRespuesta;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.*;

@Service
public class Authserviceimpl implements Authservice {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository rolesRepository;
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private JwtGenerador jwtGenerador;

    @Override
    public ResponseEntity<?> registrarInst(@RequestBody DtoRegistro dtoRegistro) {
        Map<String, String> message = new HashMap<>();
        // Validar confirmación de contraseña
        if (!dtoRegistro.getPassword().equals(dtoRegistro.getConfirmarPassword())) {
            message.put("response", "Las contraseñas no coinciden.");
        }
        // Validar si el correo ya existe en la base de datos
        if (usuariosRepository.existsByCorreo(dtoRegistro.getCorreo())) {
            message.put("response", "Ya existe un usuario con el correo proporcionado. Intenta con otro correo.");
        }
        // Validar si el teléfono tiene 10 dígitos
        if (dtoRegistro.getTelefono().length() != 10) {
            message.put("response", "El número de teléfono debe tener 10 dígitos.");
        }
        // Validar si el nombre de usuario ya existe en la base de datos
        if (usuariosRepository.existsByNombre(dtoRegistro.getNombre())) {
            message.put("response", "Ya existe un usuario con el nombre proporcionado. Intenta con otro nombre de usuario.");
        }
        // Validar que la contraseña tenga al menos un número y una mayúscula
        String password = dtoRegistro.getPassword();
        if (!password.matches(".*[0-9].*") || !password.matches(".*[A-Z].*")) {
            message.put("response", "La contraseña debe contener al menos un número y una mayúscula.");
        }

        if(!message.isEmpty()) return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        // Crear el objeto Usuarios y realizar el registro
        Usuarios usuarios = new Usuarios();
        usuarios.setNombre(dtoRegistro.getNombre());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setApellido(dtoRegistro.getApellido());
        usuarios.setCorreo(dtoRegistro.getCorreo());
        usuarios.setTelefono(dtoRegistro.getTelefono());

        // Asignar roles
        Roles roles = rolesRepository.findByNombre("USUARIO").orElseThrow(() -> new RuntimeException("Rol 'USUARIO' no encontrado"));
        usuarios.setRoles(Collections.singletonList(roles));

        // Guardar en la base de datos
        usuariosRepository.save(usuarios);
        message.put("response","Registro de instructor exitoso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> registrar(@RequestBody DtoRegistro dtoRegistro) {
        Map<String, String> message = new HashMap<>();
        // Validar confirmación de contraseña
        if (!dtoRegistro.getPassword().equals(dtoRegistro.getConfirmarPassword())) {
            message.put("response", "Las contraseñas no coinciden.");
        }
        // Validar si el correo ya existe en la base de datos
        if (usuariosRepository.existsByCorreo(dtoRegistro.getCorreo())) {
            message.put("response", "Ya existe un usuario con el correo proporcionado. Intenta con otro correo.");
        }
        // Validar si el teléfono tiene 10 dígitos
        if (dtoRegistro.getTelefono().length() != 10) {
            message.put("response", "El número de teléfono debe tener 10 dígitos.");
        }
        // Validar si el nombre de usuario ya existe en la base de datos
        if (usuariosRepository.existsByNombre(dtoRegistro.getNombre())) {
            message.put("response", "Ya existe un usuario con el nombre proporcionado. Intenta con otro nombre de usuario.");
        }
        // Validar que la contraseña tenga al menos un número y una mayúscula
        String password = dtoRegistro.getPassword();
        if (!password.matches(".*[0-9].*") || !password.matches(".*[A-Z].*")) {
            message.put("response", "La contraseña debe contener al menos un número y una mayúscula.");
        }

        if(!message.isEmpty()) return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);

        // Crear el objeto Usuarios y realizar el registro
        Usuarios usuarios = new Usuarios();
        usuarios.setNombre(dtoRegistro.getNombre());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setApellido(dtoRegistro.getApellido());
        usuarios.setCorreo(dtoRegistro.getCorreo());
        usuarios.setTelefono(dtoRegistro.getTelefono());

        // Asignar roles
        Roles roles = rolesRepository.findByNombre("USUARIO").orElseThrow(() -> new RuntimeException("Rol 'USUARIO' no encontrado"));
        usuarios.setRoles(Collections.singletonList(roles));

        // Guardar en la base de datos
        usuariosRepository.save(usuarios);
        message.put("response","Registro de usuario exitoso");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> login(@RequestBody DtoLogin dtoLogin) {
        try {
            // Intentar autenticar al usuario
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    dtoLogin.getCorreo(), dtoLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generar el token JWT
            String token = jwtGenerador.generarToken(authentication);

            // Obtener la información del perfil del usuario
            Optional<Usuarios> optionalUserInfo = usuariosRepository.findByCorreo(dtoLogin.getCorreo());
            if (optionalUserInfo.isPresent()) {
                Usuarios userInfo = optionalUserInfo.get();
                // Crear un DTO que incluya tanto el token como la información del perfil
                DtoPerfil response = new DtoPerfil(
                        token,
                        userInfo.getNombre(),
                        userInfo.getApellido(),
                        userInfo.getTelefono(),
                        userInfo.getCorreo(),
                        userInfo.getImagen(),
                        userInfo.getRoles().get(0).getNombre());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                // En caso de que no se pueda obtener la información del perfil, solo retornar el token
                return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
            }
        } catch (AuthenticationException e) {
            // Manejar la excepción de autenticación aquí (correo y/o contraseña incorrectos)
            return new ResponseEntity<>("Correo y/o contraseña incorrectos", HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<String> registrarAdmin(@RequestBody DtoRegistro dtoRegistro) {
        if (usuariosRepository.existsByNombre(dtoRegistro.getNombre())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        Usuarios usuarios = new Usuarios();
        usuarios.setNombre(dtoRegistro.getNombre());
        usuarios.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
        usuarios.setApellido(dtoRegistro.getApellido());
        usuarios.setCorreo(dtoRegistro.getCorreo());
        usuarios.setTelefono(dtoRegistro.getTelefono());
        Roles roles = rolesRepository.findByNombre("ADMIN").get();
        usuarios.setRoles(Collections.singletonList(roles));
        usuariosRepository.save(usuarios);
        return new ResponseEntity<>("Registro de admin exitoso", HttpStatus.OK);
    }
    @Override
    public List<Usuarios> getAllUsers(){
        return usuariosRepository.findAll();
    }
    @Override
    public ResponseEntity<?> getEmployee(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuariosRepository.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\":\"Server error.\"}");
        }
    }
    @Override
    public ResponseEntity<DtoPerfil> getPerfil(Principal principal) {
        // Obtiene el nombre de usuario (correo electrónico) del usuario autenticado
        String correoElectronico = principal.getName();

        // Busca el perfil del usuario por su correo electrónico
        Optional<Usuarios> optionalUserProfile = usuariosRepository.findByCorreo(correoElectronico);

        if (optionalUserProfile.isPresent()) {
            Usuarios userProfile = optionalUserProfile.get();
            // Mapea la entidad Usuarios a un DTO de perfil
            DtoPerfil perfilDto = new DtoPerfil(
                    userProfile.getNombre(),
                    userProfile.getNombre(),
                    userProfile.getApellido(),
                    userProfile.getTelefono(),
                    userProfile.getCorreo(),
                    userProfile.getImagen(),
                    userProfile.getRoles().get(0).getNombre()
            );
            return ResponseEntity.ok(perfilDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public ResponseEntity<Object> editarPerfil(@RequestBody DtoPerfil dtoPerfil, Principal principal){
        try {
            String nombreDeUsuario = principal.getName();
            Optional<Usuarios> optionalUserProfile = usuariosRepository.findByCorreo(nombreDeUsuario);
            if (optionalUserProfile.isPresent()){
                Usuarios userProfile = optionalUserProfile.get();
                String telefono = dtoPerfil.getTelefono();
                if (telefono != null && telefono.length() == 10){
                    userProfile.setTelefono(telefono);
                } else {
                    return ResponseEntity.badRequest().body("Verifica el numero telefonico");
                }
                String nuevoCorreo = dtoPerfil.getCorreo();
                if (!nuevoCorreo.equals(userProfile.getCorreo()) && usuariosRepository.existsByCorreo(nuevoCorreo)){
                    return ResponseEntity.status(HttpStatus.CONFLICT).body("El correo ya existe en la base de datos");
                }
                userProfile.setNombre(dtoPerfil.getNombre());
                userProfile.setApellido(dtoPerfil.getApellido());
                userProfile.setCorreo(nuevoCorreo);
                userProfile.setImagen(dtoPerfil.getImagen());

                // Guarda los cambios en el perfil
                Usuarios perfilActualizado = usuariosRepository.save(userProfile);

                // Mapea la entidad Usuarios actualizada a un DTO de perfil
                DtoPerfil perfilActualizadoDto = new DtoPerfil(
                        perfilActualizado.getNombre(),
                        perfilActualizado.getNombre(),
                        perfilActualizado.getApellido(),
                        perfilActualizado.getTelefono(),
                        perfilActualizado.getCorreo(),
                        perfilActualizado.getImagen(),
                        perfilActualizado.getRoles().get(0).getNombre()
                );
                return ResponseEntity.ok(perfilActualizadoDto);


            } else {
                return ResponseEntity.notFound().build();

            }
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al modificar el perfil");
        }
    }
}
