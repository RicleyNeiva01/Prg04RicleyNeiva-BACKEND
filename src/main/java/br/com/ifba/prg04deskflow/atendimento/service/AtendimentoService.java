package br.com.ifba.prg04deskflow.atendimento.service;

import br.com.ifba.prg04deskflow.atendimento.model.Atendimento;
import br.com.ifba.prg04deskflow.atendimento.repository.AtendimentoRepository;
import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.chamado.repository.ChamadoRepository;
import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.tecnico.repository.TecnicoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AtendimentoService implements AtendimentoIService{

    private final AtendimentoRepository atendimentoRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ChamadoRepository chamadoRepository;

    @Override
    @Transactional
    public Atendimento save(Atendimento atendimento) {

        if (atendimento.getChamado() == null || atendimento.getChamado().getId() == null){
            throw new BusinessException("Chamado não informado");
        }

        if (atendimento.getTecnico() == null || atendimento.getTecnico().getId() == null){
            throw new BusinessException("Técnico não informado");
        }

        Long chamadoId = atendimento.getChamado().getId();
        Long tecnicoId = atendimento.getTecnico().getId();

        Chamado chamado = chamadoRepository.findById(chamadoId)
                .orElseThrow(() -> new BusinessException("Chamado não encontrado. ID: " + chamadoId));

        Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
                .orElseThrow(() -> new BusinessException("Técnico não encontrado. ID: " + tecnicoId));

        //chamado precisa de um dono antes de ser resolvido
        if (chamado.getTecnico() == null){
            throw new BusinessException("O chamado ainda não possui um técnico responsavel. Assuma o chamado antes de resolve-lo");
        }

        if (chamado.getStatus() == StatusChamado.ABERTO){
            throw new BusinessException(
                    "O chamado deve estar EM_ANDAMENTO antes de ser resolvido."
            );
        }

        if (chamado.getStatus() == StatusChamado.RESOLVIDO){
            throw new BusinessException(
                    "Este chamado já foi resolvido."
            );
        }

        //Apenas o dono atual do chamado pode encerra-lo
        if (!chamado.getTecnico().getId().equals(tecnicoId)){
            throw new BusinessException("O técnico informado não é responsavel pelo chamado");
        }

        //Garante que nunca haverá mais de um atendimento para o mesmo chamado
        if (atendimentoRepository.findByChamadoId(chamadoId).isPresent()){
            throw new BusinessException("Este chamado ja possui um atendimento registrado");
        }

        //Associa as entidades validas ao atendimento
        atendimento.setChamado(chamado);
        atendimento.setTecnico(tecnico);

        //Altera o status do chamado para RESOLVIDO e crava a data/hora do fechamento
        chamado.setStatus(StatusChamado.RESOLVIDO);
        chamado.setDataFechamento(LocalDateTime.now());

        chamadoRepository.save(chamado);

        return atendimentoRepository.save(atendimento);
    }

    @Override
    public Page<Atendimento> findAll(Pageable pageable) {
        return atendimentoRepository.findAll(pageable);
    }

    @Override
    public Atendimento findById(Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Atendimento não encontrado. ID: " + id));
    }

    @Override
    @Transactional
    public Atendimento update(Long id, Atendimento atendimento) {
        //Buscar o atendimento
        Atendimento atendimentoExistente = atendimentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Atendimento não encontrado. ID: " + id));

        // Permite atualizar apenas o texto descritivo da solução
        if (atendimento.getDescricaoSolucao() != null && !atendimento.getDescricaoSolucao().isBlank()) {
            atendimentoExistente.setDescricaoSolucao(atendimento.getDescricaoSolucao());
        }

        return atendimentoRepository.save(atendimentoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Atendimento não encontrado. ID: " + id));

        Chamado chamado = atendimento.getChamado();

        chamado.setStatus(StatusChamado.EM_ANDAMENTO);
        chamado.setDataFechamento(null); // Limpa a data de fechamento antiga

        chamadoRepository.save(chamado);

        // Apaga fisicamente o registro de atendimento do banco
        atendimentoRepository.delete(atendimento);

    }

    @Override
    public Page<Atendimento> findByTecnicoId(Long tecnicoId, Pageable pageable) {
        // Retorna soluções realizadas por um determinado técnico, em uma pagina
        return atendimentoRepository.findByTecnicoId(tecnicoId, pageable);
    }

    @Override
    public Atendimento findByChamadoId(Long chamadoId) {
        // Busca o atendimento baseado no ID do chamado. Lança erro caso o chamado ainda não esteja resolvido
        return atendimentoRepository.findByChamadoId(chamadoId)
                .orElseThrow(() -> new BusinessException("Atendimento não encontrado para este chamado."));
    }


}
