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

    // --- CAMBIO CLAVE AQUÍ: cascade = CascadeType.ALL ---
    // Esto permite crear el Servicio automáticamente al guardar la Agenda
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL) 
    @JoinColumn(name = "servicio_id")
    @JsonIgnoreProperties({"agenda", "garantia", "cliente", "tecnico", "hibernateLazyInitializer", "handler"})
    private Servicio servicio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
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