package com.example.demo.controler;

import com.example.demo.dto.PromocionDto;
import com.example.demo.model.inventario.Promocion;
import com.example.demo.service.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class PromocionController {

    private final PromocionService promocionService;

    // GET: todas las promociones
    @GetMapping("/promocion")
    public ResponseEntity<List<PromocionDto>> lista() {
        List<Promocion> promociones = promocionService.getAll();
        if (promociones == null || promociones.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PromocionDto> dtos = promociones.stream()
                .map(this::mapPromocionToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: promoción por id
    @GetMapping("/promocion/{id}")
    public ResponseEntity<PromocionDto> getById(@PathVariable int id) {
        Promocion promocion = promocionService.getById(id);
        if (promocion == null) {
            return ResponseEntity.notFound().build();
        }

        PromocionDto dto = mapPromocionToDto(promocion);
        return ResponseEntity.ok(dto);
    }

    // POST: crear promoción
    @PostMapping("/promocion")
    public ResponseEntity<PromocionDto> save(@RequestBody PromocionDto dtoEntrada) {

        Promocion promocion = new Promocion();
        promocion.setNombre(dtoEntrada.getNombre());
        promocion.setDescripcion(dtoEntrada.getDescripcion());
        promocion.setFechaInicio(dtoEntrada.getFechaInicio());
        promocion.setFechaFin(dtoEntrada.getFechaFin());
        promocion.setPrecioTotalConDescuento(dtoEntrada.getPrecioTotalConDescuento());
        promocion.setActivo(dtoEntrada.isActivo());

        promocion = promocionService.save(promocion);

        PromocionDto dto = mapPromocionToDto(promocion);
        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar promoción
    @DeleteMapping("/promocion/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        promocionService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar promoción
    @PutMapping("/promocion/{id}")
    public ResponseEntity<PromocionDto> update(@PathVariable int id,
                                               @RequestBody PromocionDto dtoEntrada) {

        Promocion cambios = new Promocion();
        cambios.setNombre(dtoEntrada.getNombre());
        cambios.setDescripcion(dtoEntrada.getDescripcion());
        cambios.setFechaInicio(dtoEntrada.getFechaInicio());
        cambios.setFechaFin(dtoEntrada.getFechaFin());
        cambios.setPrecioTotalConDescuento(dtoEntrada.getPrecioTotalConDescuento());
        cambios.setActivo(dtoEntrada.isActivo());

        Promocion actualizada = promocionService.update(id, cambios);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }

        PromocionDto dto = mapPromocionToDto(actualizada);
        return ResponseEntity.ok(dto);
    }

    // ================== Mapeo entidad -> DTO ==================

    private PromocionDto mapPromocionToDto(Promocion p) {
        return PromocionDto.builder()
                .id(p.getId() != null ? p.getId() : 0)
                .nombre(p.getNombre())
                .descripcion(p.getDescripcion())
                .fechaInicio(p.getFechaInicio())
                .fechaFin(p.getFechaFin())
                .precioTotalConDescuento(p.getPrecioTotalConDescuento() != null ? p.getPrecioTotalConDescuento() : 0.0)
                .activo(p.getActivo() != null ? p.getActivo() : true)
                .build();
    }
}
