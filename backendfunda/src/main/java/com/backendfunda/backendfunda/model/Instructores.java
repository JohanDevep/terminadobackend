package com.backendfunda.backendfunda.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Blob;
import java.sql.Blob;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "instructores")
public class Instructores {
    //Llave primaria de la tabla
    @Id
    // @GeneratedValue se usa para generar numeros unicos en idCursos.
    // GenerationType.IDENTITY se usa para que en la base de datos el idCursos
    // asigne automaticamente numeros cada ves mas grande al registrar un nuevo curso

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructores")
    private Long idInstructores;
    private String titulos;
    private String images;
    private String nombre;
    private String description;
    private String estado;


}

