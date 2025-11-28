package com.Reparafacil.ReparafacilV2.repository;

import com.Reparafacil.ReparafacilV2.model.Garantia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GarantiaRepository extends JpaRepository<Garantia, Long> {
    Optional<Garantia> findByServicioId(Long servicioId);
}