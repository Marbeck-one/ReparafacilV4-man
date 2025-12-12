package com.Reparafacil.ReparafacilV2.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "mensajes")
public class Mensaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String remite; // Username o email de quien envía
    private String destinatario; // Username o email de quien recibe
    
    @Column(columnDefinition = "TEXT")
    private String contenido;
    
    private LocalDateTime fecha;

    // Constructores
    public Mensaje() {}

    public Mensaje(String remite, String destinatario, String contenido, LocalDateTime fecha) {
        this.remite = remite;
        this.destinatario = destinatario;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRemite() { return remite; }
    public void setRemite(String remite) { this.remite = remite; }
    public String getDestinatario() { return destinatario; }
    public void setDestinatario(String destinatario) { this.destinatario = destinatario; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public LocalDateTime getFecha() { return fecha; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
}