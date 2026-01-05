package com.example.demo.controler;

import com.example.demo.dto.IngredienteDto;
import com.example.demo.model.inventario.Ingrediente;
import com.example.demo.model.inventario.UnidadMedidaEnum;
import com.example.demo.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class IngredienteController {

    private final IngredienteService ingredienteService;

    // ===================== GET TODOS =====================
    @GetMapping("/ingrediente")
    public ResponseEntity<List<IngredienteDto>> lista() {
        List<Ingrediente> ingredientes = ingredienteService.getAll();

        if (ingredientes == null || ingredientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<IngredienteDto> dtos = ingredientes.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // ===================== GET POR ID =====================
    @GetMapping("/ingrediente/{id}")
    public ResponseEntity<IngredienteDto> getById(@PathVariable Integer id) {
        Ingrediente ing = ingredienteService.getById(id);
        if (ing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(ing));
    }

    // ===================== POST =====================
    @PostMapping("/ingrediente")
    public ResponseEntity<IngredienteDto> save(@RequestBody IngredienteDto dtoEntrada) {
        // Validación básica
        if (dtoEntrada.getNombre() == null || dtoEntrada.getNombre().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        if (dtoEntrada.getUnidadMedida() == null || dtoEntrada.getUnidadMedida().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        Ingrediente entidad = fromDto(dtoEntrada);
        entidad = ingredienteService.save(entidad);

        return ResponseEntity.ok(toDto(entidad));
    }

    // ===================== PUT =====================
    @PutMapping("/ingrediente/{id}")
    public ResponseEntity<IngredienteDto> update(@PathVariable Integer id,
                                                 @RequestBody IngredienteDto dtoEntrada) {

        Ingrediente existente = ingredienteService.getById(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }

        existente.setNombre(dtoEntrada.getNombre());
        existente.setDescripcion(dtoEntrada.getDescripcion());
        existente.setCantidadPorcion(dtoEntrada.getCantidadPorcion());

        if (dtoEntrada.getUnidadMedida() != null) {
            UnidadMedidaEnum unidad =
                    UnidadMedidaEnum.valueOf(dtoEntrada.getUnidadMedida());
            existente.setUnidadMedida(unidad);
        }

        existente.setStockActual(dtoEntrada.getStockActual());
        existente.setPrecioUnitario(dtoEntrada.getPrecioUnitario());

        Ingrediente actualizado = ingredienteService.update(id, existente);

        return ResponseEntity.ok(toDto(actualizado));
    }

    // ===================== DELETE =====================
    @DeleteMapping("/ingrediente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ingredienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===================== MAPEOS DTO <-> ENTIDAD =====================

    private IngredienteDto toDto(Ingrediente ing) {
        return IngredienteDto.builder()
                .id(ing.getId() != null ? ing.getId() : 0)
                .nombre(ing.getNombre())
                .descripcion(ing.getDescripcion())
                .cantidadPorcion(ing.getCantidadPorcion() != null ? ing.getCantidadPorcion() : 0.0)
                .unidadMedida(
                        ing.getUnidadMedida() != null ? ing.getUnidadMedida().name() : null
                )
                .stockActual(ing.getStockActual() != null ? ing.getStockActual() : 0.0)
                .precioUnitario(ing.getPrecioUnitario() != null ? ing.getPrecioUnitario() : 0.0)
                .build();
    }

    private Ingrediente fromDto(IngredienteDto dto) {
        UnidadMedidaEnum unidad =
                UnidadMedidaEnum.valueOf(dto.getUnidadMedida());

        return Ingrediente.builder()
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .cantidadPorcion(dto.getCantidadPorcion())
                .unidadMedida(unidad)
                .stockActual(dto.getStockActual())
                .precioUnitario(dto.getPrecioUnitario())
                .build();
    }
}
