package com.example.demo.repository;

import com.example.demo.model.inventario.ProductoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoIngredienteRepository
        extends JpaRepository<ProductoIngrediente, Integer> {

    List<ProductoIngrediente> findByProducto_Id(Integer idProducto);

    long countByProductoId(Integer idProducto);
}
