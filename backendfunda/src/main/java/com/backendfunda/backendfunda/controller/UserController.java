package com.backendfunda.backendfunda.controller;

import com.backendfunda.backendfunda.model.User;
import com.backendfunda.backendfunda.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Permite solicitudes desde http://localhost:5173
@CrossOrigin("http://localhost:5173/")
// Raíz para las rutas de usuario
@RequestMapping("/api/auth/u/")
public class UserController {
    // Indica a Spring que inyecte una instancia de UserRepository aqui
    @Autowired
    // Repositorio para acceder a los usuarios
    private UserRepository userRepository;
    // Método para agregar un nuevo usuario a través de una solicitud POST
    @PostMapping("/user")
// Método para crear un nuevo usuario usando los datos proporcionados en el cuerpo de la solicitud
    User newUser(@RequestBody User newUser){
        return userRepository.save(newUser);
    } // Guarda el nuevo usuario en la base de datos

    @GetMapping("/users") // Método para obtener todos los usuarios mediante una solicitud GET
        // Método para obtener una lista de todos los usuarios existentes en la base de datos
    List<User> getAllUsers(){
        return userRepository.findAll();
    }// Devuelve todos los usuarios almacenados
}
