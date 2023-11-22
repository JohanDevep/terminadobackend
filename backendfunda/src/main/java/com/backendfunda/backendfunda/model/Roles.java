package com.backendfunda.backendfunda.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role")
public class Roles {
    //Llave primaria de la tabla
    @Id
    // @GeneratedValue se usa para generar numeros unicos en idCursos.
    // GenerationType.IDENTITY se usa para que en la base de datos el idCursos
    // asigne automaticamente numeros cada ves mas grande al registrar un nuevo curso
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private  Long idRoles;
    private String nombre;
}
