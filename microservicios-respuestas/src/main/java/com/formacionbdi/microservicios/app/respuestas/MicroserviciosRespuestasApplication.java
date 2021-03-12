package com.formacionbdi.microservicios.app.respuestas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EntityScan({"com.formacionbdi.microservicios.app.respuestas.entity",
"com.formacionbdi.microservicios.commons.alumnos.entity",
"com.formacionbdi.microservicios.commons.examenes.entity"})
public class MicroserviciosRespuestasApplication {

    public static void main(String[] args) {
        SpringApplication.run(MicroserviciosRespuestasApplication.class, args);
    }

}
