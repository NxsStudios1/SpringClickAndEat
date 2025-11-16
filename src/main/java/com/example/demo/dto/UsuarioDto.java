package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioDto {
    private int id;
    private String nombre;
    private String telefono;
    private String contrasena;
    private int rol;
}
