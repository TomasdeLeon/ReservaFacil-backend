package com.reservas.backend.controller;

import com.reservas.backend.model.Favorito;
import com.reservas.backend.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favoritos")
@CrossOrigin(origins = "*")
public class FavoritoController {

    @Autowired
    private FavoritoRepository favoritoRepo;

    @PostMapping
    public ResponseEntity<?> agregarFavorito(@RequestBody Favorito favorito) {
        if (favorito.getUsuario() == null || favorito.getUsuario().getId() == null ||
                favorito.getProducto() == null || favorito.getProducto().getId() == null) {
            return ResponseEntity.badRequest().body("⚠️ Usuario o producto no pueden ser nulos");
        }

        boolean existe = favoritoRepo.existsByUsuarioIdAndProductoId(
                favorito.getUsuario().getId(), favorito.getProducto().getId());

        if (existe) {
            return ResponseEntity.badRequest().body("Ya está marcado como favorito");
        }

        return ResponseEntity.ok(favoritoRepo.save(favorito));
    }



    @DeleteMapping
    public ResponseEntity<?> eliminarFavorito(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        Optional<Favorito> favorito = favoritoRepo.findByUsuarioIdAndProductoId(usuarioId, productoId);

        if (favorito.isPresent()) {
            favoritoRepo.delete(favorito.get());
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(404).body("Favorito no encontrado");
        }
    }

    @GetMapping("/{usuarioId}")
    public List<Favorito> listarFavoritos(@PathVariable Long usuarioId) {
        return favoritoRepo.findByUsuarioId(usuarioId);
    }

    @GetMapping("/existe")
    public boolean existeFavorito(@RequestParam Long usuarioId, @RequestParam Long productoId) {
        return favoritoRepo.existsByUsuarioIdAndProductoId(usuarioId, productoId);
    }
}

