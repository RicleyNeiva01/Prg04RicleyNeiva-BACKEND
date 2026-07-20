package br.com.ifba.prg04deskflow.chamado.controller;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.chamado.dto.ChamadoGetResponseDTO;
import br.com.ifba.prg04deskflow.chamado.dto.ChamadoPostRequestDTO;
import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.chamado.service.ChamadoIService;
import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chamados")
@RequiredArgsConstructor
public class ChamadoController {

    private final ChamadoIService chamadoService;
    private final ObjectMapperUtil objectMapperUtil;

    // Criar chamado (POST /chamados)
    @PostMapping
    public ResponseEntity<ChamadoGetResponseDTO> save(@RequestBody @Valid ChamadoPostRequestDTO dto) {
        Chamado chamado = objectMapperUtil.map(dto, Chamado.class);

        // monta Usuario com ID
        Usuario usuario = new Usuario();
        usuario.setId(dto.getUsuarioId());
        chamado.setUsuario(usuario);

        // monta Categoria com ID
        Categoria categoria = new Categoria();
        categoria.setId(dto.getCategoriaId());
        chamado.setCategoria(categoria);

        Chamado salvo = chamadoService.save(chamado);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, ChamadoGetResponseDTO.class));
    }

    // Listar chamados com filtros opcionais de Título e Status (GET /chamados)
    @GetMapping
    public ResponseEntity<Page<ChamadoGetResponseDTO>> findAll(
            @RequestParam(required = false) StatusChamado status,
            @RequestParam(required = false) String titulo,
            Pageable pageable) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        String perfil = auth.getAuthorities().iterator().next().getAuthority();

        Page<Chamado> chamados = chamadoService.findChamadosByPerfil(email, perfil, status, titulo, pageable);

        return ResponseEntity.ok(chamados.map(c -> objectMapperUtil.map(c, ChamadoGetResponseDTO.class)));
    }

    // Buscar chamado por ID (GET /chamados/{id})
    @GetMapping("/{id}")
    public ResponseEntity<ChamadoGetResponseDTO> findById(@PathVariable Long id) {
        Chamado chamado = chamadoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(chamado, ChamadoGetResponseDTO.class));
    }

    // Atualizar dados gerais do chamado (PUT /chamados/{id})
    @PutMapping("/{id}")
    public ResponseEntity<ChamadoGetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid ChamadoPostRequestDTO dto) {
        Chamado chamado = objectMapperUtil.map(dto, Chamado.class);

        // categoria (opcional no update, mas se vier precisa montar)
        if (dto.getCategoriaId() != null) {
            Categoria categoria = new Categoria();
            categoria.setId(dto.getCategoriaId());
            chamado.setCategoria(categoria);
        }

        Chamado atualizado = chamadoService.update(id, chamado);

        return ResponseEntity.ok(
                objectMapperUtil.map(atualizado, ChamadoGetResponseDTO.class)
        );
    }

    // Atualizar apenas o status do chamado (PATCH /chamados/{id}/status)
    @PatchMapping("/{id}/status")
    public ResponseEntity<ChamadoGetResponseDTO> updateStatus(
            @PathVariable Long id,
            @RequestParam StatusChamado status) {
        Chamado atualizado = chamadoService.updateStatus(id, status);
        return ResponseEntity.ok(objectMapperUtil.map(atualizado, ChamadoGetResponseDTO.class));
    }

    // Deletar chamado (DELETE /chamados/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        chamadoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Atribuir um técnico ao chamado (PATCH /chamados/{id}/tecnico)
    @PatchMapping("/{id}/tecnico")
    public ResponseEntity<ChamadoGetResponseDTO> atribuirTecnico(
            @PathVariable Long id,
            @RequestParam Long tecnicoId) {

        Chamado atualizado = chamadoService.atribuirTecnico(id, tecnicoId);

        return ResponseEntity.ok(objectMapperUtil.map(atualizado, ChamadoGetResponseDTO.class));
    }

    // Buscar chamados por técnico (GET /chamados/tecnico/{tecnicoId})
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<Page<ChamadoGetResponseDTO>> findByTecnicoId(
            @PathVariable Long tecnicoId,
            Pageable pageable) {

        Page<ChamadoGetResponseDTO> pagina = chamadoService.findByTecnicoId(tecnicoId, pageable)
                .map(c -> objectMapperUtil.map(c, ChamadoGetResponseDTO.class));

        return ResponseEntity.ok(pagina);
    }
}