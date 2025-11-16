package com.example.demo.service;

import com.example.demo.dto.ComentarioDto;
import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.sesion.Rol;

import java.util.List;

public interface ComentarioService {
    List<Comentario> getAll();
    Comentario getById(Integer id);
    Comentario save(Comentario comentario);
    void delete(Integer id);
    Comentario update(Integer id, Comentario comentario);
}
