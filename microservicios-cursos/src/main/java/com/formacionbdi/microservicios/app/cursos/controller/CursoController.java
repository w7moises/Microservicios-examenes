package com.formacionbdi.microservicios.app.cursos.controller;

import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import com.formacionbdi.microservicios.app.cursos.entity.CursoAlumno;
import com.formacionbdi.microservicios.app.cursos.services.CursoService;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.controllers.CommonController;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class CursoController extends CommonController<Curso, CursoService> {
    @Value("${config.balanceador.test}")
    private String balanceadorTest;

    @DeleteMapping("/eliminar-alumno/{id}")
    public ResponseEntity<?> eliminarCursoAlumnoPorId(@PathVariable Long id){
        service.eliminarCursoAlumnoporId(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/balanceador-test")
    public ResponseEntity<?> balanceadorTest() {
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("balanceador", balanceadorTest);
        response.put("cursos", service.findAll());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> findAll() {
        List<Curso> cursos = ((List<Curso>) service.findAll()).stream().map(curso ->
        {
            curso.getCursoAlumnos().forEach(ca->{
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(cursos);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (optionalCurso.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Curso curso =optionalCurso.get();
        if(curso.getCursoAlumnos().isEmpty() == false){
            List<Long> ids = curso.getCursoAlumnos().stream().map(cursoAlumno ->{
                return cursoAlumno.getAlumnoId();
            }).collect(Collectors.toList());

            List<Alumno> alumnos = (List<Alumno>) service.obtenerAlumnosPorCurso(ids);
            curso.setAlumnos(alumnos);
        }
        return ResponseEntity.ok(curso);
    }

    @Override
    @GetMapping("/pagina")
    public ResponseEntity<?> findAll(Pageable pageable) {
        Page<Curso> cursos = service.findAll(pageable).map(curso -> {
            curso.getCursoAlumnos().forEach(ca->{
                Alumno alumno = new Alumno();
                alumno.setId(ca.getAlumnoId());
                curso.addAlumno(alumno);
            });
            return curso;
        });
        return ResponseEntity.ok().body(cursos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id) {
        if(result.hasErrors()){
            return this.validar(result);
        }
        Optional<Curso> optionalCurso = service.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        cursoDb.setNombre(curso.getNombre());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/asignar-alumnos")
    public ResponseEntity<?> assignStudent(@RequestBody List<Alumno> alumnos, @PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();

        alumnos.forEach(a -> {
            CursoAlumno cursoAlumno = new CursoAlumno();
            cursoAlumno.setAlumnoId(a.getId());
            cursoAlumno.setCurso(cursoDb);
            cursoDb.addCursoAlumno(cursoAlumno);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-alumno")
    public ResponseEntity<?> deleteStudent(@RequestBody Alumno alumno, @PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        CursoAlumno cursoAlumno = new CursoAlumno();
        cursoAlumno.setAlumnoId(alumno.getId());
        cursoDb.removeCursoAlumno(cursoAlumno);

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<?> studentById(@PathVariable Long id) {
        Curso curso = service.findCursoByAlumnoId(id);
        if (curso != null){
            List<Long> examenesIds = (List<Long>) service.obtenerExamenesIdsConRespuestasAlumno(id);
            List<Examen> examenes = curso.getExamenes().stream().map(examen -> {
                if(examenesIds.contains(examen.getId())){
                    examen.setRespondido(true);
                }
                return examen;
            }).collect(Collectors.toList());
            curso.setExamenes(examenes);
        }
        return ResponseEntity.ok(curso);
    }

    @PutMapping("/{id}/asignar-examenes")
    public ResponseEntity<?> assignExams(@RequestBody List<Examen> examenes, @PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();

        examenes.forEach(e -> {
            cursoDb.addExamen(e);
        });

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }

    @PutMapping("/{id}/eliminar-examenes")
    public ResponseEntity<?> deleteExam(@RequestBody Examen examen, @PathVariable Long id) {
        Optional<Curso> optionalCurso = service.findById(id);
        if (!optionalCurso.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Curso cursoDb = optionalCurso.get();
        cursoDb.removeExamen(examen);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(cursoDb));
    }
}
