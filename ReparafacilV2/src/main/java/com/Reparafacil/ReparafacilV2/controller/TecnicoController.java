package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Tecnico;
import com.Reparafacil.ReparafacilV2.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
public class TecnicoController {

    private final TecnicoService service;

    public TecnicoController(TecnicoService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los técnicos")
    @GetMapping
    public List<Tecnico> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener técnico por ID")
    @ApiResponse(responseCode = "404", description = "Técnico no encontrado")
    @GetMapping("/{id}")
    public Tecnico obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Crear un nuevo técnico")
    @ApiResponse(responseCode = "201", description = "Técnico creado")
    @PostMapping
    public ResponseEntity<Tecnico> crear(@Valid @RequestBody Tecnico tecnico) {
        Tecnico creado = service.crear(tecnico);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un técnico existente")
    @PutMapping("/{id}")
    public Tecnico actualizar(@PathVariable Long id, @Valid @RequestBody Tecnico tecnico) {
        return service.actualizar(id, tecnico);
    }

    @Operation(summary = "Eliminar un técnico")
    @ApiResponse(responseCode = "204", description = "Técnico eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}