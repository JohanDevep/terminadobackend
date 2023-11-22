package com.backendfunda.backendfunda.repository;

import com.backendfunda.backendfunda.model.Cursos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
// hereda metodo utiles para interactuar con la base de datos para la entidad Cursos.
public interface CursosRepository extends JpaRepository <Cursos, Long> {
    // metodo para buscar un curso por su identidad
    Optional<Cursos> findById(Long idCursos);
    // metodo para verificar si existe un curso con un título específico
    Boolean existsByTitulo(String titulo);
}
