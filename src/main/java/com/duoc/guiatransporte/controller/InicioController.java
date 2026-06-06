package com.duoc.guiatransporte.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/")
public class InicioController {

    @GetMapping
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of(
                "mensaje", "Bienvenido a Guia Transporte",
                "version", "1.0.0",
                "status", "online"
        ));
    }
}
