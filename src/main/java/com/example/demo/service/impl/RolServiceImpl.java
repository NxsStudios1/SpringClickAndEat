package com.example.demo.service.impl;

import com.example.demo.model.sesion.Rol;
import com.example.demo.repository.RolRepository;
import com.example.demo.service.RolService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class RolServiceImpl implements RolService
{
    private final RolRepository rolRepository;

    @Override
    public List<Rol> getAll()
    {
        return rolRepository.findAll();
    }

    @Override
    public Rol getById(Integer id)
    {
        return rolRepository.findById(id).orElse(null);
    }

    @Override
    public Rol save(Rol rol)
    {
        return rolRepository.save( rol );
    }

    @Override
    public void delete(Integer id)
    {
        rolRepository.deleteById(id);
    }

    @Override
    public Rol update(Integer id, Rol rol) {
        Rol actual = rolRepository.findById(id).orElse(null);
        if (actual == null) {
            return null;
        }
        actual.setTipo(rol.getTipo());
        return rolRepository.save(actual);
    }
}
