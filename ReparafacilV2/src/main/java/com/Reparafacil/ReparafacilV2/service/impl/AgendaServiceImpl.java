package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Agenda;
import com.Reparafacil.ReparafacilV2.repository.AgendaRepository;
import com.Reparafacil.ReparafacilV2.service.AgendaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AgendaServiceImpl implements AgendaService {

    private final AgendaRepository repo;

    public AgendaServiceImpl(AgendaRepository repo) {
        this.repo = repo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Agenda> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Agenda buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Slot de Agenda no encontrado: " + id));
    }

    @Override
    public Agenda crear(Agenda agenda) {
        return repo.save(agenda);
    }

    @Override
    public Agenda actualizar(Long id, Agenda agenda) {
        Agenda actual = buscarPorId(id);
        actual.setFechaHoraInicio(agenda.getFechaHoraInicio());
        actual.setFechaHoraFin(agenda.getFechaHoraFin());
        actual.setEstado(agenda.getEstado());
        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Agenda actual = buscarPorId(id);
        repo.delete(actual);
    }
}