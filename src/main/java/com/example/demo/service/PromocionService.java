package com.example.demo.service;

import com.example.demo.model.inventario.Promocion;

import java.util.List;

public interface PromocionService {

    List<Promocion> getAll();

    Promocion getById(Integer id);

    Promocion save(Promocion promocion);

    void delete(Integer id);

    Promocion update(Integer id, Promocion promocion);
}
