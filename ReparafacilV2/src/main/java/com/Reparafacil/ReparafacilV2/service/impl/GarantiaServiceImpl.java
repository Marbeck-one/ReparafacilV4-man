package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Garantia;
import com.Reparafacil.ReparafacilV2.repository.GarantiaRepository;
import com.Reparafacil.ReparafacilV2.service.GarantiaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GarantiaServiceImpl implements GarantiaService {

    private final GarantiaRepository repo;

    public GarantiaServiceImpl(GarantiaRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Garantia> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Garantia buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Garantía no encontrada: " + id));
    }

    @Override
    public Garantia crear(Garantia garantia) {
        return repo.save(garantia);
    }

    @Override
    public Garantia actualizar(Long id, Garantia garantia) {
        Garantia actual = buscarPorId(id);
        actual.setFechaInicio(garantia.getFechaInicio());
        actual.setFechaFin(garantia.getFechaFin());
        actual.setDetalles(garantia.getDetalles());
        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Garantia actual = buscarPorId(id);
        repo.delete(actual);
    }
}