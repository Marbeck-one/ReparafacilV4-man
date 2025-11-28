package com.Reparafacil.ReparafacilV2.repository;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
}
