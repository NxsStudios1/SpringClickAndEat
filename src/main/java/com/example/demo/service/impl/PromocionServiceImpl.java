package com.example.demo.service.impl;

import com.example.demo.model.inventario.Promocion;
import com.example.demo.repository.PromocionRepository;
import com.example.demo.service.PromocionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class PromocionServiceImpl implements PromocionService {

    private final PromocionRepository promocionRepository;

    @Override
    public List<Promocion> getAll() {
        return promocionRepository.findAll();
    }

    @Override
    public Promocion getById(Integer id) {
        return promocionRepository.findById(id).orElse(null);
    }

    @Override
    public Promocion save(Promocion promocion) {
        return promocionRepository.save(promocion);
    }

    @Override
    public void delete(Integer id) {
        promocionRepository.deleteById(id);
    }

    @Override
    public Promocion update(Integer id, Promocion promocion) {
        Promocion actual = promocionRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setNombre(promocion.getNombre());
        actual.setDescripcion(promocion.getDescripcion());
        actual.setFechaInicio(promocion.getFechaInicio());
        actual.setFechaFin(promocion.getFechaFin());
        actual.setPrecioTotalConDescuento(promocion.getPrecioTotalConDescuento());
        actual.setActivo(promocion.getActivo());
        return promocionRepository.save(actual);
    }
}
