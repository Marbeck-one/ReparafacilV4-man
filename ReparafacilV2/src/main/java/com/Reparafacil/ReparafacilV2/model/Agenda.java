package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda")
@Data
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relación con Servicio
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    // Cortamos el ciclo hacia Servicio
    @JsonIgnoreProperties({"agenda", "garantia", "cliente", "tecnico", "hibernateLazyInitializer", "handler"})
    private Servicio servicio;

    // Relación con Técnico
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    // Cortamos el ciclo hacia Técnico
    @JsonIgnoreProperties({"agenda", "servicios", "hibernateLazyInitializer", "handler"})
    private Tecnico tecnico;

    @Column(nullable = false)
    private LocalDateTime fechaHoraInicio;

    @Column(nullable = false)
    private LocalDateTime fechaHoraFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAgenda estado = EstadoAgenda.PENDIENTE;
}