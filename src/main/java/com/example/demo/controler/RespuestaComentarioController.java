package com.example.demo.controler;

import com.example.demo.dto.RespuestaComentarioDto;
import com.example.demo.model.comentario.Comentario;
import com.example.demo.model.comentario.RespuestaComentario;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.RespuestaComentarioService;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class RespuestaComentarioController {

    private final RespuestaComentarioService respuestaComentarioService;
    private final ComentarioService comentarioService;
    private final UsuarioService usuarioService;

    // GET: todas las respuestas
    @GetMapping("/respuestaComentario")
    public ResponseEntity<List<RespuestaComentarioDto>> lista() {
        List<RespuestaComentario> respuestas = respuestaComentarioService.getAll();
        if (respuestas == null || respuestas.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<RespuestaComentarioDto> dtos = respuestas.stream()
                .map(respuesta -> RespuestaComentarioDto.builder()
                        .id(respuesta.getId() != null ? respuesta.getId() : 0)
                        .contenido(respuesta.getContenido())
                        .fechaRespuesta(respuesta.getFechaRespuesta())
                        .idComentario(respuesta.getComentario() != null && respuesta.getComentario().getId() != null ? respuesta.getComentario().getId() : 0)
                        .idAdministrador(respuesta.getAdministrador() != null && respuesta.getAdministrador().getId() != null ? respuesta.getAdministrador().getId() : 0)
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: respuesta por id
    @GetMapping("/respuestaComentario/{id}")
    public ResponseEntity<RespuestaComentarioDto> getById(@PathVariable Integer id) {
        RespuestaComentario respuesta = respuestaComentarioService.getById(id);
        if (respuesta == null) {
            return ResponseEntity.notFound().build();
        }

        RespuestaComentarioDto dto = RespuestaComentarioDto.builder()
                .id(respuesta.getId() != null ? respuesta.getId() : 0)
                .contenido(respuesta.getContenido())
                .fechaRespuesta(respuesta.getFechaRespuesta())
                .idComentario(respuesta.getComentario() != null && respuesta.getComentario().getId() != null ? respuesta.getComentario().getId() : 0)
                .idAdministrador(respuesta.getAdministrador() != null && respuesta.getAdministrador().getId() != null ? respuesta.getAdministrador().getId() : 0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // POST: crear nueva respuesta
    @PostMapping("/respuestaComentario")
    public ResponseEntity<RespuestaComentarioDto> save(@RequestBody RespuestaComentarioDto dtoEntrada) {

        Comentario comentario = dtoEntrada.getIdComentario() != 0 ? comentarioService.getById(dtoEntrada.getIdComentario()) : null;

        Usuario administrador = dtoEntrada.getIdAdministrador() != 0 ? usuarioService.getById(dtoEntrada.getIdAdministrador()) : null;

        RespuestaComentario respuesta = new RespuestaComentario();
        respuesta.setContenido(dtoEntrada.getContenido());
        respuesta.setComentario(comentario);
        respuesta.setAdministrador(administrador);
        respuesta = respuestaComentarioService.save(respuesta);

        RespuestaComentarioDto dto = RespuestaComentarioDto.builder()
                .id(respuesta.getId() != null ? respuesta.getId() : 0)
                .contenido(respuesta.getContenido())
                .fechaRespuesta(respuesta.getFechaRespuesta())
                .idComentario(respuesta.getComentario() != null && respuesta.getComentario().getId() != null ? respuesta.getComentario().getId() : 0)
                .idAdministrador(respuesta.getAdministrador() != null && respuesta.getAdministrador().getId() != null ? respuesta.getAdministrador().getId() : 0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar respuesta
    @DeleteMapping("/respuestaComentario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        respuestaComentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar respuesta
    @PutMapping("/respuestaComentario/{id}")
    public ResponseEntity<RespuestaComentarioDto> update(@PathVariable Integer id,
                                                         @RequestBody RespuestaComentarioDto dtoEntrada) {

        Comentario comentario = dtoEntrada.getIdComentario() != 0 ? comentarioService.getById(dtoEntrada.getIdComentario()) : null;

        Usuario administrador = dtoEntrada.getIdAdministrador() != 0 ? usuarioService.getById(dtoEntrada.getIdAdministrador()) : null;

        RespuestaComentario cambios = new RespuestaComentario();
        cambios.setContenido(dtoEntrada.getContenido());
        cambios.setComentario(comentario);
        cambios.setAdministrador(administrador);

        RespuestaComentario actualizado = respuestaComentarioService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        RespuestaComentarioDto dto = RespuestaComentarioDto.builder()
                .id(actualizado.getId() != null ? actualizado.getId() : 0)
                .contenido(actualizado.getContenido())
                .fechaRespuesta(actualizado.getFechaRespuesta())
                .idComentario(actualizado.getComentario() != null && actualizado.getComentario().getId() != null ? actualizado.getComentario().getId() : 0)
                .idAdministrador(actualizado.getAdministrador() != null && actualizado.getAdministrador().getId() != null ? actualizado.getAdministrador().getId() : 0)
                .build();

        return ResponseEntity.ok(dto);
    }
}
