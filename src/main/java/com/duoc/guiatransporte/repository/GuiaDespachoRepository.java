package com.duoc.guiatransporte.repository;

import com.duoc.guiatransporte.model.GuiaDespacho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GuiaDespachoRepository extends JpaRepository<GuiaDespacho, Long> {

    Optional<GuiaDespacho> findByDespachoId(Long despachoId);
}