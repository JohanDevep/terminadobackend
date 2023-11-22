package com.backendfunda.backendfunda.controller;

import java.util.List;
import java.util.Optional;
import com.backendfunda.backendfunda.dtos.DtoInstructores;
import com.backendfunda.backendfunda.model.Instructores;
import com.backendfunda.backendfunda.repository.InstructoresRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/")
public class InstructoresController {

    @Autowired
    private InstructoresRepository instructoresRepository;

    // Obtiene todos los instructores
    @GetMapping("/Instructores")
    public List<Instructores> getInstructores() {
        return instructoresRepository.findAll();
    }

    // Crea un nuevo instructor
    @PostMapping("/CrearInstructor")
    public ResponseEntity<String> crearInstructores(@RequestBody DtoInstructores dtoInstructores) {
        if (instructoresRepository.existsBynombre(dtoInstructores.getNombre())) {
            return new ResponseEntity<>("El nombre del instructor ya existe, intenta nuevamente con otro nombre", HttpStatus.BAD_REQUEST);
        }
        Instructores instructores = new Instructores();
        instructores.setTitulos(dtoInstructores.getTitulos());
        instructores.setDescription(dtoInstructores.getDescription());
        instructores.setImages(dtoInstructores.getImages());
        instructores.setNombre(dtoInstructores.getNombre());
        instructores.setEstado(dtoInstructores.getEstado());
        instructoresRepository.save(instructores);
        return new ResponseEntity<>("Se Registro Exitosamente El Instructor", HttpStatus.OK);
    }

    // Elimina un instructor por su ID
    @DeleteMapping("/instructores/eliminar/{instructorId}")
    public ResponseEntity<String> eliminarInstructor(@PathVariable Long instructorId) {
        if (!instructoresRepository.existsById(instructorId)) {
            return new ResponseEntity<>("Instructor no encontrado", HttpStatus.NOT_FOUND);
        }
        instructoresRepository.deleteById(instructorId);
        return new ResponseEntity<>("Instructor eliminado exitosamente", HttpStatus.OK);
    }

    // Edita un instructor por su ID
    @PutMapping("/instructores/editar/{instructorId}")
    public ResponseEntity<String> editarInstructor(
            @PathVariable Long instructorId,
            @RequestBody DtoInstructores dtoInstructores
    ) {
        try {
            Optional<Instructores> optionalInstructor = instructoresRepository.findById(instructorId);

            if (optionalInstructor.isPresent()) {
                Instructores instructor = optionalInstructor.get();

                String nuevoNombre = dtoInstructores.getNombre();
                if (!nuevoNombre.equals(instructor.getNombre()) && instructoresRepository.existsByNombre(nuevoNombre)) {
                    // Verifica si el nombre ya existe para otro instructor
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe un instructor con el nombre '" + nuevoNombre + "'. Intenta con otro nombre");
                }

                // Actualiza los detalles del instructor con la información proporcionada
                instructor.setTitulos(dtoInstructores.getTitulos());
                instructor.setImages(dtoInstructores.getImages());
                instructor.setDescription(dtoInstructores.getDescription());
                instructor.setNombre(dtoInstructores.getNombre());
                instructor.setEstado(dtoInstructores.getEstado());

                // Guarda los cambios en el instructor
                Instructores instructorActualizado = instructoresRepository.save(instructor);

                return ResponseEntity.ok().body("Instructor modificado correctamente");

            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró ningún instructor con el ID: " + instructorId);
            }
        } catch (ResponseStatusException e) {
            // Manejo específico de excepción ResponseStatusException
            return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
        } catch (Exception e) {
            // Manejo general de excepciones
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud: " + e.getMessage());
        }
    }
}
