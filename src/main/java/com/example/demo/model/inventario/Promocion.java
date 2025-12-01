package com.example.demo.model.inventario;

import com.example.demo.model.base.Entidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "productos")
@Entity
@Table(name = "tbl_promocion")
public class Promocion extends Entidad {

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 300)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false)
    private Double precioTotalConDescuento;

    @Column
    private Boolean activo = true;

    @OneToMany(
            mappedBy = "promocion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PromocionProducto> productos;

}