package br.com.ifba.prg04deskflow.atendimento.service;

import br.com.ifba.prg04deskflow.atendimento.model.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AtendimentoIService {

    Atendimento save(Atendimento atendimento);
    Page<Atendimento> findAll(Pageable pageable);
    Atendimento findById(Long id);
    Atendimento update(Long id, Atendimento atendimento);
    void delete(Long id);
    Page<Atendimento> findByTecnicoId(Long tecnicoId, Pageable pageable);
    Atendimento findByChamadoId(Long chamadoId);
}
