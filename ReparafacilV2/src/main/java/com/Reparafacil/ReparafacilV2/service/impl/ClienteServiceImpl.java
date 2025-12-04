package com.Reparafacil.ReparafacilV2.service.impl;

import com.Reparafacil.ReparafacilV2.exception.NotFoundException;
import com.Reparafacil.ReparafacilV2.model.Cliente;
import com.Reparafacil.ReparafacilV2.model.Rol;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import com.Reparafacil.ReparafacilV2.repository.ClienteRepository;
import com.Reparafacil.ReparafacilV2.repository.UsuarioRepository;
import com.Reparafacil.ReparafacilV2.service.ClienteService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;
    private final UsuarioRepository usuarioRepo; // Agregado
    private final PasswordEncoder passwordEncoder; // Agregado

    // Inyección completa
    public ClienteServiceImpl(ClienteRepository repo, UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> listar() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente buscarPorId(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Cliente no encontrado: " + id));
    }

    @Override
    public Cliente crear(Cliente cliente) {
        // 1. Crear perfil cliente
        Cliente nuevo = repo.save(cliente);

        // 2. Crear usuario de login automáticamente
        if (usuarioRepo.findByUsername(cliente.getEmail()).isEmpty()) {
            Usuario u = new Usuario();
            u.setUsername(cliente.getEmail());
            
            // Password del form o default
            String pass = (cliente.getPassword() != null && !cliente.getPassword().isBlank()) 
                          ? cliente.getPassword() 
                          : "123456";
                          
            u.setPassword(passwordEncoder.encode(pass));
            u.setRol(Rol.CLIENTE);
            usuarioRepo.save(u);
        }
        return nuevo;
    }

    @Override
    public Cliente actualizar(Long id, Cliente cliente) {
        Cliente actual = buscarPorId(id);
        actual.setNombre(cliente.getNombre());
        actual.setApellido(cliente.getApellido());
        actual.setEmail(cliente.getEmail()); 
        actual.setTelefono(cliente.getTelefono());
        actual.setDireccion(cliente.getDireccion());
        
        repo.save(actual);

        // 3. Actualizar contraseña si se solicita
        if (cliente.getPassword() != null && !cliente.getPassword().isBlank()) {
            usuarioRepo.findByUsername(actual.getEmail()).ifPresent(u -> {
                u.setPassword(passwordEncoder.encode(cliente.getPassword()));
                usuarioRepo.save(u);
                System.out.println("--> Contraseña actualizada para cliente: " + actual.getEmail());
            });
        }

        return actual;
    }

    @Override
    public void eliminar(Long id) {
        Cliente actual = buscarPorId(id);
        repo.delete(actual);
    }
}