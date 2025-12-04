package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "servicios")
@Data
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción del problema es obligatoria")
    @Column(nullable = false)
    private String descripcionProblema;

    private String diagnostico; // Llenado por el técnico

    private String solucion; // Llenado por el técnico

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoServicio estado = EstadoServicio.PENDIENTE;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    // --- Relaciones (CORREGIDAS) ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    // Cortamos el bucle hacia 'servicios' del cliente y evitamos error de carga Lazy
    @JsonIgnoreProperties({"servicios", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    // Cortamos el bucle hacia 'servicios' y 'agenda' del técnico
    @JsonIgnoreProperties({"servicios", "agenda", "hibernateLazyInitializer", "handler"})
    private Tecnico tecnico;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Garantia garantia;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Agenda agenda;
}