package com.reservas.backend.controller;

import com.reservas.backend.model.Resena;
import com.reservas.backend.repository.ResenaRepository;
import com.reservas.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resenas")
@CrossOrigin(origins = "*")
public class ResenaController {

    @Autowired
    private ResenaRepository resenaRepo;

    @Autowired
    private ReservaRepository reservaRepo;

    @GetMapping("/{productoId}")
    public List<Resena> obtenerResenas(@PathVariable Long productoId) {
        return resenaRepo.findByProductoId(productoId);
    }

    @PostMapping
    public ResponseEntity<?> crearResena(@RequestBody Resena resena) {
        // Validar si el usuario hizo una reserva del producto
        boolean tieneReserva = reservaRepo.existsByProductoIdAndUsuarioId(
                resena.getProducto().getId(), resena.getUsuario().getId());

        if (!tieneReserva) {
            return ResponseEntity.status(403).body("❌ Solo podés comentar productos que reservaste.");
        }

        return ResponseEntity.ok(resenaRepo.save(resena));
    }
}
