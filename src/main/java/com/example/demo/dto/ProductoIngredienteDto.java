package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoIngredienteDto {

    private int id;
    private double cantidadIngrediente;
    private int idProducto;
    private int idIngrediente;
}
