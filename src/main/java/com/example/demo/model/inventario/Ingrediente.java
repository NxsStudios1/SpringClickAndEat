package com.example.demo.model.inventario;

import com.example.demo.model.base.Entidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productos")
@Entity
@Table(name = "tbl_ingrediente")
public class Ingrediente extends Entidad {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(nullable = false, length = 200)
    private String descripcion;

    @Column(nullable = false)
    private Double cantidadPorcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnidadMedidaEnum unidadMedida;

    @Column(nullable = false)
    private Double stockActual;

    @Column(nullable = false)
    private Double precioUnitario;

    @OneToMany(mappedBy = "ingrediente")
    private List<ProductoIngrediente> productos;
}
