package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductoDto {

    private int id;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean disponible;
    private int idCategoria;
}
