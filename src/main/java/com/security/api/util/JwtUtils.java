package com.security.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${security.private.key}")
    private String privateKey;

    @Value("${security.private.users}")
    private String privateUser;

    public String createToken(Authentication authentication){

        Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

        String userName = authentication.getPrincipal().toString();

        String autorities = authentication.getAuthorities()
             .stream()
             .map(GrantedAuthority::getAuthority)
             .collect(Collectors.joining(","));

        String JwtToken = JWT.create()
                .withIssuer(privateUser)
                .withSubject(userName)
                .withClaim("authorities", autorities)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1800000))
                .withJWTId(UUID.randomUUID().toString())
                .withNotBefore(new Date(System.currentTimeMillis()))
                .sign(algorithm);

        return JwtToken;
    }

    public DecodedJWT validateJWT(String token){


        try{
            Algorithm algorithm = Algorithm.HMAC256(this.privateKey);

            JWTVerifier jwtVerifier = JWT.require(algorithm)
                    .withIssuer(this.privateUser)
                    .build();

            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return decodedJWT;
        }
        catch (JWTVerificationException exception){
            throw new JWTVerificationException("El token es invalido");
        }

    }

    public String extraerUsuario(DecodedJWT decodedJWT){
        return decodedJWT.getSubject().toString();
    }

    public Claim obtenerUnClaim(DecodedJWT decodedJWT, String claimName){
        return decodedJWT.getClaim(claimName);
    }

    public Map<String, Claim> retornarAllClaim(DecodedJWT decodedJWT){
        return decodedJWT.getClaims();
    }
}
