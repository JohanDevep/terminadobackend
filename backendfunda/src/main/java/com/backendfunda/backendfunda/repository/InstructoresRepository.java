package com.backendfunda.backendfunda.repository;

import com.backendfunda.backendfunda.model.Instructores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
// hereda metodo utiles para interactuar con la base de datos para la entidad Instructores.
public interface InstructoresRepository extends JpaRepository <Instructores, Long> {
    // metodo para buscar un instructor por su identificador
    Optional<Instructores> findById(Long idInstructores);
    // metodo para verificar si existe un instructor con un nombre
    boolean existsByNombre(String nombre);
    // Otros métodos de repositorio, si los tienes
    Boolean existsBynombre(String nombre);
}
