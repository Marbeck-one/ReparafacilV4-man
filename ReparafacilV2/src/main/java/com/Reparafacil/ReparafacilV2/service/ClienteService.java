package com.Reparafacil.ReparafacilV2.service;

import com.Reparafacil.ReparafacilV2.model.Cliente;
import java.util.List;

public interface ClienteService {
    List<Cliente> listar();
    Cliente buscarPorId(Long id);
    Cliente crear(Cliente cliente);
    Cliente actualizar(Long id, Cliente cliente);
    void eliminar(Long id);
}