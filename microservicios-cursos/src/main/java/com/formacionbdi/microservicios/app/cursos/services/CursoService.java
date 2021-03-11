package com.formacionbdi.microservicios.app.cursos.services;

import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.services.CommonService;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface CursoService extends CommonService<Curso> {
    Curso findCursoByAlumnoId(Long id);
    Iterable<Long> obtenerExamenesIdsConRespuestasAlumno(Long alumnoId);
    Iterable<Alumno> obtenerAlumnosPorCurso(List<Long> ids);
    void eliminarCursoAlumnoporId(Long id);
}
