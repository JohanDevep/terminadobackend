package com.backendfunda.backendfunda.repository;

import com.backendfunda.backendfunda.model.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
// hereda metodo utiles para interactuar con la base de datos para la entidad Usuarios.
public interface UsuariosRepository extends JpaRepository<Usuarios, Long> {
    // metodo para buscar un usuario por su nombre
    Optional<Usuarios> findByNombre(String nombre);
    // metodo para verificar si existe un usuario con un nombre
    Boolean existsByNombre(String nombre);
    // metodo para verificar si existe un usuario con un correo
    boolean existsByCorreo(String correo);
    // metodo para buscar un usuario por su correo electrónico
    Optional<Usuarios> findByCorreo(String correo);
    // metodo para buscar un usuario por su token de recuperación
    Optional<Usuarios> findByTokenRecuperacion(String token);
    // metodo para obtener una lista de usuarios con el token de recuperación no nulo
    List<Usuarios> findByTokenRecuperacionIsNotNull(); // Agregar este método

}