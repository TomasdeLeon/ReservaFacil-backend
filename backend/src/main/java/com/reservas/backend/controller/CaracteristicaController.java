package com.reservas.backend.controller;

import com.reservas.backend.model.Caracteristica;
import com.reservas.backend.repository.CaracteristicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/caracteristicas")
@CrossOrigin(origins = "*")
public class CaracteristicaController {

    @Autowired
    private CaracteristicaRepository repo;

    @GetMapping
    public List<Caracteristica> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Caracteristica crear(@RequestBody Caracteristica c) {
        return repo.save(c);
    }
}


