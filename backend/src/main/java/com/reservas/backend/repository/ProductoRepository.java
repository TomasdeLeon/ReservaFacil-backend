package com.reservas.backend.repository;

import com.reservas.backend.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    boolean existsByNombre(String nombre);

    List<Producto> findByCategoriaId(Long categoriaId);

    List<Producto> findByNombreOrDescripcion(String nombre, String descripcion);

    List<Producto> findByNombreContainingIgnoreCaseOrDescripcionContainingIgnoreCase(String nombre, String descripcion);

}

