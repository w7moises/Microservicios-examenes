package com.formacionbdi.microservicios.commons.examenes.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name="asignaturas")
@AllArgsConstructor
@Builder
public class Asignatura {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @JsonIgnoreProperties(value= {"hijos", "handler", "hibernateLazyInitializer"})
    @ManyToOne(fetch = FetchType.LAZY)
    private Asignatura padre;

    @JsonIgnoreProperties(value = {"padre", "handler", "hibernateLazyInitializer"}, allowSetters = true)
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "padre",cascade = CascadeType.ALL)
    private List<Asignatura> hijos;

    public Asignatura (){
        this.hijos = new ArrayList<>();
    }
}
