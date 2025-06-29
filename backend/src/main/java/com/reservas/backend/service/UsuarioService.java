package com.reservas.backend.service;

import com.reservas.backend.model.Usuario;
import com.reservas.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
        return usuarioRepository.save(usuario);
    }
}
