package com.reservas.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String icono; // puede ser un emoji o URL

    @ManyToMany(mappedBy = "caracteristicas")
    @JsonIgnore
    private Set<Producto> productos;


}
