package com.example.demo.service;

import com.example.demo.model.pedido.DetallePedido;

import java.util.List;

public interface DetallePedidoService {

    List<DetallePedido> getAll();

    DetallePedido getById(Integer id);

    DetallePedido save(DetallePedido detallePedido);

    void delete(Integer id);

    DetallePedido update(Integer id, DetallePedido detallePedido);

    List<DetallePedido> findByPedidoId ( Integer idPedido);
}
