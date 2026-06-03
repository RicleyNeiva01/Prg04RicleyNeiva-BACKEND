package br.com.ifba.prg04deskflow.client.controller;

import br.com.ifba.prg04deskflow.client.dto.ViaCepDTO;
import br.com.ifba.prg04deskflow.client.service.ViaCepIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cep")
@RequiredArgsConstructor
public class ViaCepController {

    private final ViaCepIService viaCepService;

    @GetMapping("/{cep}")
    public ResponseEntity<ViaCepDTO> consultarCep(@PathVariable String cep) {
        ViaCepDTO endereco = viaCepService.buscarEndereco(cep);
        return ResponseEntity.ok(endereco);
    }
}