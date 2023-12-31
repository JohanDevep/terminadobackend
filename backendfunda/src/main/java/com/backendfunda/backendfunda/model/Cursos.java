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
@Table(name = "cursos")
public class Cursos {
    //Llave primaria de la tabla
    @Id
    // @GeneratedValue se usa para generar numeros unicos en idCursos.
    // GenerationType.IDENTITY se usa para que en la base de datos el idCursos
    // asigne automaticamente numeros cada ves mas grande al registrar un nuevo curso

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cursos")
    private Long idCursos;
    private String titulo;
    private String images;
    private String Categoria;
    private String description;
    private String instructor;
    private String video;
    private String video1;
    private String video2;
    private String video3;
    private String video4;
    private String video5;
    private String video6;
    private String video7;
    private String video8;
    private String video9;





}
