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

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties("servicios")
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id") // Se puede asignar después
    @JsonIgnoreProperties("servicios")
    private Tecnico tecnico;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("servicio")
    private Garantia garantia;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("servicio")
    private Agenda agenda; // La cita específica para este servicio
}