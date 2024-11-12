package com.security.api.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Respuesta{

    private String username;
    private String message;
    private String jwt;
    private Boolean status;

}
