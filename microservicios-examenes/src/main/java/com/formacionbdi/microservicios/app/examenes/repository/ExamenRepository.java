package com.formacionbdi.microservicios.app.examenes.repository;

import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ExamenRepository extends PagingAndSortingRepository<Examen, Long> {
    @Query("select e from Examen e where e.nombre like %?1%")
    List<Examen> findByNombre(String term);
}
