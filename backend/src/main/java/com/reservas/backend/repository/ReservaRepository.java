package com.reservas.backend.repository;

import com.reservas.backend.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    List<Reserva> findByProductoId(Long productoId);

    @Query("SELECT r FROM Reserva r WHERE r.producto.id = :productoId AND " +
            "(:fin >= r.fechaInicio AND :inicio <= r.fechaFin)")
    List<Reserva> buscarReservasSuperpuestas(
            @Param("productoId") Long productoId,
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin
    );

    boolean existsByProductoIdAndFechaInicioLessThanEqualAndFechaFinGreaterThanEqual(
            Long productoId, LocalDate fechaFin, LocalDate fechaInicio
    );

    List<Reserva> findByUsuarioId(Long usuarioId);

    boolean existsByProductoIdAndUsuarioId(Long productoId, Long usuarioId);

}
