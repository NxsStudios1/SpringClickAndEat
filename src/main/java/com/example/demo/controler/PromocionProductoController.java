package com.example.demo.controler;

import com.example.demo.dto.PromocionProductoDto;
import com.example.demo.model.inventario.Producto;
import com.example.demo.model.inventario.Promocion;
import com.example.demo.model.inventario.PromocionProducto;
import com.example.demo.service.ProductoService;
import com.example.demo.service.PromocionProductoService;
import com.example.demo.service.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class PromocionProductoController {

    private final PromocionProductoService promocionProductoService;
    private final ProductoService productoService;
    private final PromocionService promocionService;

    // GET: todas las relaciones promoción-producto
    @GetMapping("/promocionProducto")
    public ResponseEntity<List<PromocionProductoDto>> lista() {
        List<PromocionProducto> lista = promocionProductoService.getAll();
        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PromocionProductoDto> dtos = lista.stream()
                .map(this::mapPromocionProductoToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: por id
    @GetMapping("/promocionProducto/{id}")
    public ResponseEntity<PromocionProductoDto> getById(@PathVariable int id) {
        PromocionProducto pp = promocionProductoService.getById(id);
        if (pp == null) {
            return ResponseEntity.notFound().build();
        }

        PromocionProductoDto dto = mapPromocionProductoToDto(pp);
        return ResponseEntity.ok(dto);
    }

    // POST: crear relación promoción-producto
    @PostMapping("/promocionProducto")
    public ResponseEntity<PromocionProductoDto> save(@RequestBody PromocionProductoDto dtoEntrada) {

        Producto producto = dtoEntrada.getIdProducto() != 0 ? productoService.getById(dtoEntrada.getIdProducto()) : null;

        Promocion promocion = dtoEntrada.getIdPromocion() != 0 ? promocionService.getById(dtoEntrada.getIdPromocion()) : null;

        PromocionProducto pp = new PromocionProducto();
        pp.setCantidadProducto(dtoEntrada.getCantidadProducto());
        pp.setProducto(producto);
        pp.setPromocion(promocion);

        pp = promocionProductoService.save(pp);

        PromocionProductoDto dto = mapPromocionProductoToDto(pp);
        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar relación
    @DeleteMapping("/promocionProducto/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        promocionProductoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar relación
    @PutMapping("/promocionProducto/{id}")
    public ResponseEntity<PromocionProductoDto> update(@PathVariable int id,
                                                       @RequestBody PromocionProductoDto dtoEntrada) {

        Producto producto = dtoEntrada.getIdProducto() != 0 ? productoService.getById(dtoEntrada.getIdProducto()) : null;

        Promocion promocion = dtoEntrada.getIdPromocion() != 0 ? promocionService.getById(dtoEntrada.getIdPromocion()) : null;

        PromocionProducto cambios = new PromocionProducto();
        cambios.setCantidadProducto(dtoEntrada.getCantidadProducto());
        cambios.setProducto(producto);
        cambios.setPromocion(promocion);

        PromocionProducto actualizado = promocionProductoService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        PromocionProductoDto dto = mapPromocionProductoToDto(actualizado);
        return ResponseEntity.ok(dto);
    }

    // ========== Mapeo entidad -> DTO ==========

    private PromocionProductoDto mapPromocionProductoToDto(PromocionProducto pp) {
        return PromocionProductoDto.builder()
                .id(pp.getId() != null ? pp.getId() : 0)
                .cantidadProducto(pp.getCantidadProducto() != null ? pp.getCantidadProducto() : 0.0)
                .idProducto(pp.getProducto() != null && pp.getProducto().getId() != null ? pp.getProducto().getId() : 0)
                .idPromocion(pp.getPromocion() != null && pp.getPromocion().getId() != null ? pp.getPromocion().getId() : 0)
                .build();
    }
}
