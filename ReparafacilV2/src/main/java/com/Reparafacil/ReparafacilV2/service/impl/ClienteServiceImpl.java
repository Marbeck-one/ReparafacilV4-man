package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Cliente;
import com.Reparafacil.ReparafacilV2.repository.ClienteRepository;
import com.Reparafacil.ReparafacilV2.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.lang.NonNull;
import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;

    public ClienteServiceImpl(ClienteRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));
    }

    @Override
    public Cliente crear(Cliente cliente) {
        return repo.save(cliente);
    }

    @Override
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente actual = buscarPorId(id);
        actual.setNombre(cliente.getNombre());
        actual.setApellido(cliente.getApellido());
        actual.setEmail(cliente.getEmail()); 
        actual.setTelefono(cliente.getTelefono());
        actual.setDireccion(cliente.getDireccion());
        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Cliente actual = buscarPorId(id);
        repo.delete(actual);
    }
}