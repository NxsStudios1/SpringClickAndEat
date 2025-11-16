package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DetallePedidoDto {

    private int id;
    private int tipoItem;
    private int cantidad;
    private double precioUnitario;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private double subtotal;

    private int idProducto;
    private int idPromocion;
    private int idPedido;
}
