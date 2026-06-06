package com.duoc.guiatransporte.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "transportistas")
public class Transportista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String rut;
    private String telefono;
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "transportista", cascade = CascadeType.ALL)
    private List<Despacho> despachos;
}