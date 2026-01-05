// src/main/java/com/example/demo/service/impl/PedidoServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.model.inventario.Ingrediente;
import com.example.demo.model.inventario.Producto;
import com.example.demo.model.inventario.ProductoIngrediente;
import com.example.demo.model.inventario.Promocion;
import com.example.demo.model.inventario.PromocionProducto;
import com.example.demo.model.pedido.DetallePedido;
import com.example.demo.model.pedido.Pedido;
import com.example.demo.model.pedido.TipoItemEnum;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.service.IngredienteService;
import com.example.demo.service.PedidoService;
import com.example.demo.service.ProductoIngredienteService;
import com.example.demo.service.ProductoService;
import com.example.demo.service.PromocionProductoService;
import com.example.demo.service.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

    private final ProductoService productoService;
    private final PromocionService promocionService;
    private final ProductoIngredienteService productoIngredienteService;
    private final PromocionProductoService promocionProductoService;
    private final IngredienteService ingredienteService;

    @Override
    public List<Pedido> getAll() {
        return pedidoRepository.findAll();
    }

    @Override
    public Pedido getById(Integer id) {
        return pedidoRepository.findById(id).orElse(null);
    }

    @Override
    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    @Override
    public void delete(Integer id) {
        pedidoRepository.deleteById(id);
    }

    @Override
    public Pedido update(Integer id, Pedido pedido) {
        Pedido actual = pedidoRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setNumeroTicket(pedido.getNumeroTicket());
        actual.setEstado(pedido.getEstado());
        actual.setTotal(pedido.getTotal());
        actual.setObservaciones(pedido.getObservaciones());
        actual.setCliente(pedido.getCliente());
        return pedidoRepository.save(actual);
    }

    @Override
    @Transactional
    public Pedido guardarPedidoYDescontarStock(Pedido pedido) {

        Pedido guardado = pedidoRepository.save(pedido);

        List<DetallePedido> detalles = guardado.getDetalles();
        if (detalles == null || detalles.isEmpty()) {
            return guardado;
        }

        for (DetallePedido det : detalles) {
            if (det.getTipoItem() == TipoItemEnum.PRODUCTO) {
                Producto prod = productoService.getById(det.getProducto().getId());
                descontarStockProducto(prod, det.getCantidad());
            } else if (det.getTipoItem() == TipoItemEnum.PROMOCION) {
                Promocion promo = promocionService.getById(det.getPromocion().getId());
                descontarStockPromocion(promo, det.getCantidad());
            }
        }

        return guardado;
    }

    private void descontarStockProducto(Producto producto, int cantidadPedido) {

        List<ProductoIngrediente> lista =
                productoIngredienteService.findByProducto(producto.getId());

        if (lista == null || lista.isEmpty()) {
            return;
        }

        // validación
        for (ProductoIngrediente pi : lista) {
            Ingrediente ing = pi.getIngrediente();

            double requerido = pi.getCantidadIngrediente() * cantidadPedido;
            double stockActual = ing.getStockActual() != null ? ing.getStockActual() : 0.0;

            if (stockActual < requerido) {
                throw new IllegalStateException(
                        "No se puede realizar el producto: " + producto.getNombre() + " por el momento :) "
                );
            }
        }

        // descuento real
        for (ProductoIngrediente pi : lista) {
            Ingrediente ing = pi.getIngrediente();
            double requerido = pi.getCantidadIngrediente() * cantidadPedido;
            double stockActual = ing.getStockActual() != null ? ing.getStockActual() : 0.0;

            ing.setStockActual(stockActual - requerido);
            ingredienteService.save(ing);
        }
    }

    private void descontarStockPromocion(Promocion promocion, int cantidadPedidoPromo) {

        List<PromocionProducto> rels =
                promocionProductoService.findByPromocion(promocion.getId());

        if (rels == null || rels.isEmpty()) {
            return;
        }

        // validar primero
        for (PromocionProducto pp : rels) {
            Producto producto = pp.getProducto();

            int cantidadProductoTotal =
                    (int) Math.round(pp.getCantidadProducto() * cantidadPedidoPromo);

            List<ProductoIngrediente> listaPI =
                    productoIngredienteService.findByProducto(producto.getId());

            for (ProductoIngrediente pi : listaPI) {
                Ingrediente ing = pi.getIngrediente();
                double requerido = pi.getCantidadIngrediente() * cantidadProductoTotal;
                double stockActual = ing.getStockActual() != null ? ing.getStockActual() : 0.0;

                if (stockActual < requerido) {
                    throw new IllegalStateException(
                            "No se puede realizar la promocion: " + promocion.getNombre() + " por el momento :) "
                    );
                }
            }
        }

        // descontar
        for (PromocionProducto pp : rels) {
            Producto producto = pp.getProducto();
            int cantidadProductoTotal =
                    (int) Math.round(pp.getCantidadProducto() * cantidadPedidoPromo);

            List<ProductoIngrediente> listaPI =
                    productoIngredienteService.findByProducto(producto.getId());

            for (ProductoIngrediente pi : listaPI) {
                Ingrediente ing = pi.getIngrediente();
                double requerido = pi.getCantidadIngrediente() * cantidadProductoTotal;
                double stockActual = ing.getStockActual() != null ? ing.getStockActual() : 0.0;

                ing.setStockActual(stockActual - requerido);
                ingredienteService.save(ing);
            }
        }
    }
}
