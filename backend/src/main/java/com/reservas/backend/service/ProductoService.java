package com.reservas.backend.service;

import com.reservas.backend.model.Producto;
import com.reservas.backend.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    @Autowired
    private ReservaService reservaService;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public Producto crearProducto(Producto producto) {
        if (repository.existsByNombre(producto.getNombre())) {
            throw new IllegalArgumentException("El nombre ya está en uso");
        }
        return repository.save(producto);
    }

    public List<Producto> listarProductos() {
        return repository.findAll().stream()
                .filter(p -> p.getNombre() != null && !p.getNombre().isBlank())
                .filter(p -> p.getDescripcion() != null && !p.getDescripcion().isBlank())
                .collect(Collectors.toList());
    }


    public boolean eliminarProducto(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    public Producto buscarProductoPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public List<Producto> obtenerPorCategoria(Long categoriaId) {
        return repository.findByCategoriaId(categoriaId);
    }

    public Producto editarProducto(Long id, Producto nuevoProducto) {
        Producto existente = repository.findById(id).orElseThrow();
        existente.setNombre(nuevoProducto.getNombre());
        existente.setDescripcion(nuevoProducto.getDescripcion());
        existente.setImagenUrl(nuevoProducto.getImagenUrl());
        existente.setCategoria(nuevoProducto.getCategoria());
        return repository.save(existente);
    }

    public List<Producto> buscarPorTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            return repository.findAll();
        }
        return repository.findByNombreOrDescripcion(texto, texto);
    }

    public List<Producto> buscarDisponibles(String texto, String desde, String hasta, Long categoriaId) {
        List<Producto> productos = repository.findAll();

        if (texto != null && !texto.trim().isEmpty()) {
            productos = productos.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(texto.toLowerCase()) ||
                            p.getDescripcion().toLowerCase().contains(texto.toLowerCase()))
                    .collect(Collectors.toList());
        }

        if (categoriaId != null) {
            productos = productos.stream()
                    .filter(p -> p.getCategoria().getId().equals(categoriaId))
                    .collect(Collectors.toList());
        }

        if (desde != null && hasta != null) {
            LocalDate fDesde = LocalDate.parse(desde);
            LocalDate fHasta = LocalDate.parse(hasta);

            productos = productos.stream()
                    .filter(p -> p.getReservas() == null || p.getReservas().stream().noneMatch(r ->
                            !(r.getFechaFin().isBefore(fDesde) || r.getFechaInicio().isAfter(fHasta))
                    ))
                    .collect(Collectors.toList());
        }

        return productos;
    }

}

