package com.duoc.guiatransporte.service;


import com.duoc.guiatransporte.model.Despacho;
import com.duoc.guiatransporte.model.GuiaDespacho;
import com.duoc.guiatransporte.repository.DespachoRepository;
import com.duoc.guiatransporte.repository.GuiaDespachoRepository;
import com.duoc.guiatransporte.repository.TransportistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DespachoService {

    private final DespachoRepository despachoRepository;
    private final GuiaDespachoRepository guiaDespachoRepository;
    private final TransportistaRepository transportistaRepository;
    private final GuiaPdfService guiaPdfService;
    private final S3Service s3Service;

    public Despacho crear(Despacho despacho) throws Exception {

        transportistaRepository.findById(despacho.getTransportista().getId())
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado"));

        Despacho guardado = despachoRepository.save(despacho);

        String rutaEfs = guiaPdfService.generarPdfEnEfs(guardado);

        String nombreArchivo = guiaPdfService.getNombreArchivo(guardado.getId());
        String nombreTransportista = guardado.getTransportista().getNombre();
        String claveS3 = s3Service.subirDesdeEfs(
                rutaEfs,
                guardado.getFechaEmision(),
                nombreTransportista,
                nombreArchivo
        );

        GuiaDespacho guia = new GuiaDespacho();
        guia.setDespacho(guardado);
        guia.setClaveS3(claveS3);
        guiaDespachoRepository.save(guia);

        return despachoRepository.findById(guardado.getId()).get();
    }

    public byte[] descargarGuia(Long despachoId) {
        GuiaDespacho guia = guiaDespachoRepository.findByDespachoId(despachoId)
                .orElseThrow(() -> new RuntimeException("Guía no encontrada para despacho: " + despachoId));

        return s3Service.descargarArchivo(guia.getClaveS3());
    }

    public Despacho modificar(Long id, Despacho datos) throws Exception {
        Despacho existente = despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado: " + id));

        existente.setFechaEmision(datos.getFechaEmision());
        existente.setEstado(datos.getEstado());
        existente.setDescripcionCarga(datos.getDescripcionCarga());
        existente.setCantidad(datos.getCantidad());
        existente.setPeso(datos.getPeso());
        existente.setDireccionOrigen(datos.getDireccionOrigen());
        existente.setDestinatario(datos.getDestinatario());
        existente.setDireccionDestino(datos.getDireccionDestino());
        despachoRepository.save(existente);

        String rutaEfs = guiaPdfService.generarPdfEnEfs(existente);

        String nombreArchivo = guiaPdfService.getNombreArchivo(id);
        String nombreTransportista = existente.getTransportista().getNombre();
        String claveS3 = s3Service.reemplazarDesdeEfs(
                rutaEfs,
                existente.getFechaEmision(),
                nombreTransportista,
                nombreArchivo
        );

        GuiaDespacho guia = guiaDespachoRepository.findByDespachoId(id)
                .orElseThrow(() -> new RuntimeException("Guía no encontrada para despacho: " + id));
        guia.setClaveS3(claveS3);
        guiaDespachoRepository.save(guia);

        return existente;
    }

    public void eliminar(Long id) {
        Despacho despacho = despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado: " + id));

        guiaDespachoRepository.findByDespachoId(id).ifPresent(guia -> {
            s3Service.eliminarArchivo(guia.getClaveS3());
        });

        despachoRepository.deleteById(id);
    }

    public List<String> consultarPorFechaYTransportista(String fecha, String transportista) {
        return s3Service.listarPorFechaYTransportista(fecha, transportista);
    }

    public Despacho obtenerPorId(Long id) {
        return despachoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Despacho no encontrado: " + id));
    }

    public List<Despacho> obtenerTodos() {
        return despachoRepository.findAll();
    }
}