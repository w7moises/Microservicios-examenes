package com.formacionbdi.microservicios.app.cursos.clients;

import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "microservicio-usuarios")
public interface AlumnoFeignClient {
    @GetMapping("/alumnos-por-curso")
    Iterable<Alumno> obtenerAlumnosPorCurso(@RequestParam List<Long> ids);
}
