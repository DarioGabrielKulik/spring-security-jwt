package com.security.api.security;

import com.security.api.models.LoginDto;
import com.security.api.models.Respuesta;
import com.security.api.models.Usuario;
import com.security.api.repository.RepositorioUsuario;
import com.security.api.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = repositorioUsuario.findByName(username).get();

        if (usuario == null){
            throw new UsernameNotFoundException("El usuario" + username + " no se encontro" );
        }

        List<GrantedAuthority> permisos = new ArrayList<>();
        GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString());
        permisos.add(p);

        return new User(usuario.getName(), usuario.getPassword(), permisos);
    }

    public Respuesta loguin(LoginDto loginDto){


        Authentication authentication = this.authentica(loginDto.getName(), loginDto.getPassword());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String acceso = jwtUtils.createToken(authentication);

        Respuesta respuesta = new Respuesta(loginDto.getName(), "Usuario Logueado", acceso, true);

        return respuesta;
    }

    public Authentication authentica(String userName, String password){

        UserDetails userDetails = loadUserByUsername(userName);

        if(userDetails == null){
            throw new BadCredentialsException("Contraseña o Usuario incorrecto");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())){
            throw new BadCredentialsException("La contraseña es invalida");
        }

        return new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
    }

}
