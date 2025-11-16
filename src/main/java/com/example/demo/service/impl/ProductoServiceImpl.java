package com.example.demo.service.impl;

import com.example.demo.model.inventario.Producto;
import com.example.demo.repository.ProductoRepository;
import com.example.demo.service.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository productoRepository;

    @Override
    public List<Producto> getAll() {
        return productoRepository.findAll();
    }

    @Override
    public Producto getById(Integer id) {
        return productoRepository.findById(id).orElse(null);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepository.save(producto);
    }

    @Override
    public void delete(Integer id) {
        productoRepository.deleteById(id);
    }

    @Override
    public Producto update(Integer id, Producto producto) {
        Producto actual = productoRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setNombre(producto.getNombre());
        actual.setDescripcion(producto.getDescripcion());
        actual.setPrecio(producto.getPrecio());
        actual.setDisponible(producto.getDisponible());
        actual.setCategoria(producto.getCategoria());
        return productoRepository.save(actual);
    }
}
