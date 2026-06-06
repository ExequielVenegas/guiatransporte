package com.duoc.guiatransporte.repository;

import com.duoc.guiatransporte.model.Despacho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DespachoRepository extends JpaRepository<Despacho, Long> {

    List<Despacho> findByTransportistaIdAndFechaEmision(Long transportistaId, String fechaEmision);

    List<Despacho> findByTransportistaId(Long transportistaId);

    List<Despacho> findByEstado(String estado);
}