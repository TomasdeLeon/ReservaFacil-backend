package com.reservas.backend.repository;

import com.reservas.backend.model.Favorito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioId(Long usuarioId);

    boolean existsByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

    Optional<Favorito> findByUsuarioIdAndProductoId(Long usuarioId, Long productoId);

}

