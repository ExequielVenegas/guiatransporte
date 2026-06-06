package com.duoc.guiatransporte.dto;

import com.duoc.guiatransporte.model.Transportista;
import lombok.Data;

@Data
public class TransportistaDTO {

    private Long id;
    private String nombre;
    private String rut;
    private String telefono;
    private String email;

    public TransportistaDTO(Transportista t) {
        this.id       = t.getId();
        this.nombre   = t.getNombre();
        this.rut      = t.getRut();
        this.telefono = t.getTelefono();
        this.email    = t.getEmail();
    }
}