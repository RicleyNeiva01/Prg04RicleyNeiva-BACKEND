package br.com.ifba.prg04deskflow.tecnico.controller;

import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoGetResponseDTO;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoPostRequestDTO;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoPutRequestDTO;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.tecnico.service.TecnicoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoIService tecnicoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoGetResponseDTO> save(@RequestBody @Valid TecnicoPostRequestDTO dto) {
        Tecnico tecnico = objectMapperUtil.map(dto, Tecnico.class);
        tecnico.setAtivo(true);
        Tecnico salvo = tecnicoService.save(tecnico);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, TecnicoGetResponseDTO.class));
    }

    // Só ADMIN
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<TecnicoGetResponseDTO>> findAll(
            @RequestParam(required = false) String nome,
            @RequestParam(defaultValue = "false") boolean mostrarInativos,
            Pageable pageable) {

        Page<Tecnico> tecnicos;
        if (nome != null && !nome.isBlank()) {
            tecnicos = tecnicoService.findByNome(nome, mostrarInativos, pageable);
        } else {
            tecnicos = tecnicoService.findAll(mostrarInativos, pageable);
        }

        return ResponseEntity.ok(tecnicos.map(t -> objectMapperUtil.map(t, TecnicoGetResponseDTO.class)));
    }

    // Só ADMIN
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoGetResponseDTO> findById(@PathVariable Long id) {
        Tecnico tecnico = tecnicoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(tecnico, TecnicoGetResponseDTO.class));
    }

    // Só ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TecnicoGetResponseDTO> update(@PathVariable Long id,
                                                        @RequestBody @Valid TecnicoPutRequestDTO dto) {
        Tecnico tecnico = objectMapperUtil.map(dto, Tecnico.class);
        Tecnico atualizado = tecnicoService.update(id, tecnico);
        return ResponseEntity.ok(objectMapperUtil.map(atualizado, TecnicoGetResponseDTO.class));
    }

    // Só ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tecnicoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Só ADMIN
    @PutMapping("/{id}/reativar")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> reativarTecnico(@PathVariable Long id) {
        tecnicoService.reativarTecnico(id);
        return ResponseEntity.ok().build();
    }
}