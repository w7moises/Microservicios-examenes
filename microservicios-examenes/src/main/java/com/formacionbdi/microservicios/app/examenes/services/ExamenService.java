package com.formacionbdi.microservicios.app.examenes.services;

import com.formacionbdi.microservicios.commons.examenes.entity.Asignatura;
import com.formacionbdi.microservicios.commons.examenes.entity.Examen;
import com.formacionbdi.microservicios.commons.services.CommonService;

import java.util.List;

public interface ExamenService extends CommonService<Examen> {
    public List<Examen> findByNombre(String term);

    public List<Asignatura> findAllAsignaturas();
}
