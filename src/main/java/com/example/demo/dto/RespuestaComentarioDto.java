// backend: com.example.demo.dto.RespuestaComentarioDto

package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class RespuestaComentarioDto {

    private int id;
    private String contenido;

    @JsonFormat(shape = JsonFormat.Shape.STRING,
            pattern = "dd/MM/yyyy HH:mm",
            timezone = "America/Mexico_City")
    private LocalDateTime fechaRespuesta;

    private int idComentario;
    private int idAdministrador;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String nombreAdministrador;
}
