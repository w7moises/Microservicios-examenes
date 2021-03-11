package com.formacionbdi.microservicios.app.respuestas.service.impl;

import com.formacionbdi.microservicios.app.respuestas.entity.Respuesta;
import com.formacionbdi.microservicios.app.respuestas.repository.RespuestaRepository;
import com.formacionbdi.microservicios.app.respuestas.service.RespuestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class RespuestaServiceImpl implements RespuestaService {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Override
    @Transactional
    public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
        return respuestaRepository.saveAll(respuestas);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
        return respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId,examenId);
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<Long> findExamenesIdsConRespuestasByAlumno(Long alumnoId) {
        return respuestaRepository.findExamenesIdsConRespuestasByAlumno(alumnoId);
    }
}
