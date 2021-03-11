package com.formacionbdi.microservicios.app.cursos.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "cursos_alumnos")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CursoAlumno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "alumno_id",unique = true)
    private Long alumnoId;

    @JsonIgnoreProperties(value = {"cursoAlumnos"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CursoAlumno)) {
            return false;
        }
        CursoAlumno a = (CursoAlumno) obj;
        return this.alumnoId != null && this.alumnoId.equals(a.getAlumnoId());
    }
}
