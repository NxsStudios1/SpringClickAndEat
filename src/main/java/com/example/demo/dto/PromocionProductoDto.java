package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PromocionProductoDto {

    private int id;
    private double cantidadProducto;
    private int idProducto;
    private int idPromocion;
}
