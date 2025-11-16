package com.example.demo.repository;

import com.example.demo.model.comentario.RespuestaComentario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaComentarioRepository extends JpaRepository<RespuestaComentario, Integer> {
}
