package com.example.demo.model.sesion;

import com.example.demo.model.base.Entidad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "tbl_rol")
public class Rol extends Entidad {

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, unique = true)
    private RolEnum tipo;

    @OneToMany(mappedBy = "rol", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    @Override
    public String toString() {
        return tipo != null ? tipo.name() : "Rol";
    }

}