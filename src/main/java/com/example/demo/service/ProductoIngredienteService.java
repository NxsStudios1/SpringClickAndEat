package com.example.demo.service;

import com.example.demo.model.inventario.ProductoIngrediente;

import java.util.List;

public interface ProductoIngredienteService {

    List<ProductoIngrediente> getAll();

    ProductoIngrediente getById(Integer id);

    ProductoIngrediente save(ProductoIngrediente productoIngrediente);

    void delete(Integer id);

    ProductoIngrediente update(Integer id, ProductoIngrediente productoIngrediente);
}
