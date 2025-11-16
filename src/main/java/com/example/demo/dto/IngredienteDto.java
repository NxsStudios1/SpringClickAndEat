package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IngredienteDto {

    private int id;
    private String nombre;
    private String descripcion;
    private double cantidadPorcion;
    private int unidadMedida;
    private double stockActual;
    private double precioUnitario;
}
