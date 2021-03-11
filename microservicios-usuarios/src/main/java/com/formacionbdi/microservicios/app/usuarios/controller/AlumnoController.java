package com.formacionbdi.microservicios.app.usuarios.controller;

import com.formacionbdi.microservicios.app.usuarios.services.AlumnoService;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class AlumnoController extends CommonController<Alumno, AlumnoService> {

    @GetMapping("/alumnos-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.findAllById(ids));
    }
    @GetMapping("/uploads/img/{id}")
    public ResponseEntity<?> verFoto(@PathVariable Long id){
        Optional<Alumno> optionalAlumno = service.findById(id);
        if (!optionalAlumno.isPresent() || optionalAlumno.get().getFoto() == null) {
            return ResponseEntity.notFound().build();
        }
        Resource imagen = new ByteArrayResource(optionalAlumno.get().getFoto());

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imagen);
    }

    @PostMapping("/crear-con-foto")
    public ResponseEntity<?> createConFoto(@Valid Alumno alumno, BindingResult result, @RequestParam MultipartFile archivo) throws IOException {
        if(!archivo.isEmpty()){
            alumno.setFoto(archivo.getBytes());
        }
        return super.create(alumno, result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Alumno alumno, BindingResult result, @PathVariable Long id) {

        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Alumno> optionalAlumno = service.findById(id);
        if (!optionalAlumno.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = optionalAlumno.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @PutMapping("/editar-con-foto/{id}")
    public ResponseEntity<?> updateConFoto(@Valid Alumno alumno, BindingResult result, @PathVariable Long id,@RequestParam MultipartFile archivo) throws IOException {

        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Alumno> optionalAlumno = service.findById(id);
        if (!optionalAlumno.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Alumno alumnoDb = optionalAlumno.get();
        alumnoDb.setNombre(alumno.getNombre());
        alumnoDb.setApellido(alumno.getApellido());
        alumnoDb.setEmail(alumno.getEmail());

        if(!archivo.isEmpty()){
            alumnoDb.setFoto(archivo.getBytes());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(alumnoDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filterByNameAndLastName(@PathVariable String term) {
        return ResponseEntity.ok(service.findByNombreOrApellido(term));
    }
}
