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

    private String diagnostico;
    private String solucion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoServicio estado = EstadoServicio.PENDIENTE;

    @Column(nullable = false)
    private LocalDateTime fechaSolicitud = LocalDateTime.now();

    // --- CORRECCIÓN DE RELACIONES ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    // Evita bucle infinito al traer el cliente y errores Lazy
    @JsonIgnoreProperties({"servicios", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    // Evita bucle con técnico y errores Lazy
    @JsonIgnoreProperties({"servicios", "agenda", "hibernateLazyInitializer", "handler"})
    private Tecnico tecnico;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Garantia garantia;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Agenda agenda;
}