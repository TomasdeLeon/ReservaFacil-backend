package com.reservas.backend.repository;

import com.reservas.backend.model.Resena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResenaRepository extends JpaRepository<Resena, Long> {
    List<Resena> findByProductoId(Long productoId);
    boolean existsByProductoIdAndUsuarioId(Long productoId, Long usuarioId);
}
