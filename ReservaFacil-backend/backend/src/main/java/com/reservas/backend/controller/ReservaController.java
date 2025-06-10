package com.reservas.backend.controller;

import com.reservas.backend.model.Reserva;
import com.reservas.backend.repository.ReservaRepository;
import com.reservas.backend.service.ReservaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;


    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Reserva reserva) {
        if (reservaService.existeReservaSuperpuesta(
                reserva.getProducto().getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin()
        )) {
            return ResponseEntity.badRequest().body("Ya existe una reserva en esas fechas.");
        }

        Reserva guardada = reservaService.crearReserva(reserva);
        return ResponseEntity.ok(guardada);
    }


    @GetMapping("/producto/{id}")
    public List<Reserva> porProducto(@PathVariable Long id) {
        return reservaService.obtenerPorProducto(id);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerReservasDeUsuario(@PathVariable Long usuarioId) {
        return reservaService.obtenerPorUsuario(usuarioId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}

