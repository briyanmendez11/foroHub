package com.bradmr.foroHub.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.bradmr.foroHub.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String secret;

    public String generarToken(Usuario usuario) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.create()
                    .withIssuer("foroHub")
                    .withSubject(usuario.getCorreoElectronico())
                    .withExpiresAt(fechaExpiracion())
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Error generando token JWT",e);
        }

    }

    private Instant fechaExpiracion() {
        return LocalDateTime.now()
                .plusHours(2)
                .toInstant(ZoneOffset.of("-04:00"));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            return JWT.require(algorithm)
                    .withIssuer("foroHub")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (Exception e){
            throw new RuntimeException("token JWT invalido o expirado",e);
        }
    }


}
