package com.example.demo.controler;

import com.example.demo.dto.ComentarioDto;
import com.example.demo.model.comentario.CategoriaComentarioEnum;
import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class ComentarioController {

    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;

    // Obtener todos los comentarios (con filtro opcional por asunto)
    @GetMapping("/comentario")
    public ResponseEntity<List<ComentarioDto>> lista(
            @RequestParam(name = "asunto", required = false, defaultValue = "") String asunto
    ) {
        List<Comentario> comentarios = comentarioService.getAll();
        if (comentarios == null || comentarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Comentario> filtrados = comentarios;
        if (asunto != null && !asunto.isBlank()) {
            filtrados = comentarios.stream()
                    .filter(c -> c.getAsunto() != null &&
                            c.getAsunto().equalsIgnoreCase(asunto))
                    .collect(Collectors.toList());
        }

        List<ComentarioDto> dtos = filtrados.stream()
                .map(comentario -> ComentarioDto.builder()
                        .id(comentario.getId() != null ? comentario.getId() : 0)
                        .asunto(comentario.getAsunto())
                        .contenido(comentario.getContenido())
                        .calificacion(comentario.getCalificacion())
                        .categoria(
                                comentario.getCategoria() != null
                                        ? comentario.getCategoria().getIdCategoria()
                                        : 0
                        )
                        .fechaComentario(comentario.getFechaComentario())
                        .idCliente(
                                comentario.getCliente() != null &&
                                        comentario.getCliente().getId() != null
                                        ? comentario.getCliente().getId()
                                        : 0
                        )
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // Obtener comentario por id
    @GetMapping("/comentario/{id}")
    public ResponseEntity<ComentarioDto> getById(@PathVariable Integer id) {
        Comentario comentario = comentarioService.getById(id);
        if (comentario == null) {
            return ResponseEntity.notFound().build();
        }

        ComentarioDto dto = ComentarioDto.builder()
                .id(comentario.getId() != null ? comentario.getId() : 0)
                .asunto(comentario.getAsunto())
                .contenido(comentario.getContenido())
                .calificacion(comentario.getCalificacion())
                .categoria(
                        comentario.getCategoria() != null
                                ? comentario.getCategoria().getIdCategoria()
                                : 0
                )
                .fechaComentario(comentario.getFechaComentario())
                .idCliente(
                        comentario.getCliente() != null &&
                                comentario.getCliente().getId() != null
                                ? comentario.getCliente().getId()
                                : 0
                )
                .build();

        return ResponseEntity.ok(dto);
    }

    // Guardar nuevo comentario
    @PostMapping("/comentario")
    public ResponseEntity<ComentarioDto> save(@RequestBody ComentarioDto comentarioDto) {

        Usuario cliente = comentarioDto.getIdCliente() != 0
                ? usuarioService.getById(comentarioDto.getIdCliente())
                : null;

        CategoriaComentarioEnum categoria = comentarioDto.getCategoria() != 0
                ? CategoriaComentarioEnum.getById(comentarioDto.getCategoria())
                : null;

        Comentario comentario = Comentario.builder()
                .asunto(comentarioDto.getAsunto())
                .contenido(comentarioDto.getContenido())
                .calificacion(comentarioDto.getCalificacion())
                .categoria(categoria)
                // fechaComentario se pone sola con @PrePersist
                .cliente(cliente)
                .build();

        comentario = comentarioService.save(comentario);

        ComentarioDto dto = ComentarioDto.builder()
                .id(comentario.getId() != null ? comentario.getId() : 0)
                .asunto(comentario.getAsunto())
                .contenido(comentario.getContenido())
                .calificacion(comentario.getCalificacion())
                .categoria(
                        comentario.getCategoria() != null
                                ? comentario.getCategoria().getIdCategoria()
                                : 0
                )
                .fechaComentario(comentario.getFechaComentario())
                .idCliente(
                        comentario.getCliente() != null &&
                                comentario.getCliente().getId() != null
                                ? comentario.getCliente().getId()
                                : 0
                )
                .build();

        return ResponseEntity.ok(dto);
    }

    // Eliminar comentario
    @DeleteMapping("/comentario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        comentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar comentario
    @PutMapping("/comentario/{id}")
    public ResponseEntity<ComentarioDto> update(@PathVariable Integer id,
                                                @RequestBody ComentarioDto comentarioDto) {

        Usuario cliente = comentarioDto.getIdCliente() != 0
                ? usuarioService.getById(comentarioDto.getIdCliente())
                : null;

        CategoriaComentarioEnum categoria = comentarioDto.getCategoria() != 0
                ? CategoriaComentarioEnum.getById(comentarioDto.getCategoria())
                : null;

        Comentario comentario = Comentario.builder()
                .asunto(comentarioDto.getAsunto())
                .contenido(comentarioDto.getContenido())
                .calificacion(comentarioDto.getCalificacion())
                .categoria(categoria)
                // no tocamos fechaComentario
                .cliente(cliente)
                .build();

        Comentario actualizado = comentarioService.update(id, comentario);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        ComentarioDto dto = ComentarioDto.builder()
                .id(actualizado.getId() != null ? actualizado.getId() : 0)
                .asunto(actualizado.getAsunto())
                .contenido(actualizado.getContenido())
                .calificacion(actualizado.getCalificacion())
                .categoria(
                        actualizado.getCategoria() != null
                                ? actualizado.getCategoria().getIdCategoria()
                                : 0
                )
                .fechaComentario(actualizado.getFechaComentario())
                .idCliente(
                        actualizado.getCliente() != null &&
                                actualizado.getCliente().getId() != null
                                ? actualizado.getCliente().getId()
                                : 0
                )
                .build();

        return ResponseEntity.ok(dto);
    }
}
