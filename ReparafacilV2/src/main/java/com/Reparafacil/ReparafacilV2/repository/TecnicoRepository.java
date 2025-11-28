package com.Reparafacil.ReparafacilV2.repository;

import com.Reparafacil.ReparafacilV2.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
    Optional<Tecnico> findByEmail(String email);
    List<Tecnico> findByEspecialidadAndDisponibleTrue(String especialidad);
}