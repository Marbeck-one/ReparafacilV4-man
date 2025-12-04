package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor; // <--- AGREGADO
import lombok.Data;
import lombok.NoArgsConstructor;  // <--- AGREGADO

import java.time.LocalDateTime;

@Entity
@Table(name = "servicios")
@Data
@NoArgsConstructor  // <--- NECESARIO PARA JSON
@AllArgsConstructor // <--- RECOMENDADO
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

    // --- Relaciones ---

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"servicios", "hibernateLazyInitializer", "handler"})
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tecnico_id")
    @JsonIgnoreProperties({"servicios", "agenda", "hibernateLazyInitializer", "handler"})
    private Tecnico tecnico;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Garantia garantia;

    @OneToOne(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"servicio", "hibernateLazyInitializer", "handler"})
    private Agenda agenda;
}