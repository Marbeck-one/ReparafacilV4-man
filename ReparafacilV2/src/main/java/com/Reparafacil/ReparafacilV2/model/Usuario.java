package com.Reparafacil.ReparafacilV2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor; // <--- NUEVO
import lombok.Data;
import lombok.NoArgsConstructor;  // <--- NUEVO

@Entity 
@Table(name = "usuarios") 
@Data
@NoArgsConstructor  // <--- NECESARIO
@AllArgsConstructor // <--- NECESARIO
public class Usuario {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    @Column(unique = true, nullable = false) 
    private String username;
    
    @Column(nullable = false) 
    private String password;
    
    @Enumerated(EnumType.STRING) 
    private Rol rol;
}