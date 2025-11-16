package com.example.demo.controler;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.model.sesion.Rol;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.RolService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RolService rolService; // Para mapear el int rol a Rol entidad

    // Obtener todos los usuarios
    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioDto>> lista() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios == null || usuarios.isEmpty())
            return ResponseEntity.notFound().build();

        List<UsuarioDto> dtos = usuarios.stream().map(usuario -> UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .telefono(usuario.getTelefono())
                .contrasena(usuario.getContrasena())
                .rol(usuario.getRol() != null ? usuario.getRol().getId() : null)
                .build()
        ).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Obtener usuario por id
    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> getById(@PathVariable Integer id) {
        Usuario usuario = usuarioService.getById(id);
        if (usuario == null)
            return ResponseEntity.notFound().build();

        UsuarioDto dto = UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .telefono(usuario.getTelefono())
                .contrasena(usuario.getContrasena())
                .rol(usuario.getRol() != null ? usuario.getRol().getId() : null)
                .build();
        return ResponseEntity.ok(dto);
    }

    // Guardar nuevo usuario
    @PostMapping("/usuario")
    public ResponseEntity<UsuarioDto> save(@RequestBody UsuarioDto usuarioDto) {
        Rol rol = rolService.getById(usuarioDto.getRol());
        Usuario usuario = Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .telefono(usuarioDto.getTelefono())
                .contrasena(usuarioDto.getContrasena())
                .rol(rol)
                .build();

        usuario = usuarioService.save(usuario);

        UsuarioDto dto = UsuarioDto.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .telefono(usuario.getTelefono())
                .contrasena(usuario.getContrasena())
                .rol(usuario.getRol() != null ? usuario.getRol().getId() : null)
                .build();

        return ResponseEntity.ok(dto);
    }

    // Eliminar usuario
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar usuario
    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable Integer id, @RequestBody UsuarioDto usuarioDto) {
        Rol rol = rolService.getById(usuarioDto.getRol());
        Usuario usuario = Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .telefono(usuarioDto.getTelefono())
                .contrasena(usuarioDto.getContrasena())
                .rol(rol)
                .build();
        Usuario updated = usuarioService.update(id, usuario);

        UsuarioDto dto = UsuarioDto.builder()
                .id(updated.getId())
                .nombre(updated.getNombre())
                .telefono(updated.getTelefono())
                .contrasena(updated.getContrasena())
                .rol(updated.getRol() != null ? updated.getRol().getId() : null)
                .build();

        return ResponseEntity.ok(dto);
    }
}