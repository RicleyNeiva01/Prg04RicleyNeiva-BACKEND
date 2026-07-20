package br.com.ifba.prg04deskflow.atendimento.controller;

import br.com.ifba.prg04deskflow.atendimento.dto.AtendimentoGetResponseDTO;
import br.com.ifba.prg04deskflow.atendimento.dto.AtendimentoPostRequestDTO;
import br.com.ifba.prg04deskflow.atendimento.dto.AtendimentoPutRequestDTO;
import br.com.ifba.prg04deskflow.atendimento.model.Atendimento;
import br.com.ifba.prg04deskflow.atendimento.service.AtendimentoIService;
import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/atendimentos")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoIService atendimentoService;
    private final ObjectMapperUtil objectMapperUtil;

    //Savar atendimento
    @PostMapping
    public ResponseEntity<AtendimentoGetResponseDTO> save(@RequestBody @Valid AtendimentoPostRequestDTO dto){

        Atendimento atendimento = objectMapperUtil.map(dto, Atendimento.class);

        //Monta chamado com id
        Chamado chamado = new Chamado();
        chamado.setId(dto.getChamadoId());
        atendimento.setChamado(chamado);

        //Monta Tecnico com id
        Tecnico tecnico = new Tecnico();
        tecnico.setId(dto.getTecnicoId());
        atendimento.setTecnico(tecnico);

        Atendimento salvo = atendimentoService.save(atendimento);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, AtendimentoGetResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<Page<AtendimentoGetResponseDTO>> findAll(Pageable pageable){

        Page<AtendimentoGetResponseDTO> pagina = atendimentoService.findAll(pageable)
                .map(a -> objectMapperUtil.map(a, AtendimentoGetResponseDTO.class));
        return ResponseEntity.ok(pagina);
    }

    //Buscar atendimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<AtendimentoGetResponseDTO> findById(@PathVariable Long id){

        Atendimento atendimento = atendimentoService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(atendimento, AtendimentoGetResponseDTO.class));
    }

    //Atualizar Atendimento
    @PutMapping("/{id}")
    public ResponseEntity<AtendimentoGetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AtendimentoPutRequestDTO dto) {

        Atendimento atendimento = objectMapperUtil.map(dto, Atendimento.class);

        Atendimento atualizado = atendimentoService.update(id, atendimento);

        return ResponseEntity.ok(
                objectMapperUtil.map(atualizado, AtendimentoGetResponseDTO.class)
        );
    }

    // Deletar atendimento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        atendimentoService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Buscar atendimento por Chamado ID
    @GetMapping("/chamado/{chamadoId}")
    public ResponseEntity<AtendimentoGetResponseDTO> findByChamadoId(@PathVariable Long chamadoId) {

        Atendimento atendimento = atendimentoService.findByChamadoId(chamadoId);
        return ResponseEntity.ok(objectMapperUtil.map(atendimento, AtendimentoGetResponseDTO.class));
    }

    // Listar atendimentos por Tecnico ID
    @GetMapping("/tecnico/{tecnicoId}")
    public ResponseEntity<Page<AtendimentoGetResponseDTO>> findByTecnicoId(
            @PathVariable Long tecnicoId,
            Pageable pageable) {

        Page<AtendimentoGetResponseDTO> pagina = atendimentoService.findByTecnicoId(tecnicoId, pageable)
                .map(a -> objectMapperUtil.map(a, AtendimentoGetResponseDTO.class));

        return ResponseEntity.ok(pagina);
    }
}
