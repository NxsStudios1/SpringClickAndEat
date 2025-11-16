package com.example.demo.service.impl;

import com.example.demo.model.sesion.Usuario;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class UsuarioServiceImpl implements UsuarioService
{
    private final UsuarioRepository usuarioRepository;

    @Override
    public List<Usuario> getAll()
    {
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario getById(Integer id)
    {
        return usuarioRepository.findById(id).orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario)
    {
        return usuarioRepository.save( usuario );
    }

    @Override
    public void delete(Integer id)
    {
        usuarioRepository.deleteById(id);
    }

    @Override
    public Usuario update(Integer id, Usuario usuario)
    {
        Usuario aux = usuarioRepository.getById( id );
        usuario.setNombre( usuario.getNombre() );
        usuario.setTelefono( usuario.getTelefono() );
        usuario.setContrasena( usuario.getContrasena() );
        usuarioRepository.save( aux );
        return aux;
    }
}
