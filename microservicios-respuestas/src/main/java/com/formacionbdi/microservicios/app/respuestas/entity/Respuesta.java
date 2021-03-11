package com.formacionbdi.microservicios.app.respuestas.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.formacionbdi.microservicios.commons.alumnos.entity.Alumno;
import com.formacionbdi.microservicios.commons.examenes.entity.Pregunta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "respuestas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    //@ManyToOne(fetch = FetchType.LAZY)
    @Transient
    private Alumno alumno;

    @Column(name = "alumno_id")
    private Long alumnoId;

    @OneToOne(fetch = FetchType.LAZY)
    private Pregunta pregunta;

}
