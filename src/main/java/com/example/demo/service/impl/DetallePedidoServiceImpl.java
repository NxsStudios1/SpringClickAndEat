package com.example.demo.service.impl;

import com.example.demo.model.pedido.DetallePedido;
import com.example.demo.repository.DetallePedidoRepository;
import com.example.demo.service.DetallePedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DetallePedidoServiceImpl implements DetallePedidoService {

    private final DetallePedidoRepository detallePedidoRepository;

    @Override
    public List<DetallePedido> getAll() {
        return detallePedidoRepository.findAll();
    }

    @Override
    public DetallePedido getById(Integer id) {
        return detallePedidoRepository.findById(id).orElse(null);
    }

    @Override
    public DetallePedido save(DetallePedido detallePedido) {
        return detallePedidoRepository.save(detallePedido);
    }

    @Override
    public void delete(Integer id) {
        detallePedidoRepository.deleteById(id);
    }

    @Override
    public DetallePedido update(Integer id, DetallePedido detallePedido) {
        DetallePedido actual = detallePedidoRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setTipoItem(detallePedido.getTipoItem());
        actual.setCantidad(detallePedido.getCantidad());
        actual.setPrecioUnitario(detallePedido.getPrecioUnitario());
        actual.setSubtotal(detallePedido.getSubtotal());
        actual.setProducto(detallePedido.getProducto());
        actual.setPromocion(detallePedido.getPromocion());
        actual.setPedido(detallePedido.getPedido());
        return detallePedidoRepository.save(actual);
    }
}
