package com.formacionbdi.microservicios.app.respuestas.controller;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class RespuestaController {
    @Autowired
    private RespuestaService respuestaService;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Iterable<Respuesta> respuestas){
        respuestas = ((List<Respuesta>)respuestas).stream().map(r -> {
            r.setAlumnoId(r.getAlumno().getId());
            return r;
        }).collect(Collectors.toList());
        Iterable<Respuesta> respuestasDb = respuestaService.saveAll(respuestas);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuestasDb);
    }


    @GetMapping("alumno/{alumnoId}/examen/{examenId}")
    public ResponseEntity<?> findRespuestabByAlumnoByExamen(@PathVariable Long alumnoId,@PathVariable Long examenId){
        Iterable<Respuesta> respuestas = respuestaService.findRespuestaByAlumnoByExamen(alumnoId,examenId);
        return ResponseEntity.ok(respuestas);
    }

    @GetMapping("/alumno/{alumnoId}/examenes-respondidos")
    public ResponseEntity<?> obtenerExamenesIdsConRespuestasAlumno(@PathVariable Long alumnoId){
        Iterable<Long> examenesId =respuestaService.findExamenesIdsConRespuestasByAlumno(alumnoId);
        return ResponseEntity.ok(examenesId);
    }

}
