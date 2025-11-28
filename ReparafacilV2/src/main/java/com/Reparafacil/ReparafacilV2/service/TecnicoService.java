package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Tecnico;
import java.util.List;

public interface TecnicoService {
    List<Tecnico> listar();
    Tecnico buscarPorId(Long id);
    Tecnico crear(Tecnico tecnico);
    Tecnico actualizar(Long id, Tecnico tecnico);
    void eliminar(Long id);
}