package com.formacionbdi.microservicios.app.respuestas.service;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;

public interface RespuestaService {
    Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
    Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId,Long examenId);
    Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId);
}
