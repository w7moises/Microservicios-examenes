package com.formacionbdi.microservicios.app.cursos.repository;

import com.formacionbdi.microservicios.app.cursos.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CursoRepository extends PagingAndSortingRepository<Curso, Long> {
    @Query("select c from Curso c join fetch c.cursoAlumnos a where a.alumnoId = ?1")
    Curso findCursoByAlumnoId(Long id);

    @Modifying
    @Query("delete from CursoAlumno ca where ca.alumnoId=?1")
    public void eliminarCursoAlumnoPorId(Long id);
}
