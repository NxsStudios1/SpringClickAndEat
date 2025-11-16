package com.example.demo.model.inventario;

import com.example.demo.model.base.Entidad;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"promocion"})
@Entity
@Table(name = "tbl_promocionProducto")
public class PromocionProducto extends Entidad {

    @Column(nullable = false)
    private Double cantidadProducto;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "idPromocion", nullable = false)
    private Promocion promocion;

}