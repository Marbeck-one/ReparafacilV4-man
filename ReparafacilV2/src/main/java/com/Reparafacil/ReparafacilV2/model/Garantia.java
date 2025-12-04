package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor; // <--- NUEVO
import lombok.Data;
import lombok.NoArgsConstructor;  // <--- NUEVO

import java.time.LocalDate;

@Entity
@Table(name = "garantias")
@Data
@NoArgsConstructor  // <--- NECESARIO
@AllArgsConstructor // <--- NECESARIO
public class Garantia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    @JsonIgnoreProperties({"garantia", "agenda", "hibernateLazyInitializer", "handler"}) 
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    private String detalles;

    @Enumerated(EnumType.STRING)
    private EstadoGarantia estado = EstadoGarantia.PENDIENTE;
}