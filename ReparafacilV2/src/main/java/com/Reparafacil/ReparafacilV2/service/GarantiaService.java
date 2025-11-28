package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Garantia;
import java.util.List;

public interface GarantiaService {
    List<Garantia> listar();
    Garantia buscarPorId(Long id);
    Garantia crear(Garantia garantia);
    Garantia actualizar(Long id, Garantia garantia);
    void eliminar(Long id);
}