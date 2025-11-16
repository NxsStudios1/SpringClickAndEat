package com.example.demo.service;

import com.example.demo.model.inventario.PromocionProducto;

import java.util.List;

public interface PromocionProductoService {

    List<PromocionProducto> getAll();

    PromocionProducto getById(Integer id);

    PromocionProducto save(PromocionProducto promocionProducto);

    void delete(Integer id);

    PromocionProducto update(Integer id, PromocionProducto promocionProducto);
}
