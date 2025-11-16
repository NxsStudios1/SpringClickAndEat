package com.example.demo.service.impl;

import com.example.demo.model.comentario.RespuestaComentario;
import com.example.demo.repository.RespuestaComentarioRepository;
import com.example.demo.service.RespuestaComentarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RespuestaComentarioServiceImpl implements RespuestaComentarioService {

    private final RespuestaComentarioRepository respuestaComentarioRepository;

    @Override
    public List<RespuestaComentario> getAll() {
        return respuestaComentarioRepository.findAll();
    }

    @Override
    public RespuestaComentario getById(Integer id) {
        return respuestaComentarioRepository.findById(id).orElse(null);
    }

    @Override
    public RespuestaComentario save(RespuestaComentario respuestaComentario) {
        return respuestaComentarioRepository.save(respuestaComentario);
    }

    @Override
    public void delete(Integer id) {
        respuestaComentarioRepository.deleteById(id);
    }

    @Override
    public RespuestaComentario update(Integer id, RespuestaComentario respuestaComentario) {
        RespuestaComentario actual = respuestaComentarioRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setContenido(respuestaComentario.getContenido());
        actual.setComentario(respuestaComentario.getComentario());
        actual.setAdministrador(respuestaComentario.getAdministrador());
        return respuestaComentarioRepository.save(actual);
    }
}
