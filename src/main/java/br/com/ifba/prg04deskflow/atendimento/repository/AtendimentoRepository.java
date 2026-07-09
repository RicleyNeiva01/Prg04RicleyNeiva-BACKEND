package br.com.ifba.prg04deskflow.atendimento.repository;

import br.com.ifba.prg04deskflow.atendimento.model.Atendimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    Optional<Atendimento> findByChamadoId(Long chamadoId);

    Page<Atendimento> findByTecnicoId(Long tecnicoId, Pageable pageable);
}
