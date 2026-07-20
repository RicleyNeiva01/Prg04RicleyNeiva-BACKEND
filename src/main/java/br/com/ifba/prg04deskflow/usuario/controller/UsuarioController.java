package br.com.ifba.prg04deskflow.usuario.controller;

import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioGetResponseDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPostRequestDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPutRequestDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioService;
    private final ObjectMapperUtil objectMapperUtil;

    // Público — qualquer um pode se cadastrar
    @PostMapping
    public ResponseEntity<UsuarioGetResponseDTO> save(@RequestBody @Valid UsuarioPostRequestDTO dto) {
        Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
        Usuario salvo = usuarioService.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, UsuarioGetResponseDTO.class));
    }

    // Só ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UsuarioGetResponseDTO>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "false") boolean mostrarInativos,
            Pageable pageable) {

        Page<Usuario> usuarios;
        if (nome != null && !nome.isBlank()) {
            usuarios = usuarioService.findByNome(nome, mostrarInativos, pageable);
        } else {
            usuarios = usuarioService.findAll(mostrarInativos, pageable);
        }

        return ResponseEntity.ok(usuarios.map(u -> objectMapperUtil.map(u, UsuarioGetResponseDTO.class)));
    }

    // Só ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioGetResponseDTO> findById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class));
    }

    // Só ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioGetResponseDTO> update(@PathVariable Long id,
                                                        @RequestBody @Valid UsuarioPutRequestDTO dto) {
        Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
        Usuario atualizado = usuarioService.update(id, usuario);
        return ResponseEntity.ok(objectMapperUtil.map(atualizado, UsuarioGetResponseDTO.class));
    }

    // Só ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Só ADMIN
    @PutMapping("/{id}/reativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        usuarioService.reativar(id);
        return ResponseEntity.noContent().build();
    }

}