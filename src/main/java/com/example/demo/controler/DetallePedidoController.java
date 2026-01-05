// src/main/java/com/example/demo/controler/DetallePedidoController.java
package com.example.demo.controler;

import com.example.demo.dto.DetallePedidoDto;
import com.example.demo.model.inventario.Producto;
import com.example.demo.model.inventario.Promocion;
import com.example.demo.model.pedido.DetallePedido;
import com.example.demo.model.pedido.Pedido;
import com.example.demo.model.pedido.TipoItemEnum;
import com.example.demo.service.DetallePedidoService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;
    private final PedidoService pedidoService;
    private final ProductoService productoService;
    private final PromocionService promocionService;

    // GET: todos los detalles
    @GetMapping("/detallePedido")
    public ResponseEntity<List<DetallePedidoDto>> lista() {
        List<DetallePedido> detalles = detallePedidoService.getAll();
        if (detalles == null || detalles.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DetallePedidoDto> dtos = detalles.stream()
                .map(this::mapDetalleToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: detalles por idPedido
    @GetMapping("/detallePedido/pedido/{idPedido}")
    public ResponseEntity<List<DetallePedidoDto>> getByPedido(@PathVariable int idPedido) {
        List<DetallePedido> lista = detallePedidoService.findByPedidoId(idPedido);

        if (lista == null || lista.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<DetallePedidoDto> dtos = lista.stream()
                .map(this::mapDetalleToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // POST: crear detalle individual
    @PostMapping("/detallePedido")
    public ResponseEntity<DetallePedidoDto> save(@RequestBody DetallePedidoDto dtoEntrada) {

        Pedido pedido = dtoEntrada.getIdPedido() != 0
                ? pedidoService.getById(dtoEntrada.getIdPedido())
                : null;

        Producto producto = dtoEntrada.getIdProducto() != 0
                ? productoService.getById(dtoEntrada.getIdProducto())
                : null;

        Promocion promocion = dtoEntrada.getIdPromocion() != 0
                ? promocionService.getById(dtoEntrada.getIdPromocion())
                : null;

        TipoItemEnum tipoItem = dtoEntrada.getTipoItem() != 0
                ? TipoItemEnum.getById(dtoEntrada.getTipoItem())
                : null;

        double subtotal = dtoEntrada.getCantidad() * dtoEntrada.getPrecioUnitario();

        DetallePedido detalle = new DetallePedido();
        detalle.setTipoItem(tipoItem);
        detalle.setCantidad(dtoEntrada.getCantidad());
        detalle.setPrecioUnitario(dtoEntrada.getPrecioUnitario());
        detalle.setSubtotal(subtotal);
        detalle.setProducto(producto);
        detalle.setPromocion(promocion);
        detalle.setPedido(pedido);

        detalle = detallePedidoService.save(detalle);

        DetallePedidoDto dto = mapDetalleToDto(detalle);
        return ResponseEntity.ok(dto);
    }

    // DELETE
    @DeleteMapping("/detallePedido/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        detallePedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT
    @PutMapping("/detallePedido/{id}")
    public ResponseEntity<DetallePedidoDto> update(@PathVariable int id,
                                                   @RequestBody DetallePedidoDto dtoEntrada) {

        Pedido pedido = dtoEntrada.getIdPedido() != 0
                ? pedidoService.getById(dtoEntrada.getIdPedido())
                : null;

        Producto producto = dtoEntrada.getIdProducto() != 0
                ? productoService.getById(dtoEntrada.getIdProducto())
                : null;

        Promocion promocion = dtoEntrada.getIdPromocion() != 0
                ? promocionService.getById(dtoEntrada.getIdPromocion())
                : null;

        TipoItemEnum tipoItem = dtoEntrada.getTipoItem() != 0
                ? TipoItemEnum.getById(dtoEntrada.getTipoItem())
                : null;

        double subtotal = dtoEntrada.getCantidad() * dtoEntrada.getPrecioUnitario();

        DetallePedido cambios = new DetallePedido();
        cambios.setTipoItem(tipoItem);
        cambios.setCantidad(dtoEntrada.getCantidad());
        cambios.setPrecioUnitario(dtoEntrada.getPrecioUnitario());
        cambios.setSubtotal(subtotal);
        cambios.setProducto(producto);
        cambios.setPromocion(promocion);
        cambios.setPedido(pedido);

        DetallePedido actualizado = detallePedidoService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        DetallePedidoDto dto = mapDetalleToDto(actualizado);
        return ResponseEntity.ok(dto);
    }

    private DetallePedidoDto mapDetalleToDto(DetallePedido d) {
        return DetallePedidoDto.builder()
                .id(d.getId() != null ? d.getId() : 0)
                .tipoItem(d.getTipoItem() != null ? d.getTipoItem().getIdTipo() : 0)
                .cantidad(d.getCantidad() != null ? d.getCantidad() : 0)
                .precioUnitario(d.getPrecioUnitario() != null ? d.getPrecioUnitario() : 0.0)
                .subtotal(d.getSubtotal() != null ? d.getSubtotal() : 0.0)
                .idProducto(d.getProducto() != null && d.getProducto().getId() != null ? d.getProducto().getId() : 0)
                .idPromocion(d.getPromocion() != null && d.getPromocion().getId() != null ? d.getPromocion().getId() : 0)
                .idPedido(d.getPedido() != null && d.getPedido().getId() != null ? d.getPedido().getId() : 0)
                .build();
    }
}
