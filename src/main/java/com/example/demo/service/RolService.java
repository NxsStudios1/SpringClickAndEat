package com.example.demo.service;

import com.example.demo.model.sesion.Rol;

import java.util.List;

public interface RolService
{
    List<Rol> getAll( );
    Rol getById(Integer id);
    Rol save(Rol rol);
    void delete(Integer id);
    Rol update(Integer id, Rol rol);
}
