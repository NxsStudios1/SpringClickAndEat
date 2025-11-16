package com.example.demo.service;

import com.example.demo.model.inventario.Ingrediente;

import java.util.List;

public interface IngredienteService {

    List<Ingrediente> getAll();

    Ingrediente getById(Integer id);

    Ingrediente save(Ingrediente ingrediente);

    void delete(Integer id);

    Ingrediente update(Integer id, Ingrediente ingrediente);
}
