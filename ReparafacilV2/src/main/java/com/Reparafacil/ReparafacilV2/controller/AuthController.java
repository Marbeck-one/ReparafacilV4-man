package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.dto.AuthRequest;
import com.Reparafacil.ReparafacilV2.dto.AuthResponse;
import com.Reparafacil.ReparafacilV2.model.Cliente; // <--- Importar Cliente
import com.Reparafacil.ReparafacilV2.model.Rol;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import com.Reparafacil.ReparafacilV2.repository.ClienteRepository; // <--- Importar Repo
import com.Reparafacil.ReparafacilV2.repository.UsuarioRepository;
import com.Reparafacil.ReparafacilV2.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final ClienteRepository clienteRepository; // <--- 1. Inyectar esto
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        // 1. Verificar si el usuario ya existe
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // 2. Crear el nuevo USUARIO (Login)
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(request.getUsername());
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevoUsuario.setRol(Rol.CLIENTE);
        
        usuarioRepository.save(nuevoUsuario);

        // 3. Crear el perfil de CLIENTE (Datos de negocio)
        // Como el DTO de registro es simple, llenamos el resto con datos temporales
        // para que el usuario los edite después en su perfil.
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setEmail(request.getUsername()); // El enlace clave
        nuevoCliente.setNombre("Usuario");            // Placeholder
        nuevoCliente.setApellido("Nuevo");            // Placeholder
        nuevoCliente.setTelefono("Sin registrar");    // Placeholder para cumplir @NotBlank
        nuevoCliente.setDireccion("Sin registrar");   // Placeholder para cumplir @NotBlank
        
        clienteRepository.save(nuevoCliente);

        // 4. Generar token automáticamente
        UserDetails userDetails = userDetailsService.loadUserByUsername(nuevoUsuario.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}