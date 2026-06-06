package com.duoc.guiatransporte.dto;

import com.duoc.guiatransporte.model.Despacho;
import lombok.Data;

@Data
public class DespachoDTO {

    private Long id;
    private String fechaEmision;
    private String estado;
    private String descripcionCarga;
    private Integer cantidad;
    private Double peso;
    private String direccionOrigen;
    private String destinatario;
    private String direccionDestino;

    private Long transportistaId;
    private String transportistaNombre;

    private String claveS3;

    public DespachoDTO(Despacho d) {
        this.id                  = d.getId();
        this.fechaEmision        = d.getFechaEmision();
        this.estado              = d.getEstado();
        this.descripcionCarga    = d.getDescripcionCarga();
        this.cantidad            = d.getCantidad();
        this.peso                = d.getPeso();
        this.direccionOrigen     = d.getDireccionOrigen();
        this.destinatario        = d.getDestinatario();
        this.direccionDestino    = d.getDireccionDestino();

        if (d.getTransportista() != null) {
            this.transportistaId     = d.getTransportista().getId();
            this.transportistaNombre = d.getTransportista().getNombre();
        }

        if (d.getGuia() != null) {
            this.claveS3 = d.getGuia().getClaveS3();
        }
    }
}