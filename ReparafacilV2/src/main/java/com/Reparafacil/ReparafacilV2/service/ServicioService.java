package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Servicio;
import java.util.List;

public interface ServicioService {
    List<Servicio> listar();
    Servicio buscarPorId(Long id);
    Servicio crear(Servicio servicio);
    Servicio actualizar(Long id, Servicio servicio);
    void eliminar(Long id);
}