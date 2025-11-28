package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "garantias")
@Data
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;

    @NotBlank(message = "Los detalles de la garantía son obligatorios")
    @Column(columnDefinition = "TEXT")
    private String detalles;

    // --- Relaciones ---

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", referencedColumnName = "id")
    @JsonIgnoreProperties("garantia")
    private Servicio servicio;
}