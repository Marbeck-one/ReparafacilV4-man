package com.Reparafacil.ReparafacilV2.controller;

import com.Reparafacil.ReparafacilV2.model.Garantia;
import com.Reparafacil.ReparafacilV2.service.GarantiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/garantias")
public class GarantiaController {

    private final GarantiaService service;

    public GarantiaController(GarantiaService service) {
        this.service = service;
    }

    @Operation(summary = "Listar todas las garantías")
    @GetMapping
    public List<Garantia> listar() {
        return service.listar();
    }

    @Operation(summary = "Obtener garantía por ID")
    @ApiResponse(responseCode = "404", description = "Garantía no encontrada")
    @GetMapping("/{id}")
    public Garantia obtener(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @Operation(summary = "Crear una nueva garantía (asociada a un servicio)")
    @ApiResponse(responseCode = "201", description = "Garantía creada")
    @PostMapping
    public ResponseEntity<Garantia> crear(@Valid @RequestBody Garantia garantia) {
        Garantia creado = service.crear(garantia);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar una garantía existente")
    @PutMapping("/{id}")
    public Garantia actualizar(@PathVariable Long id, @Valid @RequestBody Garantia garantia) {
        return service.actualizar(id, garantia);
    }

    @Operation(summary = "Eliminar una garantía")
    @ApiResponse(responseCode = "204", description = "Garantía eliminada")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}