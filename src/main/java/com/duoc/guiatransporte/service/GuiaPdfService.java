package com.duoc.guiatransporte.service;

import com.duoc.guiatransporte.model.Despacho;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class GuiaPdfService {

    @Value("${efs.path}")
    private String efsPath;

    public String generarPdfEnEfs(Despacho despacho) throws Exception {

        Files.createDirectories(Paths.get(efsPath));

        String nombreArchivo = getNombreArchivo(despacho.getId());
        String rutaCompleta = efsPath + "/" + nombreArchivo;

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(rutaCompleta));
        document.open();


        Font fTitulo = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        Font fSeccion = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        Font fNormal = new Font(Font.FontFamily.HELVETICA, 11);


        document.add(new Paragraph("GUÍA DE DESPACHO #" + despacho.getId(), fTitulo));
        document.add(new Paragraph("Fecha emisión: " + despacho.getFechaEmision(), fNormal));
        document.add(new Paragraph("Estado: " + despacho.getEstado(), fNormal));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("TRANSPORTISTA", fSeccion));
        document.add(new Paragraph("Nombre: " + despacho.getTransportista().getNombre(), fNormal));
        document.add(new Paragraph("RUT: " + despacho.getTransportista().getRut(), fNormal));
        document.add(new Paragraph("Teléfono: " + despacho.getTransportista().getTelefono(), fNormal));
        document.add(new Paragraph("Email: " + despacho.getTransportista().getEmail(), fNormal));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("ORIGEN", fSeccion));
        document.add(new Paragraph("Dirección: " + despacho.getDireccionOrigen(), fNormal));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("DESTINO", fSeccion));
        document.add(new Paragraph("Destinatario: " + despacho.getDestinatario(), fNormal));
        document.add(new Paragraph("Dirección: " + despacho.getDireccionDestino(), fNormal));
        document.add(new Paragraph(" "));

        document.add(new Paragraph("DETALLE CARGA", fSeccion));
        document.add(new Paragraph("Descripción: " + despacho.getDescripcionCarga(), fNormal));
        document.add(new Paragraph("Cantidad: " + despacho.getCantidad(), fNormal));
        document.add(new Paragraph("Peso: " + despacho.getPeso() + " kg", fNormal));

        document.close();

        return rutaCompleta;
    }

    public String getNombreArchivo(Long despachoId) {
        return "guia_" + despachoId + ".pdf";
    }
}