package com.duoc.guiatransporte.repository;

import com.duoc.guiatransporte.model.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransportistaRepository extends JpaRepository<Transportista, Long> {

    Optional<Transportista> findByNombre(String nombre);

    Optional<Transportista> findByRut(String rut);
}