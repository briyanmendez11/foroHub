package com.bradmr.foroHub.controller;

import com.bradmr.foroHub.domain.topico.DatosDetalleTopico;
import com.bradmr.foroHub.domain.topico.DatosListadoTopico;
import com.bradmr.foroHub.domain.topico.Topico;
import com.bradmr.foroHub.domain.topico.TopicoRepository;
import com.bradmr.foroHub.domain.usuario.Usuario;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/topicos")
public class TopicoController {

    private TopicoRepository topicoRepository;

    public TopicoController(TopicoRepository topicoRepository) {
        this.topicoRepository = topicoRepository;
    }

    @PostMapping
    public DatosDetalleTopico registrar(
            @RequestBody @Valid DatosDetalleTopico datos,
            @AuthenticationPrincipal Usuario usuario
    ){
        Topico topico = new Topico(
                null,
                datos.titulo(),
                datos.mensaje(),
                LocalDateTime.now(),
                "ABIERTO",
                usuario,
                datos.curso()
        );
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

    @GetMapping
    public Page<DatosListadoTopico> listar(
            @PageableDefault(size = 10, sort = "fechaCreacion")
            Pageable paginacion) {

        return topicoRepository.findAll(paginacion)
                .map(t -> new DatosListadoTopico(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensaje(),
                        t.getFechaCreacion(),
                        t.getCurso()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosDetalleTopico> detallar(@PathVariable Long id) {

        var topico = topicoRepository.findById(id);

        if (topico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var t = topico.get();

        var datos = new DatosDetalleTopico(t);

        return ResponseEntity.ok(datos);
    }
}
