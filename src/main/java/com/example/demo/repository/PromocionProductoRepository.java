package com.example.demo.repository;

import com.example.demo.model.inventario.PromocionProducto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromocionProductoRepository extends JpaRepository<PromocionProducto, Integer> {

    long countByPromocionId(Integer idPromocion);

    long countByProducto_IdAndPromocion_ActivoTrue(Integer idProducto);
}
