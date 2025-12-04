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
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

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
        // 1. Guardar el perfil
        Tecnico nuevoTecnico = repo.save(tecnico);

        // 2. Crear Login si no existe
        if (usuarioRepo.findByUsername(tecnico.getEmail()).isEmpty()) {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setUsername(tecnico.getEmail());
            
            // Usar password del form o default "123456"
            String pass = (tecnico.getPassword() != null && !tecnico.getPassword().isBlank()) 
                          ? tecnico.getPassword() 
                          : "123456";
                          
            nuevoUsuario.setPassword(passwordEncoder.encode(pass)); 
            nuevoUsuario.setRol(Rol.TECNICO);
            
            usuarioRepo.save(nuevoUsuario);
        }

        return nuevoTecnico;
    }

    @Override
    public Tecnico actualizar(Long id, Tecnico tecnico) {
        Tecnico actual = buscarPorId(id);
        
        // Actualizar datos básicos
        actual.setNombre(tecnico.getNombre());
        actual.setApellido(tecnico.getApellido());
        
        // Si cambia el email, habría que actualizar el username del usuario también (lógica compleja omitida por seguridad básica)
        // Por ahora asumimos que el email se actualiza aquí:
        actual.setEmail(tecnico.getEmail());
        
        actual.setTelefono(tecnico.getTelefono());
        actual.setEspecialidad(tecnico.getEspecialidad());
        actual.setDisponible(tecnico.isDisponible());
        
        if (tecnico.getFoto() != null) {
             actual.setFoto(tecnico.getFoto());
        }

        repo.save(actual);

        // 3. Actualizar contraseña si viene en el JSON
        if (tecnico.getPassword() != null && !tecnico.getPassword().isBlank()) {
            usuarioRepo.findByUsername(actual.getEmail()).ifPresent(usuario -> {
                usuario.setPassword(passwordEncoder.encode(tecnico.getPassword()));
                usuarioRepo.save(usuario);
                System.out.println("--> Contraseña actualizada para técnico: " + actual.getEmail());
            });
        }

        return actual;
    }

    @Override
    public void eliminar(Long id) {
        Tecnico actual = buscarPorId(id);
        // Opcional: Eliminar también el usuario asociado si se desea
        // usuarioRepo.findByUsername(actual.getEmail()).ifPresent(usuarioRepo::delete);
        repo.delete(actual);
    }
}