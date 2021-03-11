package com.formacionbdi.microservicios.app.examenes.repository;

import com.formacionbdi.microservicios.commons.examenes.entity.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsignaturaRepository extends JpaRepository <Asignatura,Long > {
}
