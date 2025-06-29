package com.reservas.backend.controller;

import com.reservas.backend.model.Producto;
import com.reservas.backend.model.Usuario;
import com.reservas.backend.repository.UsuarioRepository;
import com.reservas.backend.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService service;

    @Autowired
    private UsuarioRepository usuarioRepository;


    public ProductoController(ProductoService service) {
        this.service = service;
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto, @RequestHeader("X-User-Email") String emailUsuario) {
        Usuario usuario = usuarioRepository.findByEmail(emailUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        producto.setUsuario(usuario);
        return service.crearProducto(producto);
    }


    @GetMapping
    public List<Producto> listarProductos() {
        return service.listarProductos();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        if (!service.eliminarProducto(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // ✅ Este método permite buscar un producto por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Producto producto = service.buscarProductoPorId(id);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/categoria/{categoriaId}")
    public List<Producto> productosPorCategoria(@PathVariable Long categoriaId) {
        return service.obtenerPorCategoria(categoriaId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Producto> editar(@PathVariable Long id, @RequestBody Producto productoEditado) {
        Producto actualizado = service.editarProducto(id, productoEditado);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/buscar")
    public List<Producto> buscarProductos(@RequestParam String texto) {
        return service.buscarPorTexto(texto);
    }

    @GetMapping("/disponibles")
    public List<Producto> buscarDisponibles(
            @RequestParam(required = false) String texto,
            @RequestParam(required = false) String desde,
            @RequestParam(required = false) String hasta,
            @RequestParam(required = false) Long categoriaId) {

        return service.buscarDisponibles(texto, desde, hasta, categoriaId);
    }

}

