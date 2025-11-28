package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.dto.AuthRequest;
import com.Reparafacil.ReparafacilV2.dto.AuthResponse;
import com.Reparafacil.ReparafacilV2.model.Rol;
import com.Reparafacil.ReparafacilV2.model.Usuario;
import com.Reparafacil.ReparafacilV2.repository.UsuarioRepository;
import com.Reparafacil.ReparafacilV2.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder; // <-- Importante
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository; // <-- Agregado
    private final PasswordEncoder passwordEncoder;     // <-- Agregado

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    // --- NUEVO MÉTODO DE REGISTRO ---
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        // 1. Verificar si el usuario ya existe
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().build(); // O lanzar excepción personalizada
        }

        // 2. Crear el nuevo usuario
        Usuario nuevo = new Usuario();
        nuevo.setUsername(request.getUsername()); // Usaremos el email como username
        nuevo.setPassword(passwordEncoder.encode(request.getPassword()));
        nuevo.setRol(Rol.CLIENTE); // Por defecto es cliente

        usuarioRepository.save(nuevo);

        // 3. Generar token automáticamente para que quede logueado
        UserDetails userDetails = userDetailsService.loadUserByUsername(nuevo.getUsername());
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token));
    }
}