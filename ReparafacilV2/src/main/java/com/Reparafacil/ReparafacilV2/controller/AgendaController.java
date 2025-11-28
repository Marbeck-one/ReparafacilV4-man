package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Agenda;
import com.Reparafacil.ReparafacilV2.service.AgendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agendas")
public class AgendaController {

    private final AgendaService service;

    public AgendaController(AgendaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todos los slots de agenda")
    @GetMapping
    public List<Agenda> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener slot de agenda por ID")
    @ApiResponse(responseCode = "404", description = "Slot no encontrado")
    @GetMapping("/{id}")
    public Agenda obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Crear un nuevo slot de agenda (ej. disponibilidad técnico)")
    @ApiResponse(responseCode = "201", description = "Slot creado")
    @PostMapping
    public ResponseEntity<Agenda> crear(@Valid @RequestBody Agenda agenda) {
        Agenda creado = service.crear(agenda);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un slot de agenda (ej. reservar)")
    @PutMapping("/{id}")
    public Agenda actualizar(@PathVariable Long id, @Valid @RequestBody Agenda agenda) {
        return service.actualizar(id, agenda);
    }

    @Operation(summary = "Eliminar un slot de agenda")
    @ApiResponse(responseCode = "204", description = "Slot eliminado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}