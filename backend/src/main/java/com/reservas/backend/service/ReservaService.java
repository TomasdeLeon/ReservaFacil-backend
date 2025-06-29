package com.reservas.backend.service;

import com.reservas.backend.model.Reserva;
import com.reservas.backend.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    public Reserva crearReserva(Reserva reserva) {
        List<Reserva> superpuestas = reservaRepository.buscarReservasSuperpuestas(
                reserva.getProducto().getId(),
                reserva.getFechaInicio(),
                reserva.getFechaFin()
        );

        if (!superpuestas.isEmpty()) {
            throw new RuntimeException("El producto ya está reservado en esas fechas.");
        }

        return reservaRepository.save(reserva);
    }

    public List<Reserva> obtenerPorProducto(Long productoId) {
        return reservaRepository.findByProductoId(productoId);
    }

    public boolean existeReservaSuperpuesta(Long productoId, LocalDate desde, LocalDate hasta) {
        return reservaRepository.existsByProductoIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
                productoId, hasta, desde
        );
    }

    public List<Reserva> obtenerPorUsuario(Long usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId)
                .stream()
                .filter(r -> r.getProducto() != null)
                .collect(Collectors.toList());
    }

}
