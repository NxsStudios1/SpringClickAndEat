package com.example.demo.controler;

import com.example.demo.dto.UsuarioDto;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.model.sesion.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.service.UsuarioService;
import com.example.demo.service.RolService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
@Component
@Order(2)
public class UsuarioController implements CommandLineRunner {

    private final UsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;
    private final RolService rolService;
    private final RolRepository rolRepository;

    // ================== GET TODOS ==================
    @GetMapping("/usuario")
    public ResponseEntity<List<UsuarioDto>> lista() {
        List<Usuario> usuarios = usuarioService.getAll();
        if (usuarios.isEmpty())
            return ResponseEntity.notFound().build();

        List<UsuarioDto> dtos = usuarios.stream().map(usuario ->
                UsuarioDto.builder()
                        .id(usuario.getId())
                        .nombre(usuario.getNombre())
                        .telefono(usuario.getTelefono())
                        .contrasena(usuario.getContrasena())
                        .rol(usuario.getRol() != null ? usuario.getRol().getId() : null)
                        .build()
        ).collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ================== GET POR ID ==================
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

    // ================== POST REGISTRO ==================
    @PostMapping("/usuario")
    public ResponseEntity<?> save(@RequestBody UsuarioDto usuarioDto) {

        // 🔥 VALIDAR TELÉFONO ÚNICO
        if (usuarioService.telefonoExiste(usuarioDto.getTelefono())) {
            return ResponseEntity
                    .status(409) // HTTP CONFLICT
                    .body("El número ya está registrado");
        }

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

    // ================== DELETE ==================
    @DeleteMapping("/usuario/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ================== UPDATE ==================
    @PutMapping("/usuario/{id}")
    public ResponseEntity<UsuarioDto> update(@PathVariable Integer id,
                                             @RequestBody UsuarioDto usuarioDto) {

        Rol rol = rolService.getById(usuarioDto.getRol());

        Usuario usuario = Usuario.builder()
                .nombre(usuarioDto.getNombre())
                .telefono(usuarioDto.getTelefono())
                .contrasena(usuarioDto.getContrasena())
                .rol(rol)
                .build();

        Usuario updated = usuarioService.update(id, usuario);
        if (updated == null)
            return ResponseEntity.notFound().build();

        UsuarioDto dto = UsuarioDto.builder()
                .id(updated.getId())
                .nombre(updated.getNombre())
                .telefono(updated.getTelefono())
                .contrasena(updated.getContrasena())
                .rol(updated.getRol() != null ? updated.getRol().getId() : null)
                .build();

        return ResponseEntity.ok(dto);
    }

    // ================== ADMIN INICIAL ==================
    @Override
    public void run(String... args) {

        String telefonoAndrea = "525576866360";

        if (usuarioRepository.existsByTelefono(telefonoAndrea)) {
            return;
        }

        Rol rolAdmin = rolRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("No existe el rol ADMIN con id = 1"));

        Usuario andrea = Usuario.builder()
                .nombre("Andrea")
                .telefono(telefonoAndrea)
                .contrasena("AndyLover1")
                .rol(rolAdmin)
                .build();

        usuarioRepository.save(andrea);
        System.out.println(">>> Usuario administrador Andrea creado.");
    }
}
