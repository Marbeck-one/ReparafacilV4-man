package com.Reparafacil.ReparafacilV2.repository;

import com.Reparafacil.ReparafacilV2.model.EstadoServicio;
import com.Reparafacil.ReparafacilV2.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
    List<Servicio> findByEstado(EstadoServicio estado);
    List<Servicio> findByClienteId(Long clienteId);
    List<Servicio> findByTecnicoId(Long tecnicoId);
}