package com.backendfunda.backendfunda.controller;

import com.backendfunda.backendfunda.dtos.DtoCursos;
import com.backendfunda.backendfunda.model.Cursos;
import com.backendfunda.backendfunda.repository.CursosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/")
public class CursosController {

    @Autowired
    private CursosRepository cursosRepository;

    // Obtiene todos los cursos
    @GetMapping("/cursos")
    public List<Cursos> getCursos() {
        return cursosRepository.findAll();
    }

    // Obtiene un curso por su ID
    @GetMapping("/cursos/{id}")
    public ResponseEntity<?> getCursos(@PathVariable Long id) {
        Optional<Cursos> cursoOptional = cursosRepository.findById(id);

        if (cursoOptional.isPresent()) {
            Cursos curso = cursoOptional.get();
            return ResponseEntity.ok(curso);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado por ese ID");
        }
    }

    // Elimina un curso por su ID
    @DeleteMapping("/cursos/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id) {
        if (cursosRepository.existsById(id)) {
            cursosRepository.deleteById(id);
            return ResponseEntity.ok("Curso eliminado exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ID no encontrado");
        }
    }

    // Edita un curso existente por su ID
    @PutMapping("cursoEdit/{id}")
    public ResponseEntity<String> editarCurso(@PathVariable Long id, @RequestBody DtoCursos dtoCursos) {
        Optional<Cursos> optionalCursos = cursosRepository.findById(id);
        if (optionalCursos.isPresent()) {
            Cursos cursos = optionalCursos.get();
            // Actualiza los videos del curso con los nuevos videos
            // sería como editar los enlaces de los videos
            cursos.setVideo(dtoCursos.getVideo());
            // se actualizan otros enlaces de video de manera similar
            cursosRepository.save(cursos);
            return ResponseEntity.ok("Curso Modificado Exitosamente");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Curso no encontrado, verifica el ID: " + id);
        }
    }

    // Crea un nuevo curso con la información proporcionada
    @PostMapping("/CrearCurso")
    public ResponseEntity<String> crearCurso(@RequestBody DtoCursos dtoCursos) {
        // Verifica si ya existe un curso con el mismo título
        if (cursosRepository.existsByTitulo(dtoCursos.getTitulo())) {
            return new ResponseEntity<>("El curso con ese titulo ya existe, intenta con otro titulo", HttpStatus.BAD_REQUEST);
        }
        // Crea un nuevo curso con los detalles proporcionados
        Cursos cursos = new Cursos();
        cursos.setTitulo(dtoCursos.getTitulo());
        cursos.setDescription(dtoCursos.getDescription());
        cursos.setImages(dtoCursos.getImages());
        cursos.setInstructor(dtoCursos.getInstructor());
        cursos.setCategoria(dtoCursos.getCategoria());
        // Configura los enlaces de video del curso
        cursos.setVideo(dtoCursos.getVideo());
        cursosRepository.save(cursos); // Guarda el nuevo curso en la base de datos
        return new ResponseEntity<>("Registro de cursos exitoso", HttpStatus.OK);
    }
}
