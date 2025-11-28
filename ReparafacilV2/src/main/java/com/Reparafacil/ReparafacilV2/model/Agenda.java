package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "agenda")
@Data
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha/hora de inicio es obligatoria")
    private LocalDateTime fechaHoraInicio;

    @NotNull(message = "La fecha/hora de fin es obligatoria")
    private LocalDateTime fechaHoraFin;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoAgenda estado = EstadoAgenda.DISPONIBLE;

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    @JsonIgnoreProperties("agenda")
    private Tecnico tecnico;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    @JsonIgnoreProperties("agenda")
    private Servicio servicio; // El servicio que reserva este slot
}