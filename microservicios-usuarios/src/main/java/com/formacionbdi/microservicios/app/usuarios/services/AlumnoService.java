package com.formacionbdi.microservicios.app.usuarios.services;

import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AlumnoService extends CommonService<Alumno> {
    List<Alumno> findByNombreOrApellido(String term);
    Iterable<Alumno> findAllById(Iterable<Long> ids);
    void eliminarCursoAlumnoPorId(Long id);
}
