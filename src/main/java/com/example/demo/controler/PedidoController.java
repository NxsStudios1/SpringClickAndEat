package com.example.demo.controler;

import com.example.demo.dto.PedidoDto;
import com.example.demo.model.pedido.EstadoPedidoEnum;
import com.example.demo.model.pedido.Pedido;
import com.example.demo.model.sesion.Usuario;
import com.example.demo.service.PedidoService;
import com.example.demo.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/Gerdoc/api")
@RestController
@AllArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final UsuarioService usuarioService;

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

    // POST: crear pedido (fechaPedido se genera sola en la entidad)
    @PostMapping("/pedido")
    public ResponseEntity<PedidoDto> save(@RequestBody PedidoDto dtoEntrada) {

        Usuario cliente = dtoEntrada.getIdCliente() != 0 ? usuarioService.getById(dtoEntrada.getIdCliente()) : null;

        EstadoPedidoEnum estado = dtoEntrada.getEstado() != 0 ? EstadoPedidoEnum.getById(dtoEntrada.getEstado()) : null;

        Pedido pedido = new Pedido();
        pedido.setNumeroTicket(dtoEntrada.getNumeroTicket());
        pedido.setEstado(estado);
        pedido.setTotal(dtoEntrada.getTotal());
        pedido.setObservaciones(dtoEntrada.getObservaciones());
        pedido.setCliente(cliente);


        pedido = pedidoService.save(pedido);

        PedidoDto dto = mapPedidoToDto(pedido);
        return ResponseEntity.ok(dto);
    }

    // DELETE: eliminar pedido
    @DeleteMapping("/pedido/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pedidoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // PUT: actualizar pedido
    @PutMapping("/pedido/{id}")
    public ResponseEntity<PedidoDto> update(@PathVariable int id,
                                            @RequestBody PedidoDto dtoEntrada) {

        Usuario cliente = dtoEntrada.getIdCliente() != 0 ? usuarioService.getById(dtoEntrada.getIdCliente()) : null;

        EstadoPedidoEnum estado = dtoEntrada.getEstado() != 0 ? EstadoPedidoEnum.getById(dtoEntrada.getEstado()) : null;

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

    // ================== Mapeo entidad -> DTO ==================

    private PedidoDto mapPedidoToDto(Pedido pedido) {
        return PedidoDto.builder()
                .id(pedido.getId() != null ? pedido.getId() : 0)
                .numeroTicket(pedido.getNumeroTicket())
                .estado(pedido.getEstado() != null ? pedido.getEstado().getIdEstado() : 0)
                .total(pedido.getTotal() != null ? pedido.getTotal() : 0.0)
                .fechaPedido(pedido.getFechaPedido())
                .observaciones(pedido.getObservaciones())
                .idCliente(pedido.getCliente() != null && pedido.getCliente().getId() != null ? pedido.getCliente().getId() : 0)
                .build();
    }
}
