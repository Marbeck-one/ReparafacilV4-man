package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Rol;
import com.Reparafacil.ReparafacilV2.model.Tecnico;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import com.Reparafacil.ReparafacilV2.repository.TecnicoRepository;
import com.Reparafacil.ReparafacilV2.repository.UsuarioRepository;
import com.Reparafacil.ReparafacilV2.service.TecnicoService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TecnicoServiceImpl implements TecnicoService {

    private final TecnicoRepository repo;
    private final UsuarioRepository usuarioRepo; // Necesario para crear el usuario de login
    private final PasswordEncoder passwordEncoder; // Necesario para encriptar la contraseña

    // Inyección de dependencias en el constructor
    public TecnicoServiceImpl(TecnicoRepository repo, UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
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
        // 1. Guardamos los datos del técnico (Perfil)
        Tecnico nuevoTecnico = repo.save(tecnico);

        // 2. Creamos AUTOMÁTICAMENTE el usuario de acceso (Login)
        // Usamos el email como usuario y "123456" como contraseña por defecto
        if (usuarioRepo.findByUsername(tecnico.getEmail()).isEmpty()) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(tecnico.getEmail());
            nuevoUsuario.setPassword(passwordEncoder.encode("123456")); 
            nuevoUsuario.setRol(Rol.TECNICO);
            
            usuarioRepo.save(nuevoUsuario);
            System.out.println("--> LOGIN CREADO para: " + tecnico.getEmail() + " / Pass: 123456");
        }

        return nuevoTecnico;
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
        
        // Actualizamos foto si viene
        if (tecnico.getFoto() != null) {
             actual.setFoto(tecnico.getFoto());
        }

        return repo.save(actual);
    }

    @Override
    public void eliminar(Long id) {
        Tecnico actual = buscarPorId(id);
        repo.delete(actual);
    }
}