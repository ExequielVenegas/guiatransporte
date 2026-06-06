package com.duoc.guiatransporte.controller;

import com.duoc.guiatransporte.model.Transportista;
import com.duoc.guiatransporte.service.TransportistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transportistas")
@RequiredArgsConstructor
public class TransportistaController {

    private final TransportistaService transportistaService;

    @PostMapping
    public ResponseEntity<Transportista> crear(@RequestBody Transportista transportista) {
        return ResponseEntity.ok(transportistaService.crear(transportista));
    }

    @GetMapping
    public ResponseEntity<List<Transportista>> obtenerTodos() {
        return ResponseEntity.ok(transportistaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportista> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(transportistaService.obtenerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transportista> modificar(
            @PathVariable Long id,
            @RequestBody Transportista datos) {
        try {
            return ResponseEntity.ok(transportistaService.modificar(id, datos));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            transportistaService.eliminar(id);
            return ResponseEntity.ok("Transportista " + id + " eliminado.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}