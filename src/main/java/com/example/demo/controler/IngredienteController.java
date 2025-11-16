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

    // GET: todos los ingredientes
    @GetMapping("/ingrediente")
    public ResponseEntity<List<IngredienteDto>> lista() {
        List<Ingrediente> ingredientes = ingredienteService.getAll();
        if (ingredientes == null || ingredientes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<IngredienteDto> dtos = ingredientes.stream()
                .map(ingrediente -> IngredienteDto.builder()
                        .id(ingrediente.getId() != null ? ingrediente.getId() : 0)
                        .nombre(ingrediente.getNombre())
                        .descripcion(ingrediente.getDescripcion())
                        .cantidadPorcion(ingrediente.getCantidadPorcion() != null ? ingrediente.getCantidadPorcion() : 0.0)
                        .unidadMedida(ingrediente.getUnidadMedida() != null ? ingrediente.getUnidadMedida().getIdUnidad() : 0)
                        .stockActual(ingrediente.getStockActual() != null ? ingrediente.getStockActual() : 0.0)
                        .precioUnitario(ingrediente.getPrecioUnitario() != null ? ingrediente.getPrecioUnitario() : 0.0)
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: ingrediente por id
    @GetMapping("/ingrediente/{id}")
    public ResponseEntity<IngredienteDto> getById(@PathVariable Integer id) {
        Ingrediente ingrediente = ingredienteService.getById(id);
        if (ingrediente == null) {
            return ResponseEntity.notFound().build();
        }

        IngredienteDto dto = IngredienteDto.builder()
                .id(ingrediente.getId() != null ? ingrediente.getId() : 0)
                .nombre(ingrediente.getNombre())
                .descripcion(ingrediente.getDescripcion())
                .cantidadPorcion(ingrediente.getCantidadPorcion() != null ? ingrediente.getCantidadPorcion() : 0.0)
                .unidadMedida(ingrediente.getUnidadMedida() != null ? ingrediente.getUnidadMedida().getIdUnidad() : 0)
                .stockActual(ingrediente.getStockActual() != null ? ingrediente.getStockActual() : 0.0)
                .precioUnitario(ingrediente.getPrecioUnitario() != null ? ingrediente.getPrecioUnitario() : 0.0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // POST: crear ingrediente
    @PostMapping("/ingrediente")
    public ResponseEntity<IngredienteDto> save(@RequestBody IngredienteDto dtoEntrada) {

        UnidadMedidaEnum unidad = UnidadMedidaEnum.getById(dtoEntrada.getUnidadMedida());

        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre(dtoEntrada.getNombre());
        ingrediente.setDescripcion(dtoEntrada.getDescripcion());
        ingrediente.setCantidadPorcion(dtoEntrada.getCantidadPorcion());
        ingrediente.setUnidadMedida(unidad);
        ingrediente.setStockActual(dtoEntrada.getStockActual());
        ingrediente.setPrecioUnitario(dtoEntrada.getPrecioUnitario());

        ingrediente = ingredienteService.save(ingrediente);

        IngredienteDto dto = IngredienteDto.builder()
                .id(ingrediente.getId() != null ? ingrediente.getId() : 0)
                .nombre(ingrediente.getNombre())
                .descripcion(ingrediente.getDescripcion())
                .cantidadPorcion(ingrediente.getCantidadPorcion() != null ? ingrediente.getCantidadPorcion() : 0.0)
                .unidadMedida(ingrediente.getUnidadMedida() != null ? ingrediente.getUnidadMedida().getIdUnidad() : 0)
                .stockActual(ingrediente.getStockActual() != null ? ingrediente.getStockActual() : 0.0)
                .precioUnitario(ingrediente.getPrecioUnitario() != null ? ingrediente.getPrecioUnitario() : 0.0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar ingrediente
    @DeleteMapping("/ingrediente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        ingredienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar ingrediente
    @PutMapping("/ingrediente/{id}")
    public ResponseEntity<IngredienteDto> update(@PathVariable Integer id, @RequestBody IngredienteDto dtoEntrada) {

        UnidadMedidaEnum unidad = UnidadMedidaEnum.getById(dtoEntrada.getUnidadMedida());

        Ingrediente cambios = new Ingrediente();
        cambios.setNombre(dtoEntrada.getNombre());
        cambios.setDescripcion(dtoEntrada.getDescripcion());
        cambios.setCantidadPorcion(dtoEntrada.getCantidadPorcion());
        cambios.setUnidadMedida(unidad);
        cambios.setStockActual(dtoEntrada.getStockActual());
        cambios.setPrecioUnitario(dtoEntrada.getPrecioUnitario());

        Ingrediente actualizado = ingredienteService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        IngredienteDto dto = IngredienteDto.builder()
                .id(actualizado.getId() != null ? actualizado.getId() : 0)
                .nombre(actualizado.getNombre())
                .descripcion(actualizado.getDescripcion())
                .cantidadPorcion(actualizado.getCantidadPorcion() != null ? actualizado.getCantidadPorcion() : 0.0)
                .unidadMedida(actualizado.getUnidadMedida() != null ? actualizado.getUnidadMedida().getIdUnidad() : 0)
                .stockActual(actualizado.getStockActual() != null ? actualizado.getStockActual() : 0.0)
                .precioUnitario(actualizado.getPrecioUnitario() != null ? actualizado.getPrecioUnitario() : 0.0)
                .build();

        return ResponseEntity.ok(dto);
    }
}
