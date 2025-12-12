package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Mensaje;
import com.Reparafacil.ReparafacilV2.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mensajes")
@CrossOrigin(origins = "*") // Para evitar problemas con el front
public class MensajeController {

    @Autowired
    private MensajeService mensajeService;

    // GET: Obtener chat con un usuario específico
    @GetMapping("/chat/{otroUsuario}")
    public ResponseEntity<List<Mensaje>> verChat(@PathVariable String otroUsuario) {
        // Obtenemos el usuario logueado desde el Token
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String miUsuario = auth.getName(); // El email/username del token

        return ResponseEntity.ok(mensajeService.obtenerConversacion(miUsuario, otroUsuario));
    }

    // POST: Enviar mensaje
    @PostMapping
    public ResponseEntity<Mensaje> enviar(@RequestBody Map<String, String> body) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String miUsuario = auth.getName();

        String para = body.get("destinatario");
        String texto = body.get("contenido");

        Mensaje nuevo = mensajeService.enviarMensaje(miUsuario, para, texto);
        return ResponseEntity.ok(nuevo);
    }
}