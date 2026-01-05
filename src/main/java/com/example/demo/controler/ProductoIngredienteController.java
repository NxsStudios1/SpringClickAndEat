package com.example.demo.controler;

import com.example.demo.dto.ProductoIngredienteDto;
import com.example.demo.model.inventario.Ingrediente;
import com.example.demo.model.inventario.Producto;
import com.example.demo.model.inventario.ProductoIngrediente;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.ProductoIngredienteService;
import com.example.demo.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class ProductoIngredienteController {

    private final ProductoIngredienteService productoIngredienteService;
    private final ProductoService productoService;
    private final IngredienteService ingredienteService;

    // =================== GET GENERALES ===================

    // GET: lista completa
    @GetMapping("/productoIngrediente")
    public ResponseEntity<List<ProductoIngredienteDto>> lista() {
        List<ProductoIngrediente> lista = productoIngredienteService.getAll();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductoIngredienteDto> dtos = lista.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: productoIngrediente por id
    @GetMapping("/productoIngrediente/{id}")
    public ResponseEntity<ProductoIngredienteDto> getById(@PathVariable Integer id) {
        ProductoIngrediente pi = productoIngredienteService.getById(id);
        if (pi == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(toDto(pi));
    }

    // GET: por idProducto
    @GetMapping("/productoIngrediente/producto/{idProducto}")
    public ResponseEntity<List<ProductoIngredienteDto>> getByProducto(
            @PathVariable Integer idProducto) {

        List<ProductoIngrediente> lista =
                productoIngredienteService.findByProducto(idProducto);

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductoIngredienteDto> dtos = lista.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }


    // POST: crear relación producto-ingrediente
    @PostMapping("/productoIngrediente")
    public ResponseEntity<ProductoIngredienteDto> save(
            @RequestBody ProductoIngredienteDto dto) {

        Producto producto = productoService.getById(dto.getIdProducto());
        Ingrediente ingrediente = ingredienteService.getById(dto.getIdIngrediente());

        if (producto == null || ingrediente == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductoIngrediente entidad = new ProductoIngrediente();
        entidad.setCantidadIngrediente(dto.getCantidadIngrediente());
        entidad.setProducto(producto);
        entidad.setIngrediente(ingrediente);

        ProductoIngrediente guardado = productoIngredienteService.save(entidad);

        return ResponseEntity.ok(toDto(guardado));
    }

    // PUT: actualizar relación
    @PutMapping("/productoIngrediente/{id}")
    public ResponseEntity<ProductoIngredienteDto> update(
            @PathVariable Integer id,
            @RequestBody ProductoIngredienteDto dto) {

        Producto producto = productoService.getById(dto.getIdProducto());
        Ingrediente ingrediente = ingredienteService.getById(dto.getIdIngrediente());

        if (producto == null || ingrediente == null) {
            return ResponseEntity.badRequest().build();
        }

        ProductoIngrediente entidad = new ProductoIngrediente();
        entidad.setCantidadIngrediente(dto.getCantidadIngrediente());
        entidad.setProducto(producto);
        entidad.setIngrediente(ingrediente);

        ProductoIngrediente actualizado =
                productoIngredienteService.update(id, entidad);

        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(toDto(actualizado));
    }

    // DELETE: eliminar relación
    @DeleteMapping("/productoIngrediente/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        productoIngredienteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // =================== MAPEADOR ENTIDAD -> DTO ===================

    private ProductoIngredienteDto toDto(ProductoIngrediente entidad) {
        return ProductoIngredienteDto.builder()
                .id(entidad.getId() != null ? entidad.getId() : 0)
                .cantidadIngrediente(
                        entidad.getCantidadIngrediente() != null
                                ? entidad.getCantidadIngrediente()
                                : 0.0
                )
                .idProducto(
                        entidad.getProducto() != null && entidad.getProducto().getId() != null
                                ? entidad.getProducto().getId()
                                : 0
                )
                .idIngrediente(
                        entidad.getIngrediente() != null && entidad.getIngrediente().getId() != null
                                ? entidad.getIngrediente().getId()
                                : 0
                )
                .nombreProducto(
                        entidad.getProducto() != null ? entidad.getProducto().getNombre() : null
                )
                .nombreIngrediente(
                        entidad.getIngrediente() != null ? entidad.getIngrediente().getNombre() : null
                )
                .build();
    }
}
