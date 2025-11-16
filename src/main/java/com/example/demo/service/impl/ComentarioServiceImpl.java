package com.example.demo.service.impl;

import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.sesion.Rol;
import com.example.demo.repository.ComentarioRepository;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.ComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ComentarioServiceImpl implements ComentarioService {

    private final ComentarioRepository comentarioRepository;

    @Override
    public List<Comentario> getAll() {
        return comentarioRepository.findAll();
    }

    @Override
    public Comentario getById(Integer id) {
        return comentarioRepository.findById(id).orElse(null);
    }

    @Override
    public Comentario save(Comentario comentario) {
        return comentarioRepository.save(comentario);
    }

    @Override
    public void delete(Integer id) {
        comentarioRepository.deleteById(id);
    }

    @Override
    public Comentario update(Integer id, Comentario comentario) {
        Comentario actual = comentarioRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setAsunto(comentario.getAsunto());
        actual.setContenido(comentario.getContenido());
        actual.setCalificacion(comentario.getCalificacion());
        actual.setCategoria(comentario.getCategoria());
        actual.setCliente(comentario.getCliente());
        return comentarioRepository.save(actual);
    }
}