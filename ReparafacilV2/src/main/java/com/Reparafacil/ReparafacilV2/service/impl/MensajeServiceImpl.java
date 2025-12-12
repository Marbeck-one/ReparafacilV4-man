package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.model.Mensaje;
import com.Reparafacil.ReparafacilV2.repository.MensajeRepository;
import com.Reparafacil.ReparafacilV2.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MensajeServiceImpl implements MensajeService {

    @Autowired
    private MensajeRepository mensajeRepository;

    @Override
    public Mensaje enviarMensaje(String de, String para, String texto) {
        Mensaje msg = new Mensaje(de, para, texto, LocalDateTime.now());
        return mensajeRepository.save(msg);
    }

    @Override
    public List<Mensaje> obtenerConversacion(String usuarioA, String usuarioB) {
        // Busca la charla cruzada: A->B o B->A
        return mensajeRepository.findByRemiteAndDestinatarioOrDestinatarioAndRemiteOrderByFechaAsc(
            usuarioA, usuarioB, usuarioA, usuarioB
        );
    }
}