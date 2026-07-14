package br.com.ifba.prg04deskflow.tecnico.controller;

import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoGetResponseDTO;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoPostRequestDTO;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.tecnico.service.TecnicoIService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tecnicos")
@RequiredArgsConstructor
public class TecnicoController {

    private final TecnicoIService tecnicoService;
    private final ObjectMapperUtil objectMapperUtil;

    @PostMapping
    public ResponseEntity<TecnicoGetResponseDTO> save(@RequestBody @Valid TecnicoPostRequestDTO dto){

        Tecnico tecnico = objectMapperUtil.map(dto, Tecnico.class);
        tecnico.setAtivo(true);
        Tecnico salvo = tecnicoService.save(tecnico);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, TecnicoGetResponseDTO.class));
    }

    @GetMapping
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

        Page<TecnicoGetResponseDTO> resposta = tecnicos.map(
                tecnico -> objectMapperUtil.map(tecnico, TecnicoGetResponseDTO.class));

        return ResponseEntity.ok(resposta);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TecnicoGetResponseDTO> findById(@PathVariable Long id){

        Tecnico tecnico = tecnicoService.findById(id);

        return ResponseEntity.ok(
                objectMapperUtil.map(tecnico, TecnicoGetResponseDTO.class)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TecnicoGetResponseDTO> update(
            @PathVariable Long id,
            @RequestBody @Valid TecnicoPostRequestDTO dto) {

        Tecnico tecnico = objectMapperUtil.map(dto, Tecnico.class);

        Tecnico atualizado = tecnicoService.update(id, tecnico);

        return ResponseEntity.ok(
                objectMapperUtil.map(atualizado, TecnicoGetResponseDTO.class)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        tecnicoService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
