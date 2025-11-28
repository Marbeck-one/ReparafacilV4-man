package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Cliente;
import com.Reparafacil.ReparafacilV2.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los clientes")
    @GetMapping
    public List<Cliente> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener cliente por ID")
    @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    @GetMapping("/{id}")
    public Cliente obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado")
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente) {
        Cliente creado = service.crear(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un cliente existente")
    @PutMapping("/{id}")
    public Cliente actualizar(@PathVariable Long id, @Valid @RequestBody Cliente cliente) {
        return service.actualizar(id, cliente);
    }

    @Operation(summary = "Eliminar un cliente")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}