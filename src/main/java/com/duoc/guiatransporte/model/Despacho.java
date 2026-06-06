package com.duoc.guiatransporte.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "despachos")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fechaEmision;
    private String estado;

    private String descripcionCarga;
    private Integer cantidad;
    private Double peso;

    private String direccionOrigen;
    private String destinatario;
    private String direccionDestino;

    @ManyToOne
    @JoinColumn(name = "transportista_id")
    private Transportista transportista;

    @OneToOne(mappedBy = "despacho", cascade = CascadeType.ALL)
    private GuiaDespacho guia;
}