package com.reservas.backend.controller;

import com.reservas.backend.model.Reserva;
import com.reservas.backend.model.Usuario;
import com.reservas.backend.repository.ReservaRepository;
import com.reservas.backend.repository.UsuarioRepository;
import com.reservas.backend.service.EmailService;
import com.reservas.backend.service.ReservaService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.ErrorManager;

@RestController
@RequestMapping("/api/reservas")
@CrossOrigin(origins = "*")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    /* ───────────────────────────────
     * 1. Crear una reserva + e‑mail
     * ─────────────────────────────── */
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Reserva reserva) throws MessagingException {

        Reserva guardada = reservaService.crearReserva(reserva);

        try {
            String html = emailService.buildHtmlReserva(guardada);
            emailService.enviarEmailHtml(
                    guardada.getUsuario().getEmail(),   // puede ser null
                    "Confirmación de reserva #" + guardada.getId(),
                    html);
        } catch (Exception e) {
            //Log log = null;
            //log.error("No se pudo enviar mail de confirmación", e);
        }
        // devolvemos la reserva igualmente
        return ResponseEntity.ok(guardada);

    }

    /* ───────────────────────────────
     * 2. Listar reservas por producto
     * ─────────────────────────────── */
    @GetMapping("/producto/{id}")
    public List<Reserva> porProducto(@PathVariable Long id) {
        return reservaService.obtenerPorProducto(id);
    }

    /* ───────────────────────────────
     * 3. Listar reservas por usuario
     * ─────────────────────────────── */
    @GetMapping("/usuario/{usuarioId}")
    public List<Reserva> obtenerReservasDeUsuario(@PathVariable Long usuarioId) {
        return reservaService.obtenerPorUsuario(usuarioId);
    }

    /* ───────────────────────────────
     * 4. Cancelar reserva
     * ─────────────────────────────── */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarReserva(@PathVariable Long id) {
        if (!reservaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        reservaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}


