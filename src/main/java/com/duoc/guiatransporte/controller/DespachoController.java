package com.duoc.guiatransporte.controller;

import com.duoc.guiatransporte.model.Despacho;
import com.duoc.guiatransporte.service.DespachoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/despachos")
@RequiredArgsConstructor
public class DespachoController {

    private final DespachoService despachoService;

    @PostMapping
    public ResponseEntity<Despacho> crear(@RequestBody Despacho despacho) {
        try {
            return ResponseEntity.ok(despachoService.crear(despacho));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Despacho>> obtenerTodos() {
        return ResponseEntity.ok(despachoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Despacho> obtenerPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(despachoService.obtenerPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @GetMapping("/{id}/guia/descargar")
    public ResponseEntity<byte[]> descargarGuia(@PathVariable Long id) {
        try {
            byte[] pdf = despachoService.descargarGuia(id);
            String nombreArchivo = "guia_" + id + ".pdf";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + nombreArchivo + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Despacho> modificar(
            @PathVariable Long id,
            @RequestBody Despacho datos) {
        try {
            return ResponseEntity.ok(despachoService.modificar(id, datos));
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