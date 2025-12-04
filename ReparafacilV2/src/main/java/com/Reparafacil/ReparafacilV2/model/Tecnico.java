package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tecnicos")
@Data
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;

    @Email(message = "Debe ser un email válido")
    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefono;

    @NotBlank(message = "La especialidad es obligatoria")
    private String especialidad;

    private boolean disponible = true;

    // Campo para la foto (URL)
    @Column(length = 500)
    private String foto;

    // --- CORRECCIÓN DE LISTAS ---

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    // Al pedir un técnico, no traemos todos los detalles pesados de sus servicios de vuelta
    @JsonIgnoreProperties({"tecnico", "cliente", "hibernateLazyInitializer", "handler"})
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"tecnico", "hibernateLazyInitializer", "handler"})
    private List<Agenda> agenda;
}