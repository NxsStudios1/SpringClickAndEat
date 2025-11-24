package com.example.demo.service.impl;

import com.example.demo.model.inventario.ProductoIngrediente;
import com.example.demo.repository.ProductoIngredienteRepository;
import com.example.demo.service.ProductoIngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductoIngredienteServiceImpl implements ProductoIngredienteService {

    private final ProductoIngredienteRepository productoIngredienteRepository;

    @Override
    public List<ProductoIngrediente> getAll() {
        return productoIngredienteRepository.findAll();
    }

    @Override
    public ProductoIngrediente getById(Integer id) {
        return productoIngredienteRepository.findById(id).orElse(null);
    }

    @Override
    public ProductoIngrediente save(ProductoIngrediente productoIngrediente) {
        return productoIngredienteRepository.save(productoIngrediente);
    }

    @Override
    public void delete(Integer id) {
        productoIngredienteRepository.deleteById(id);
    }

    @Override
    public ProductoIngrediente update(Integer id, ProductoIngrediente productoIngrediente) {
        ProductoIngrediente actual = productoIngredienteRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setCantidadIngrediente(productoIngrediente.getCantidadIngrediente());
        actual.setProducto(productoIngrediente.getProducto());
        actual.setIngrediente(productoIngrediente.getIngrediente());
        return productoIngredienteRepository.save(actual);
    }
}
