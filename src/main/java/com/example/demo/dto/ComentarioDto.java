// src/main/java/com/example/demo/dto/ComentarioDto.java
package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ComentarioDto {

    private int id;
    private String asunto;
    private String contenido;
    private int calificacion;

    // ahora como STRING (COMIDA, SERVICIO, etc.)
    private String categoria;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm",
            timezone = "America/Mexico_City"
    )
    private LocalDateTime fechaComentario;

    private int idCliente;

    // Nombre del cliente que hizo el comentario
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nombreCliente;

    // Respuestas del administrador embebidas
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<RespuestaComentarioDto> respuestas;
}
