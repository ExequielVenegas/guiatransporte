package com.duoc.guiatransporte.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "guias_despacho")
public class GuiaDespacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "despacho_id")
    private Despacho despacho;

    // Clave donde quedó el PDF en S3
    // Ej: 20241201/TransportesXYZ/guia_1.pdf
    private String claveS3;

    private LocalDateTime fechaCreacion;

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    }
}