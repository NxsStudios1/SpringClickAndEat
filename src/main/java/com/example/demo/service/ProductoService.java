package com.example.demo.service;

import com.example.demo.model.inventario.Producto;

import java.util.List;

public interface ProductoService {

    List<Producto> getAll();

    Producto getById(Integer id);

    Producto save(Producto producto);

    void delete(Integer id);

    Producto update(Integer id, Producto producto);
}
