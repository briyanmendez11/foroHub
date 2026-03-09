package com.bradmr.foroHub.controller;

import com.bradmr.foroHub.domain.usuario.DatosAutenticacionUsuario;
import com.bradmr.foroHub.domain.usuario.Usuario;
import com.bradmr.foroHub.infra.security.DatosJWTToken;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AutenticationController {

    private final AuthenticationManager authenticationManager;


    public AutenticationController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    public DatosJWTToken autenticar(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        var authToken = new UsernamePasswordAuthenticationToken(
                datos.correoElectronico(),
                datos.contrasena()
        );
        var autenticacion = authenticationManager.authenticate(authToken);

        return new DatosJWTToken("token-temporal");
    }

}
