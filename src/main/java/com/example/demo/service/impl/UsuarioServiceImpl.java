package com.example.demo.service.impl;

import com.example.demo.model.sesion.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAll() {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Integer id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @Override
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario) {
        Usuario aux = usuarioRepository.findById(id).orElse(null);
        if (aux == null) return null;

        aux.setNombre(usuario.getNombre());
        aux.setTelefono(usuario.getTelefono());
        aux.setContrasena(usuario.getContrasena());
        aux.setRol(usuario.getRol());

        return usuarioRepository.save(aux);
    }

    // ✅ IMPLEMENTACIÓN CLAVE
    @Override
    public boolean telefonoExiste(String telefono) {
        return usuarioRepository.existsByTelefono(telefono);
    }
}
