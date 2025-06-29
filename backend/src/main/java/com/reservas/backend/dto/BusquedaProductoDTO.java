package com.reservas.backend.dto;

import lombok.Data;

@Data
public class BusquedaProductoDTO {
    private String texto;
    private String fechaInicio;
    private String fechaFin;
}
