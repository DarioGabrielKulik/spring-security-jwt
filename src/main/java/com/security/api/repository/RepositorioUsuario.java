package com.security.api.repository;

import com.security.api.models.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RepositorioUsuario extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByName(String name);
}
