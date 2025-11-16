package com.example.demo.service;


import com.example.demo.model.inventario.CategoriaProducto;

import java.util.List;

public interface CategoriaProductoService {

    List<CategoriaProducto> getAll();

    CategoriaProducto getById(Integer id);

    CategoriaProducto save(CategoriaProducto categoriaProducto);

    void delete(Integer id);

    CategoriaProducto update(Integer id, CategoriaProducto categoriaProducto);
}
