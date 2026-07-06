package br.com.ifba.prg04deskflow.comentario.controller;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.comentario.dto.ComentarioGetResponseDTO;
import br.com.ifba.prg04deskflow.comentario.dto.ComentarioPostRequestDTO;
import br.com.ifba.prg04deskflow.comentario.model.Comentario;
import br.com.ifba.prg04deskflow.comentario.service.ComentarioIService;
import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentarios")
@RequiredArgsConstructor
public class ComentarioController {

    private final ComentarioIService comentarioService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<ComentarioGetResponseDTO> save(@RequestBody @Valid ComentarioPostRequestDTO dto) {
        Comentario comentario = objectMapperUtil.map(dto, Comentario.class);

        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        comentario.setUsuario(usuario);

        Chamado chamado = new Chamado();
        chamado.setId(dto.getChamadoId());
        comentario.setChamado(chamado);

        Comentario salvo = comentarioService.save(comentario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, ComentarioGetResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<Page<ComentarioGetResponseDTO>> findAll(Pageable pageable) {
        Page<ComentarioGetResponseDTO> pagina = comentarioService.findAll(pageable)
                .map(c -> objectMapperUtil.map(c, ComentarioGetResponseDTO.class));
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComentarioGetResponseDTO> findById(@PathVariable Long id) {
        Comentario comentario = comentarioService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(comentario, ComentarioGetResponseDTO.class));
    }

    @GetMapping("/chamado/{chamadoId}")
    public ResponseEntity<Page<ComentarioGetResponseDTO>> findByChamadoId(
            @PathVariable Long chamadoId, Pageable pageable) {
        Page<ComentarioGetResponseDTO> pagina = comentarioService.findByChamadoId(chamadoId, pageable)
                .map(c -> objectMapperUtil.map(c, ComentarioGetResponseDTO.class));
        return ResponseEntity.ok(pagina);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        comentarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}