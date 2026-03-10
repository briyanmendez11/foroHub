package com.bradmr.foroHub.infra.security;

import com.bradmr.foroHub.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private TokenService tokenService;
    private UsuarioRepository usuarioRepository;


    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }




    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        var token = recuperarToken(request);

        if (token != null) {
            var subject = tokenService.getSubject(token);

            var usuario = usuarioRepository.findByCorreoElectronico(subject);
            if (usuario.isPresent()){
                var authentication = new UsernamePasswordAuthenticationToken(
                        usuario.get(),
                        null,
                        usuario.get().getAuthorities()

                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }
    private String recuperarToken(HttpServletRequest request) {
        var autHeader = request.getHeader("Authorization");
        if (autHeader == null){
            return null;
        }
        return autHeader.replace("Bearer ", "");
    }
}
