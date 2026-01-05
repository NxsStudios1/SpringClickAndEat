package com.example.demo.controler;

import com.example.demo.dto.CategoriaProductoDto;
import com.example.demo.model.inventario.CategoriaProducto;
import com.example.demo.service.CategoriaProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    // GET: todas las categorías
    @GetMapping("/categoriaProducto")
    public ResponseEntity<List<CategoriaProductoDto>> lista() {
        List<CategoriaProducto> categorias = categoriaProductoService.getAll();
        if (categorias == null || categorias.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<CategoriaProductoDto> dtos = categorias.stream()
                .map(cat -> CategoriaProductoDto.builder()
                        .id(cat.getId() != null ? cat.getId() : 0)
                        .nombre(cat.getNombre())
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: categoría por id
    @GetMapping("/categoriaProducto/{id}")
    public ResponseEntity<CategoriaProductoDto> getById(@PathVariable Integer id) {
        CategoriaProducto cat = categoriaProductoService.getById(id);
        if (cat == null) {
            return ResponseEntity.notFound().build();
        }

        CategoriaProductoDto dto = CategoriaProductoDto.builder()
                .id(cat.getId() != null ? cat.getId() : 0)
                .nombre(cat.getNombre())
                .build();

        return ResponseEntity.ok(dto);
    }

    // POST:rear categoría
    @PostMapping("/categoriaProducto")
    public ResponseEntity<CategoriaProductoDto> save(@RequestBody CategoriaProductoDto dtoEntrada) {

        CategoriaProducto cat = new CategoriaProducto();
        cat.setNombre(dtoEntrada.getNombre());

        cat = categoriaProductoService.save(cat);

        CategoriaProductoDto dto = CategoriaProductoDto.builder()
                .id(cat.getId() != null ? cat.getId() : 0)
                .nombre(cat.getNombre())
                .build();

        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar categoría
    @DeleteMapping("/categoriaProducto/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        categoriaProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar categoría
    @PutMapping("/categoriaProducto/{id}")
    public ResponseEntity<CategoriaProductoDto> update(@PathVariable Integer id,
                                                       @RequestBody CategoriaProductoDto dtoEntrada) {

        CategoriaProducto cambios = new CategoriaProducto();
        cambios.setNombre(dtoEntrada.getNombre());

        CategoriaProducto actualizado = categoriaProductoService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        CategoriaProductoDto dto = CategoriaProductoDto.builder()
                .id(actualizado.getId() != null ? actualizado.getId() : 0)
                .nombre(actualizado.getNombre())
                .build();

        return ResponseEntity.ok(dto);
    }
}
