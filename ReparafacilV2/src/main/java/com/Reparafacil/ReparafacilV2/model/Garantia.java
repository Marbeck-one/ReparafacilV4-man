package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "garantias")
@Data
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación OneToOne con Servicio
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    // IMPORTANTE: Esto evita que al leer la garantía intente leer todo el servicio de nuevo y falle
    @JsonIgnoreProperties({"garantia", "agenda", "cliente", "tecnico", "hibernateLazyInitializer", "handler"})
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    private String detalles;
}