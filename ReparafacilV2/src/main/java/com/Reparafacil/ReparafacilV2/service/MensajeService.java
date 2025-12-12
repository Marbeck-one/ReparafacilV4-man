package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Mensaje;
import java.util.List;

public interface MensajeService {
    Mensaje enviarMensaje(String de, String para, String texto);
    List<Mensaje> obtenerConversacion(String usuarioA, String usuarioB);
}