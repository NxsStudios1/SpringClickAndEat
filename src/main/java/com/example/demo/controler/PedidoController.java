// src/main/java/com/example/demo/controler/PedidoController.java
package com.example.demo.controler;

import com.example.demo.dto.DetallePedidoDto;
import com.example.demo.dto.PedidoDto;
import com.example.demo.model.inventario.Producto;
import com.example.demo.model.inventario.Promocion;
import com.example.demo.model.pedido.DetallePedido;
import com.example.demo.model.pedido.EstadoPedidoEnum;
import com.example.demo.model.pedido.Pedido;
import com.example.demo.model.pedido.TipoItemEnum;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.PromocionService;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;
    private final ProductoService productoService;
    private final PromocionService promocionService;

    // GET: todos los pedidos
    @GetMapping("/pedido")
    public ResponseEntity<List<PedidoDto>> lista() {
        List<Pedido> pedidos = pedidoService.getAll();
        if (pedidos == null || pedidos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PedidoDto> dtos = pedidos.stream()
                .map(this::mapPedidoToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    // GET: pedido por id
    @GetMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto> getById(@PathVariable int id) {
        Pedido pedido = pedidoService.getById(id);
        if (pedido == null) {
            return ResponseEntity.notFound().build();
        }

        PedidoDto dto = mapPedidoToDto(pedido);
        return ResponseEntity.ok(dto);
    }

    // POST: crear pedido (cliente)
    @PostMapping("/pedido")
    public ResponseEntity<?> save(@RequestBody PedidoDto dtoEntrada) {

        try {
            Usuario cliente = dtoEntrada.getIdCliente() != 0
                    ? usuarioService.getById(dtoEntrada.getIdCliente())
                    : null;

            EstadoPedidoEnum estado = dtoEntrada.getEstado() != 0
                    ? EstadoPedidoEnum.getById(dtoEntrada.getEstado())
                    : null;

            Pedido pedido = new Pedido();
            pedido.setNumeroTicket(dtoEntrada.getNumeroTicket());
            pedido.setEstado(estado);
            pedido.setTotal(dtoEntrada.getTotal());
            pedido.setObservaciones(dtoEntrada.getObservaciones());
            pedido.setCliente(cliente);

            List<DetallePedido> detalles = new ArrayList<>();

            if (dtoEntrada.getDetalles() != null) {
                for (DetallePedidoDto dDto : dtoEntrada.getDetalles()) {

                    DetallePedido det = new DetallePedido();
                    det.setPedido(pedido);
                    det.setCantidad(dDto.getCantidad());
                    det.setPrecioUnitario(dDto.getPrecioUnitario());
                    det.setSubtotal(dDto.getCantidad() * dDto.getPrecioUnitario());
                    det.setTipoItem(TipoItemEnum.getById(dDto.getTipoItem()));

                    if (dDto.getIdProducto() != 0) {
                        Producto prod = productoService.getById(dDto.getIdProducto());
                        det.setProducto(prod);
                    }

                    if (dDto.getIdPromocion() != 0) {
                        Promocion promo = promocionService.getById(dDto.getIdPromocion());
                        det.setPromocion(promo);
                    }

                    detalles.add(det);
                }
            }

            pedido.setDetalles(detalles);

            // Guardar y descontar stock
            Pedido guardado = pedidoService.guardarPedidoYDescontarStock(pedido);

            PedidoDto dto = mapPedidoToDto(guardado);
            return ResponseEntity.ok(dto);

        } catch (IllegalStateException e) {
            // sin stock suficiente
            return ResponseEntity.status(409).body(e.getMessage());
        }
    }

    // DELETE: eliminar pedido
    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar pedido (solo cabecera, no detalles)
    @PutMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable int id,
                                            @RequestBody PedidoDto dtoEntrada) {

        Usuario cliente = dtoEntrada.getIdCliente() != 0
                ? usuarioService.getById(dtoEntrada.getIdCliente())
                : null;

        EstadoPedidoEnum estado = dtoEntrada.getEstado() != 0
                ? EstadoPedidoEnum.getById(dtoEntrada.getEstado())
                : null;

        Pedido cambios = new Pedido();
        cambios.setNumeroTicket(dtoEntrada.getNumeroTicket());
        cambios.setEstado(estado);
        cambios.setTotal(dtoEntrada.getTotal());
        cambios.setObservaciones(dtoEntrada.getObservaciones());
        cambios.setCliente(cliente);

        Pedido actualizado = pedidoService.update(id, cambios);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }

        PedidoDto dto = mapPedidoToDto(actualizado);
        return ResponseEntity.ok(dto);
    }

    // ================== Mapeos ==================

    private PedidoDto mapPedidoToDto(Pedido pedido) {

        List<DetallePedidoDto> detalleDtos = null;
        if (pedido.getDetalles() != null) {
            detalleDtos = pedido.getDetalles().stream()
                    .map(this::mapDetalleToDto)
                    .collect(Collectors.toList());
        }

        return PedidoDto.builder()
                .id(pedido.getId() != null ? pedido.getId() : 0)
                .numeroTicket(pedido.getNumeroTicket())
                .estado(pedido.getEstado() != null ? pedido.getEstado().getIdEstado() : 0)
                .total(pedido.getTotal() != null ? pedido.getTotal() : 0.0)
                .fechaPedido(pedido.getFechaPedido())
                .observaciones(pedido.getObservaciones())
                .idCliente(pedido.getCliente() != null && pedido.getCliente().getId() != null
                        ? pedido.getCliente().getId()
                        : 0)
                .nombreCliente(pedido.getCliente() != null ? pedido.getCliente().getNombre() : null)
                .detalles(detalleDtos)
                .build();
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
                .idPedido(pedidoIdSafe(d))
                .build();
    }

    private int pedidoIdSafe(DetallePedido d) {
        return d.getPedido() != null && d.getPedido().getId() != null
                ? d.getPedido().getId()
                : 0;
    }
}
