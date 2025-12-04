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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id")
    @JsonIgnoreProperties({"garantia", "agenda", "hibernateLazyInitializer", "handler"}) 
    private Servicio servicio;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    private String detalles;

    // Aquí es donde daba error, ahora funcionará al existir el archivo EstadoGarantia.java
    @Enumerated(EnumType.STRING)
    private EstadoGarantia estado = EstadoGarantia.PENDIENTE;
}