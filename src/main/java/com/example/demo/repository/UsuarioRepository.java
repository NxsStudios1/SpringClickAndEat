package com.example.demo.repository;

import com.example.demo.model.sesion.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByTelefono(String telefono);
    boolean existsByTelefono(String telefono);

}
