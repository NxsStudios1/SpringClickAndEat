// src/main/java/com/example/demo/dto/PedidoDto.java
package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoDto {

    private int id;
    private String numeroTicket;
    private int estado;
    private double total;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm",
            timezone = "America/Mexico_City")
    private LocalDateTime fechaPedido;

    private String observaciones;
    private int idCliente;

    private List<DetallePedidoDto> detalles;

    private String nombreCliente;
}
