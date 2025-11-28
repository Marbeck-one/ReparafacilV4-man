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
    private String especialidad; // E.g., "Refrigeración", "Electrónica"

    private boolean disponible = true;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("tecnico")
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("tecnico")
    private List<Agenda> agenda;
}