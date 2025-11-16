package com.example.demo.service.impl;

import com.example.demo.model.pedido.Pedido;
import com.example.demo.repository.PedidoRepository;
import com.example.demo.service.PedidoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository pedidoRepository;

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
}
