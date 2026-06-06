package com.duoc.guiatransporte.controller;

import com.duoc.guiatransporte.dto.TransportistaDTO;
import com.duoc.guiatransporte.model.Transportista;
import com.duoc.guiatransporte.service.TransportistaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transportistas")
@RequiredArgsConstructor
public class TransportistaController {

    private final TransportistaService transportistaService;

    @PostMapping
    public ResponseEntity<TransportistaDTO> crear(@RequestBody Transportista transportista) {
        Transportista creado = transportistaService.crear(transportista);
        return ResponseEntity.ok(new TransportistaDTO(creado));
    }

    @GetMapping
    public ResponseEntity<List<TransportistaDTO>> obtenerTodos() {
        List<TransportistaDTO> lista = transportistaService.obtenerTodos()
                .stream()
                .map(TransportistaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportistaDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new TransportistaDTO(transportistaService.obtenerPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransportistaDTO> modificar(
            @PathVariable Long id,
            @RequestBody Transportista datos) {
        try {
            return ResponseEntity.ok(new TransportistaDTO(transportistaService.modificar(id, datos)));
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