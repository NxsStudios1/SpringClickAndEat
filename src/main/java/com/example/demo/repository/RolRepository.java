package com.example.demo.repository;

import com.example.demo.model.sesion.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>
{    Rol findByTipo(com.example.demo.model.sesion.RolEnum tipo);

}
