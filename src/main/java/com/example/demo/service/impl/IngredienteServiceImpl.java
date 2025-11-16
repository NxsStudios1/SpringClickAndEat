package com.example.demo.service.impl;

import com.example.demo.model.inventario.Ingrediente;
import com.example.demo.repository.IngredienteRepository;
import com.example.demo.service.IngredienteService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class IngredienteServiceImpl implements IngredienteService {

    private final IngredienteRepository ingredienteRepository;

    @Override
    public List<Ingrediente> getAll() {
        return ingredienteRepository.findAll();
    }

    @Override
    public Ingrediente getById(Integer id) {
        return ingredienteRepository.findById(id).orElse(null);
    }

    @Override
    public Ingrediente save(Ingrediente ingrediente) {
        return ingredienteRepository.save(ingrediente);
    }

    @Override
    public void delete(Integer id) {
        ingredienteRepository.deleteById(id);
    }

    @Override
    public Ingrediente update(Integer id, Ingrediente ingrediente) {
        Ingrediente actual = ingredienteRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setNombre(ingrediente.getNombre());
        actual.setDescripcion(ingrediente.getDescripcion());
        actual.setCantidadPorcion(ingrediente.getCantidadPorcion());
        actual.setUnidadMedida(ingrediente.getUnidadMedida());
        actual.setStockActual(ingrediente.getStockActual());
        actual.setPrecioUnitario(ingrediente.getPrecioUnitario());
        return ingredienteRepository.save(actual);
    }
}
