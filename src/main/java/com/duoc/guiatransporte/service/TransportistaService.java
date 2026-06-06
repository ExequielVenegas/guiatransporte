package com.duoc.guiatransporte.service;

import com.duoc.guiatransporte.model.Transportista;
import com.duoc.guiatransporte.repository.TransportistaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransportistaService {

    private final TransportistaRepository transportistaRepository;

    public Transportista crear(Transportista transportista) {
        return transportistaRepository.save(transportista);
    }

    public List<Transportista> obtenerTodos() {
        return transportistaRepository.findAll();
    }

    public Transportista obtenerPorId(Long id) {
        return transportistaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transportista no encontrado: " + id));
    }

    public Transportista modificar(Long id, Transportista datos) {
        Transportista existente = obtenerPorId(id);
        existente.setNombre(datos.getNombre());
        existente.setRut(datos.getRut());
        existente.setTelefono(datos.getTelefono());
        existente.setEmail(datos.getEmail());
        return transportistaRepository.save(existente);
    }

    public void eliminar(Long id) {
        transportistaRepository.deleteById(id);
    }
}