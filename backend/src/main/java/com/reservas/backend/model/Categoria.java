package com.reservas.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String descripcion;

    private String imagenUrl;

    // Constructor vacío
    public Categoria() {}

    // Constructor con campos (opcional)
    public Categoria(String titulo, String descripcion, String imagenUrl) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagenUrl = imagenUrl;
    }
}
