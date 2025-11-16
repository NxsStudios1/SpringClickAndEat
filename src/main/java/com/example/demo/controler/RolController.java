package com.example.demo.controler;

import com.example.demo.dto.RolDto;
import com.example.demo.model.sesion.Rol;
import com.example.demo.model.sesion.RolEnum;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.RolService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
@Order(1)
public class RolController implements CommandLineRunner {
    private final RolService rolService;
    private final RolRepository rolRepository;

    @GetMapping("/rol")
    public ResponseEntity<List<RolDto>> lista() {
        List<Rol> roles = rolService.getAll();
        if (roles == null || roles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<RolDto> dtos = roles.stream()
                .map(rol -> RolDto.builder()
                        .rol(rol.getTipo() != null ? rol.getTipo().getIdRol() : null)
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/rol/{id}")
    public ResponseEntity<RolDto> getById(@PathVariable Integer id) {
        Rol rol = rolService.getById(id);
        if (rol == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(
                RolDto.builder().rol(
                        rol.getTipo() != null ? rol.getTipo().getIdRol() : null
                ).build()
        );
    }

    @PostMapping("/rol")
    public ResponseEntity<RolDto> save(@RequestBody RolDto rolDto) {
        Rol rol = Rol.builder()
                .tipo(RolEnum.getById(rolDto.getRol()))
                .build();
        rolService.save(rol);
        return ResponseEntity.ok(
                RolDto.builder().rol(
                        rol.getTipo() != null ? rol.getTipo().getIdRol() : null
                ).build()
        );
    }

    @DeleteMapping("/rol/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        rolService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/rol/{id}")
    public ResponseEntity<RolDto> update(@PathVariable Integer id, @RequestBody RolDto rolDto) {
        Rol updated = rolService.update(id, Rol.builder()
                .tipo(RolEnum.getById(rolDto.getRol()))
                .build()
        );
        return ResponseEntity.ok(
                RolDto.builder().rol(
                        updated.getTipo() != null ? updated.getTipo().getIdRol() : null
                ).build()
        );
    }

    @Override
    public void run(String... args) throws Exception {
        if (rolRepository.findByTipo(RolEnum.ADMINISTRADOR) == null) {
            rolRepository.save(Rol.builder().tipo(RolEnum.ADMINISTRADOR).build());
        }
        if (rolRepository.findByTipo(RolEnum.CLIENTE) == null) {
            rolRepository.save(Rol.builder().tipo(RolEnum.CLIENTE).build());
        }
    }
}