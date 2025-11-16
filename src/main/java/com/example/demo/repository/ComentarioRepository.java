package com.example.demo.repository;

import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.sesion.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
