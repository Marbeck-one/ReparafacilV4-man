package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Agenda;
import java.util.List;

public interface AgendaService {
    List<Agenda> listar();
    Agenda buscarPorId(Long id);
    Agenda crear(Agenda agenda);
    Agenda actualizar(Long id, Agenda agenda);
    void eliminar(Long id);
}