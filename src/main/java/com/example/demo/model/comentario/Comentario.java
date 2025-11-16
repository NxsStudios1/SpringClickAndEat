package com.example.demo.model.comentario;

import com.example.demo.model.base.Entidad;
import com.example.demo.model.sesion.Usuario;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "tbl_comentario")
public class Comentario extends Entidad {

    @Column(nullable = false, length = 100)
    private String asunto;

    @Column(nullable = false, length = 2000)
    private String contenido;

    @Column(nullable = false)
    private int calificacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "categoria")
    private CategoriaComentarioEnum categoria;

    @Column(name = "fechaComentario", updatable = false)
    private LocalDateTime fechaComentario;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Usuario cliente;

    /*@OneToMany(mappedBy = "comentario", cascade = CascadeType.ALL)
    private List<RespuestaComentario> respuestas;
*/
    public Usuario getUsuario(){
        return cliente;
    }

    @PrePersist
    public void prePersist() {
        if (fechaComentario == null) {
            fechaComentario = LocalDateTime.now();
        }
    }

}