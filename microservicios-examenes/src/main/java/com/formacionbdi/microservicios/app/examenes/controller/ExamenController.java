package com.formacionbdi.microservicios.app.examenes.controller;

import com.formacionbdi.microservicios.app.examenes.services.ExamenService;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class ExamenController extends CommonController<Examen, ExamenService> {
    @PutMapping("/{id}")
    public ResponseEntity<?> udpate(@Valid @RequestBody Examen examen, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
            return this.validar(result);
        }

        Optional<Examen> optionalExamen = service.findById(id);
        if (!optionalExamen.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Examen examenDb = optionalExamen.get();
        examenDb.setNombre(examen.getNombre());

        examenDb.getPreguntas().stream().filter(pdb -> !examen.getPreguntas().contains(pdb)).forEach(examenDb::removePregunta);

        examenDb.setPreguntas(examen.getPreguntas());

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(examenDb));
    }

    @GetMapping("/filtrar/{term}")
    public ResponseEntity<?> filtrar(@PathVariable String term){
        return ResponseEntity.ok(service.findByNombre(term));
    }

    @GetMapping("/asignaturas")
    public ResponseEntity<?> listarAsignaturas(){
        return ResponseEntity.ok(service.findAllAsignaturas());
    }
}
