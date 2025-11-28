package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Tecnico;
import com.Reparafacil.ReparafacilV2.repository.TecnicoRepository;
import com.Reparafacil.ReparafacilV2.service.TecnicoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository repo;

    public TecnicoServiceImpl(TecnicoRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tecnico> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tecnico buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Técnico no encontrado: " + id));
    }

    @Override
    public Tecnico crear(Tecnico tecnico) {
        return repo.save(tecnico);
    }

    @Override
    public Tecnico actualizar(Long id, Tecnico tecnico) {
        Tecnico actual = buscarPorId(id);
        actual.setNombre(tecnico.getNombre());
        actual.setApellido(tecnico.getApellido());
        actual.setEmail(tecnico.getEmail());
        actual.setTelefono(tecnico.getTelefono());
        actual.setEspecialidad(tecnico.getEspecialidad());
        actual.setDisponible(tecnico.isDisponible());
        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Tecnico actual = buscarPorId(id);
        repo.delete(actual);
    }
}