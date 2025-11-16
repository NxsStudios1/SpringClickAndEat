package com.example.demo.model.sesion;

import com.example.demo.model.base.Entidad;
import com.example.demo.model.comentario.Comentario;
import jakarta.persistence.*;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "tbl_usuario")
public class Usuario extends Entidad {

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, length = 15)
    private String telefono;

    @Column(name = "contrasena" , nullable = false)
    private String contrasena;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @OneToMany(mappedBy = "cliente")
    private List<Comentario> comentarios;
/*
    @OneToMany(mappedBy = "administrador")
    private List<RespuestaComentario> respuestas;

    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos;*/

    @Override
    public String toString() {
        return nombre + " (" + telefono + ")";
    }

}
