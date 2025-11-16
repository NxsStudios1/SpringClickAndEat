package com.example.demo.repository;

import com.example.demo.model.inventario.PromocionProducto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromocionProductoRepository extends JpaRepository<PromocionProducto, Integer> {
}
