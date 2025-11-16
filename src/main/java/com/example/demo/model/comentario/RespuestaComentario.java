package com.example.demo.model.comentario;

import com.example.demo.model.base.Entidad;
import com.example.demo.model.sesion.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_respuestaComentario")

public class RespuestaComentario extends Entidad {

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(name = "fechaRespuesta")
    private LocalDateTime fechaRespuesta;

    @ManyToOne
    @JoinColumn(name = "idComentario", nullable = false)
    private Comentario comentario;

    @ManyToOne
    @JoinColumn(name = "idAdministrador", nullable = false)
    private Usuario administrador;
}
