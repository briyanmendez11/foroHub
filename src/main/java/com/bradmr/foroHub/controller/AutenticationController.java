package com.bradmr.foroHub.controller;

import com.bradmr.foroHub.domain.usuario.DatosAutenticacionUsuario;
import com.bradmr.foroHub.domain.usuario.Usuario;
import com.bradmr.foroHub.infra.security.DatosJWTToken;
import com.bradmr.foroHub.infra.security.TokenService;
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

    private final TokenService tokenService;


    public AutenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public DatosJWTToken autenticar(@RequestBody @Valid DatosAutenticacionUsuario datos) {
        var authToken = new UsernamePasswordAuthenticationToken(
                datos.correoElectronico(),
                datos.contrasena()
        );
        var autenticacion = authenticationManager.authenticate(authToken);

        var usuario = (Usuario) autenticacion.getPrincipal();

        var jwtToken = tokenService.generarToken(usuario);

        return new DatosJWTToken(jwtToken);
    }

}
