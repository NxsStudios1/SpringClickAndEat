package com.example.demo.repository;

import com.example.demo.model.comentario.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ComentarioRepository
        extends JpaRepository<Comentario, Integer> {

    List<Comentario> findAllByOrderByFechaComentarioDesc();
    @Query("SELECT c FROM Comentario c ORDER BY c.fechaComentario DESC")
    List<Comentario> obtenerComentariosOrdenados();

}
