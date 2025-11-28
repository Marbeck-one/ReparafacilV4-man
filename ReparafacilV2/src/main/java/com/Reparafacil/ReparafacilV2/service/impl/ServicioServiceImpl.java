package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Servicio;
import com.Reparafacil.ReparafacilV2.repository.ServicioRepository;
import com.Reparafacil.ReparafacilV2.service.ServicioService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioServiceImpl implements ServicioService {

    private final ServicioRepository repo;

    public ServicioServiceImpl(ServicioRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Servicio> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Servicio buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Servicio no encontrado: " + id));
    }

    @Override
    public Servicio crear(Servicio servicio) {
        // En un caso real, buscaríamos Cliente y Tecnico por ID y los asignaríamos
        // aquí para asegurar que existen.
        return repo.save(servicio);
    }

    @Override
    public Servicio actualizar(Long id, Servicio servicio) {
        Servicio actual = buscarPorId(id);
        actual.setDescripcionProblema(servicio.getDescripcionProblema());
        actual.setDiagnostico(servicio.getDiagnostico());
        actual.setSolucion(servicio.getSolucion());
        actual.setEstado(servicio.getEstado());
        // No actualizamos relaciones (cliente, tecnico) aquí,
        // eso sería en endpoints más complejos (ej: /api/servicios/1/asignar/2)
        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Servicio actual = buscarPorId(id);
        repo.delete(actual);
    }
}