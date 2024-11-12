package com.security.api.services;

import com.security.api.models.Usuario;
import com.security.api.repository.RepositorioUsuario;
import com.security.api.roles.Rol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioUsuario {

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Transactional
    public void create(String nombre, String password){
        Usuario usuario = new Usuario();
        usuario.setName(nombre);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        repositorioUsuario.save(usuario);
    }

    public List<Usuario> listUsers(){
        List<Usuario> usuarios = (List<Usuario>) repositorioUsuario.findAll();
        return usuarios;
    }

}
