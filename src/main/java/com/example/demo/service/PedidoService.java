package com.example.demo.service;

import com.example.demo.model.pedido.Pedido;

import java.util.List;

public interface PedidoService {

    List<Pedido> getAll();

    Pedido getById(Integer id);

    Pedido save(Pedido pedido);

    void delete(Integer id);

    Pedido update(Integer id, Pedido pedido);
}
