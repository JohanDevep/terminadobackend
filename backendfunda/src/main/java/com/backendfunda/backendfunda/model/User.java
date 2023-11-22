package com.backendfunda.backendfunda.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {
    //Llave primaria de la tabla
    @Id
    // @GeneratedValue se usa para generar numeros unicos en idCursos.
    // GenerationType.IDENTITY se usa para que en la base de datos el idCursos
    // asigne automaticamente numeros cada ves mas grande al registrar un nuevo curso
    @GeneratedValue
    private Long id;

    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellido")
    private String apellido;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "correo")
    private String correo;
    @Column(name = "password")
    private String password;

}
