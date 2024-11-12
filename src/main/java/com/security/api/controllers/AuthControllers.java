package com.security.api.controllers;

import com.security.api.services.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/usuario")
public class AuthControllers {

    @Autowired
    private ServicioUsuario servicioUsuario;

    @GetMapping("/hello")
    public String hello(){
        return "Hola Spring";
    }

    @GetMapping("/lista")
    public ResponseEntity<?> helloSecurity(){
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(servicioUsuario.listUsers());
        }catch (Exception ex){
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(ex.getMessage());
        }
    }



}
