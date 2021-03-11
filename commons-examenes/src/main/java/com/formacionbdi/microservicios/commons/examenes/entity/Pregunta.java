package com.formacionbdi.microservicios.commons.examenes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "preguntas")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pregunta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String texto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examen_id")
    @JsonIgnoreProperties(value = {"preguntas"})
    private Examen examen;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Pregunta)) {
            return false;
        }
        Pregunta p = (Pregunta) obj;
        return this.id != null && this.id.equals(p.getId());
    }
}
