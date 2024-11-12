package com.security.api.controllers;

import com.security.api.models.LoginDto;
import com.security.api.models.Respuesta;
import com.security.api.security.UserDetailServiceImpl;
import com.security.api.services.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthJwt {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    private ServicioUsuario servicioUsuario;


    @PostMapping("/create")
    public ResponseEntity<?> registrar(@RequestBody LoginDto loginDto){
        servicioUsuario.create(loginDto.getName(),loginDto.getPassword());
        return ResponseEntity.ok(200);
    }

    @PostMapping("/jwt")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto){
    return ResponseEntity.status(HttpStatus.OK).body(userDetailService.loguin(loginDto));
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
