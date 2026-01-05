package com.example.demo.repository;

import com.example.demo.model.inventario.PromocionProducto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromocionProductoRepository extends JpaRepository<PromocionProducto, Integer> {

    long countByPromocionId(Integer idPromocion);

    long countByProducto_IdAndPromocion_ActivoTrue(Integer idProducto);

    List<PromocionProducto> findByPromocion_Id(Integer idPromocion);

}
