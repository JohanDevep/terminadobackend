package com.backendfunda.backendfunda.repository;

import com.backendfunda.backendfunda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// hereda metodo utiles para interactuar con la base de datos para la entidad User.
public interface UserRepository extends JpaRepository <User,Long> {
}
