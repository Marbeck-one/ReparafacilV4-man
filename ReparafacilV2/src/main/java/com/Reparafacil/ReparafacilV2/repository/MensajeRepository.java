package com.Reparafacil.ReparafacilV2.repository;

import com.Reparafacil.ReparafacilV2.model.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensajeRepository extends JpaRepository<Mensaje, Long> {
    // Busca mensajes donde (Yo soy emisor Y el otro receptor) O (Yo soy receptor Y el otro emisor)
    // Ordenados por fecha para que salga como chat
    List<Mensaje> findByRemiteAndDestinatarioOrDestinatarioAndRemiteOrderByFechaAsc(
        String remite1, String destinatario1, 
        String destinatario2, String remite2
    );
}