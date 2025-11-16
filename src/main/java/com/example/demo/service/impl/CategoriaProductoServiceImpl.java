package com.example.demo.service.impl;

import com.example.demo.model.inventario.CategoriaProducto;
import com.example.demo.repository.CategoriaProductoRepository;
import com.example.demo.service.CategoriaProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {

    private final CategoriaProductoRepository categoriaProductoRepository;

    @Override
    public List<CategoriaProducto> getAll() {
        return categoriaProductoRepository.findAll();
    }

    @Override
    public CategoriaProducto getById(Integer id) {
        return categoriaProductoRepository.findById(id).orElse(null);
    }

    @Override
    public CategoriaProducto save(CategoriaProducto categoriaProducto) {
        return categoriaProductoRepository.save(categoriaProducto);
    }

    @Override
    public void delete(Integer id) {
        categoriaProductoRepository.deleteById(id);
    }

    @Override
    public CategoriaProducto update(Integer id, CategoriaProducto categoriaProducto) {
        CategoriaProducto actual = categoriaProductoRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setNombre(categoriaProducto.getNombre());
        return categoriaProductoRepository.save(actual);
    }
}
