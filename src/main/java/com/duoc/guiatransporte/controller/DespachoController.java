package com.duoc.guiatransporte.controller;

import com.duoc.guiatransporte.dto.DespachoDTO;
import com.duoc.guiatransporte.model.Despacho;
import com.duoc.guiatransporte.service.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/despachos")
@RequiredArgsConstructor
public class DespachoController {

    private final DespachoService despachoService;

    @PostMapping
    public ResponseEntity<DespachoDTO> crear(@RequestBody Despacho despacho) {
        try {
            return ResponseEntity.ok(new DespachoDTO(despachoService.crear(despacho)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<DespachoDTO>> obtenerTodos() {
        List<DespachoDTO> lista = despachoService.obtenerTodos()
                .stream()
                .map(DespachoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DespachoDTO> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(new DespachoDTO(despachoService.obtenerPorId(id)));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/{id}/guia/descargar")
    public ResponseEntity<byte[]> descargarGuia(@PathVariable Long id) {
        try {
            byte[] pdf = despachoService.descargarGuia(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"guia_" + id + ".pdf\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DespachoDTO> modificar(
            @PathVariable Long id,
            @RequestBody Despacho datos) {
        try {
            return ResponseEntity.ok(new DespachoDTO(despachoService.modificar(id, datos)));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {
        try {
            despachoService.eliminar(id);
            return ResponseEntity.ok("Despacho " + id + " eliminado de S3 y BD.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/consultar")
    public ResponseEntity<List<String>> consultar(
            @RequestParam String fecha,
            @RequestParam String transportista) {
        try {
            return ResponseEntity.ok(
                    despachoService.consultarPorFechaYTransportista(fecha, transportista)
            );
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}