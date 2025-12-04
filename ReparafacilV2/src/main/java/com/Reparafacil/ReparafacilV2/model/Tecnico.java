package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor; // <--- NUEVO
import lombok.Data;
import lombok.NoArgsConstructor;  // <--- NUEVO

import java.util.List;

@Entity
@Table(name = "tecnicos")
@Data
@NoArgsConstructor  // <--- NECESARIO
@AllArgsConstructor // <--- NECESARIO
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

    @Column(length = 500)
    private String foto;

    @Transient
    private String password;

    // --- Listas BLINDADAS ---

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"tecnico", "cliente", "hibernateLazyInitializer", "handler"})
    private List<Servicio> servicios;

    @OneToMany(mappedBy = "tecnico", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"tecnico", "servicio", "hibernateLazyInitializer", "handler"})
    private List<Agenda> agenda;
}