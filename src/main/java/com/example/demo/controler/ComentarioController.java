// src/main/java/com/example/demo/controler/ComentarioController.java
package com.example.demo.controler;

import com.example.demo.dto.ComentarioDto;
import com.example.demo.dto.RespuestaComentarioDto;
import com.example.demo.model.comentario.CategoriaComentarioEnum;
import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.comentario.RespuestaComentario;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;

    // ================== GET LISTA ==================
    @GetMapping("/comentario")
    public ResponseEntity<List<ComentarioDto>> lista(
            @RequestParam(name = "asunto", defaultValue = "", required = false)
            String asunto
    ) {
        List<Comentario> comentarios = comentarioService.getAll();
        if (comentarios == null || comentarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (asunto != null && !asunto.isEmpty()) {
            comentarios = comentarios.stream()
                    .filter(c -> c.getAsunto() != null &&
                            c.getAsunto().equalsIgnoreCase(asunto))
                    .collect(Collectors.toList());
        }

        List<ComentarioDto> dtos = comentarios.stream()
                .map(this::mapComentarioToDtoConRespuestas)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ================== GET POR ID ==================
    @GetMapping("/comentario/{id}")
    public ResponseEntity<ComentarioDto> getById(@PathVariable int id) {
        Comentario comentario = comentarioService.getById(id);
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }
        ComentarioDto dto = mapComentarioToDtoConRespuestas(comentario);
        return ResponseEntity.ok(dto);
    }

    // ================== POST ==================
    @PostMapping("/comentario")
    public ResponseEntity<ComentarioDto> save(@RequestBody ComentarioDto dtoEntrada) {

        Usuario cliente = dtoEntrada.getIdCliente() != 0
                ? usuarioService.getById(dtoEntrada.getIdCliente())
                : null;

        CategoriaComentarioEnum categoria = null;
        if (dtoEntrada.getCategoria() != null && !dtoEntrada.getCategoria().isEmpty()) {
            try {
                categoria = CategoriaComentarioEnum.valueOf(dtoEntrada.getCategoria());
            } catch (IllegalArgumentException ex) {
            }
        }

        Comentario comentario = Comentario.builder()
                .asunto(dtoEntrada.getAsunto())
                .contenido(dtoEntrada.getContenido())
                .calificacion(dtoEntrada.getCalificacion())
                .categoria(categoria)
                .cliente(cliente)
                .build();

        comentario = comentarioService.save(comentario);

        ComentarioDto dto = mapComentarioToDtoConRespuestas(comentario);
        return ResponseEntity.ok(dto);
    }

    // ================== PUT ==================
    @PutMapping("/comentario/{id}")
    public ResponseEntity<ComentarioDto> update(@PathVariable int id,
                                                @RequestBody ComentarioDto dtoEntrada) {

        Usuario cliente = dtoEntrada.getIdCliente() != 0
                ? usuarioService.getById(dtoEntrada.getIdCliente())
                : null;

        CategoriaComentarioEnum categoria = null;
        if (dtoEntrada.getCategoria() != null && !dtoEntrada.getCategoria().isEmpty()) {
            try {
                categoria = CategoriaComentarioEnum.valueOf(dtoEntrada.getCategoria());
            } catch (IllegalArgumentException ex) {
            }
        }

        Comentario cambios = Comentario.builder()
                .asunto(dtoEntrada.getAsunto())
                .contenido(dtoEntrada.getContenido())
                .calificacion(dtoEntrada.getCalificacion())
                .categoria(categoria)
                .cliente(cliente)
                .build();

        Comentario actualizado = comentarioService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        ComentarioDto dto = mapComentarioToDtoConRespuestas(actualizado);
        return ResponseEntity.ok(dto);
    }

    // ================== DELETE ==================
    @DeleteMapping("/comentario/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        comentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ========== MAPPER ENTIDAD -> DTO CON RESPUESTAS ==========
    private ComentarioDto mapComentarioToDtoConRespuestas(Comentario comentario) {

        List<RespuestaComentarioDto> respuestasDto;

        List<RespuestaComentario> respuestas = comentario.getRespuestas();
        if (respuestas == null || respuestas.isEmpty()) {
            respuestasDto = Collections.emptyList();
        } else {
            respuestasDto = respuestas.stream()
                    .map(r -> RespuestaComentarioDto.builder()
                            .id(r.getId() != null ? r.getId() : 0)
                            .contenido(r.getContenido())
                            .fechaRespuesta(r.getFechaRespuesta())
                            .idComentario(
                                    r.getComentario() != null && r.getComentario().getId() != null
                                            ? r.getComentario().getId()
                                            : 0
                            )
                            .idAdministrador(
                                    r.getAdministrador() != null && r.getAdministrador().getId() != null
                                            ? r.getAdministrador().getId()
                                            : 0
                            )
                            .nombreAdministrador(
                                    r.getAdministrador() != null
                                            ? r.getAdministrador().getNombre()
                                            : null
                            )
                            .build()
                    )
                    .collect(Collectors.toList());
        }

        return ComentarioDto.builder()
                .id(comentario.getId() != null ? comentario.getId() : 0)
                .asunto(comentario.getAsunto())
                .contenido(comentario.getContenido())
                .calificacion(comentario.getCalificacion())
                .categoria(comentario.getCategoria() != null ? comentario.getCategoria().name() : null)
                .fechaComentario(comentario.getFechaComentario())
                .idCliente(
                        comentario.getCliente() != null && comentario.getCliente().getId() != null
                                ? comentario.getCliente().getId()
                                : 0
                )
                .nombreCliente(
                        comentario.getCliente() != null
                                ? comentario.getCliente().getNombre()
                                : null
                )
                .respuestas(respuestasDto)
                .build();
    }


}
