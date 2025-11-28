package com.Reparafacil.ReparafacilV2.security;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import com.Reparafacil.ReparafacilV2.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
@Service @RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;
    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        return User.builder().username(usuario.getUsername()).password(usuario.getPassword()).roles(usuario.getRol().name()).build();
    }
}
