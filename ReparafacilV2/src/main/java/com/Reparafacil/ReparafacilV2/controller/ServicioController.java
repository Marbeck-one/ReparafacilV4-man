package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Servicio;
import com.Reparafacil.ReparafacilV2.service.ServicioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService service;

    public ServicioController(ServicioService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los servicios")
    @GetMapping
    public List<Servicio> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener servicio por ID")
    @ApiResponse(responseCode = "404", description = "Servicio no encontrado")
    @GetMapping("/{id}")
    public Servicio obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Crear un nuevo servicio (solicitud)")
    @ApiResponse(responseCode = "201", description = "Servicio creado")
    @PostMapping
    public ResponseEntity<Servicio> crear(@Valid @RequestBody Servicio servicio) {
        // En un request real, esto solo traería la descripción
        // y el ID del cliente.
        Servicio creado = service.crear(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un servicio existente")
    @PutMapping("/{id}")
    public Servicio actualizar(@PathVariable Long id, @Valid @RequestBody Servicio servicio) {
        return service.actualizar(id, servicio);
    }

    @Operation(summary = "Eliminar un servicio")
    @ApiResponse(responseCode = "204", description = "Servicio eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}