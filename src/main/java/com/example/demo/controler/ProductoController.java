package com.example.demo.controler;

import com.example.demo.dto.ProductoDto;
import com.example.demo.model.inventario.CategoriaProducto;
import com.example.demo.model.inventario.Producto;
import com.example.demo.service.CategoriaProductoService;
import com.example.demo.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class ProductoController {

    private final ProductoService productoService;
    private final CategoriaProductoService categoriaProductoService;

    // GET: todos los productos
    @GetMapping("/producto")
    public ResponseEntity<List<ProductoDto>> lista() {
        List<Producto> productos = productoService.getAll();
        if (productos == null || productos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<ProductoDto> dtos = productos.stream()
                .map(p -> ProductoDto.builder()
                        .id(p.getId() != null ? p.getId() : 0)
                        .nombre(p.getNombre())
                        .descripcion(p.getDescripcion())
                        .precio(p.getPrecio() != null ? p.getPrecio() : 0.0)
                        .disponible(p.getDisponible() != null ? p.getDisponible() : true)
                        .idCategoria(p.getCategoria() != null && p.getCategoria().getId() != null
                                ? p.getCategoria().getId()
                                : 0)
                        .build()
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: producto por id
    @GetMapping("/producto/{id}")
    public ResponseEntity<ProductoDto> getById(@PathVariable int id) {
        Producto p = productoService.getById(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }

        ProductoDto dto = ProductoDto.builder()
                .id(p.getId() != null ? p.getId() : 0)
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .precio(p.getPrecio() != null ? p.getPrecio() : 0.0)
                .disponible(p.getDisponible() != null ? p.getDisponible() : true)
                .idCategoria(p.getCategoria() != null && p.getCategoria().getId() != null
                        ? p.getCategoria().getId()
                        : 0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // POST: crear producto
    @PostMapping("/producto")
    public ResponseEntity<ProductoDto> save(@RequestBody ProductoDto dtoEntrada) {

        CategoriaProducto categoria =
                dtoEntrada.getIdCategoria() != 0
                        ? categoriaProductoService.getById(dtoEntrada.getIdCategoria())
                        : null;

        Producto producto = new Producto();
        producto.setNombre(dtoEntrada.getNombre());
        producto.setDescripcion(dtoEntrada.getDescripcion());
        producto.setPrecio(dtoEntrada.getPrecio());
        producto.setDisponible(dtoEntrada.isDisponible());
        producto.setCategoria(categoria);

        producto = productoService.save(producto);

        ProductoDto dto = ProductoDto.builder()
                .id(producto.getId() != null ? producto.getId() : 0)
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio() != null ? producto.getPrecio() : 0.0)
                .disponible(producto.getDisponible() != null ? producto.getDisponible() : true)
                .idCategoria(producto.getCategoria() != null && producto.getCategoria().getId() != null
                        ? producto.getCategoria().getId()
                        : 0)
                .build();

        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar producto (manejando conflicto)
    @DeleteMapping("/producto/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException e) {
            // Nuestro mensaje del service (promos o ingredientes)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (DataIntegrityViolationException e) {
            // Por si se escapa alguna restricción de BD
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("No se puede eliminar el producto porque está relacionado con otros registros.");
        }
    }

    // PUT: actualizar producto
    @PutMapping("/producto/{id}")
    public ResponseEntity<ProductoDto> update(@PathVariable int id,
                                              @RequestBody ProductoDto dtoEntrada) {

        CategoriaProducto categoria =
                dtoEntrada.getIdCategoria() != 0
                        ? categoriaProductoService.getById(dtoEntrada.getIdCategoria())
                        : null;

        Producto cambios = new Producto();
        cambios.setNombre(dtoEntrada.getNombre());
        cambios.setDescripcion(dtoEntrada.getDescripcion());
        cambios.setPrecio(dtoEntrada.getPrecio());
        cambios.setDisponible(dtoEntrada.isDisponible());
        cambios.setCategoria(categoria);

        Producto actualizado = productoService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        ProductoDto dto = ProductoDto.builder()
                .id(actualizado.getId() != null ? actualizado.getId() : 0)
                .nombre(actualizado.getNombre())
                .descripcion(actualizado.getDescripcion())
                .precio(actualizado.getPrecio() != null ? actualizado.getPrecio() : 0.0)
                .disponible(actualizado.getDisponible() != null ? actualizado.getDisponible() : true)
                .idCategoria(actualizado.getCategoria() != null && actualizado.getCategoria().getId() != null
                        ? actualizado.getCategoria().getId()
                        : 0)
                .build();

        return ResponseEntity.ok(dto);
    }
}
