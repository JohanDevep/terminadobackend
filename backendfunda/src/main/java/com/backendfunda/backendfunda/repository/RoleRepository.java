package com.backendfunda.backendfunda.repository;

import com.backendfunda.backendfunda.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
// hereda metodo utiles para interactuar con la base de datos para la entidad Roles.
public interface RoleRepository extends JpaRepository<Roles, Long> {
    //Metodo para buscar un role por su nombre en nuestra base de datos
    Optional<Roles> findByNombre(String nombre);

}
