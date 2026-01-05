package com.example.demo.service.impl;

import com.example.demo.model.inventario.PromocionProducto;
import com.example.demo.repository.PromocionProductoRepository;
import com.example.demo.service.PromocionProductoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PromocionProductoServiceImpl implements PromocionProductoService {

    private final PromocionProductoRepository promocionProductoRepository;

    @Override
    public List<PromocionProducto> getAll() {
        return promocionProductoRepository.findAll();
    }

    @Override
    public PromocionProducto getById(Integer id) {
        return promocionProductoRepository.findById(id).orElse(null);
    }

    @Override
    public PromocionProducto save(PromocionProducto promocionProducto) {
        return promocionProductoRepository.save(promocionProducto);
    }

    @Override
    public void delete(Integer id) {
        promocionProductoRepository.deleteById(id);
    }

    @Override
    public PromocionProducto update(Integer id, PromocionProducto promocionProducto) {
        PromocionProducto actual = promocionProductoRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setCantidadProducto(promocionProducto.getCantidadProducto());
        actual.setProducto(promocionProducto.getProducto());
        actual.setPromocion(promocionProducto.getPromocion());
        return promocionProductoRepository.save(actual);
    }


    @Override
    public List<PromocionProducto> findByPromocion(Integer idPromocion) {
        return promocionProductoRepository.findByPromocion_Id(idPromocion);
    }
}
