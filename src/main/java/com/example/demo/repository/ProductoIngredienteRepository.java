package com.example.demo.repository;

import com.example.demo.model.inventario.ProductoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoIngredienteRepository extends JpaRepository<ProductoIngrediente, Integer> {
}
