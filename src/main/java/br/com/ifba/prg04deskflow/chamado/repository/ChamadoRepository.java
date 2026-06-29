package br.com.ifba.prg04deskflow.chamado.repository;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable);
}