package com.Reparafacil.ReparafacilV2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor; // <--- NUEVO
import lombok.Data;
import lombok.NoArgsConstructor;  // <--- NUEVO

import java.util.List;

@Entity
@Table(name = "clientes")
@Data
@NoArgsConstructor  // <--- NECESARIO PARA QUE JACKSON LEA EL JSON
@AllArgsConstructor // <--- BUENA PRÁCTICA
public class Cliente {

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

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @Transient
    private String password;

    // --- Lista BLINDADA ---
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties({"cliente", "tecnico", "hibernateLazyInitializer", "handler"})
    private List<Servicio> servicios;
}