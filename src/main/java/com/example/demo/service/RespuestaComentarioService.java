package com.example.demo.service;

import com.example.demo.model.comentario.RespuestaComentario;

import java.util.List;

public interface RespuestaComentarioService {

    List<RespuestaComentario> getAll();

    RespuestaComentario getById(Integer id);

    RespuestaComentario save(RespuestaComentario respuestaComentario);

    void delete(Integer id);

    RespuestaComentario update(Integer id, RespuestaComentario respuestaComentario);
}
