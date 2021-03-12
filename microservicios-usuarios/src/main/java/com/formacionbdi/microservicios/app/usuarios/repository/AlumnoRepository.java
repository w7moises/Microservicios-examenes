package com.formacionbdi.microservicios.app.usuarios.repository;

import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AlumnoRepository extends PagingAndSortingRepository<Alumno, Long> {
    @Query("select a from Alumno a where upper(a.nombre) like upper(concat('%',?1,'%')) or upper(a.apellido) like upper(concat('%',?1,'%'))")
    List<Alumno> findByNombreOrApellido(String term);
}
