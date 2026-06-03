package br.com.ifba.prg04deskflow.client.service;

import br.com.ifba.prg04deskflow.client.dto.ViaCepDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ViaCepService implements ViaCepIService {

    private final WebClient webClient;

    @Override
    public ViaCepDTO buscarEndereco(String cep) {
        return webClient.get()
                .uri("https://viacep.com.br/ws/" + cep + "/json/")
                .retrieve()
                .bodyToMono(ViaCepDTO.class)
                .block();
    }
}