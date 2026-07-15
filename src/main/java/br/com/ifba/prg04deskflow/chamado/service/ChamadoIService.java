package br.com.ifba.prg04deskflow.chamado.service;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChamadoIService {

    Chamado save(Chamado chamado);
    Page<Chamado> findAll(Pageable pageable);
    Chamado findById(Long id);
    Chamado update(Long id, Chamado chamado);
    void delete(Long id);
    public Chamado updateStatus(Long id, StatusChamado novoStatus);
    Chamado atribuirTecnico(Long idChamado, Long idTecnico);
    Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable);
    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);
    Page<Chamado> findByTitulo(String titulo, Pageable pageable);
}
