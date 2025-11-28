package com.Reparafacil.ReparafacilV2.repository;

import com.Reparafacil.ReparafacilV2.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> findByEmail(String email);
}