package br.com.ifba.prg04deskflow.client.service;

import br.com.ifba.prg04deskflow.client.dto.ViaCepDTO;

public interface ViaCepIService {

    //Buscar endereço
    ViaCepDTO buscarEndereco(String cep);

}
