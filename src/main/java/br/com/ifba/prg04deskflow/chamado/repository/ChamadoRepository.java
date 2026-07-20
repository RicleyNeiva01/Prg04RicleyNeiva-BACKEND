package br.com.ifba.prg04deskflow.chamado.repository;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChamadoRepository extends JpaRepository<Chamado, Long> {

    Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable);

    Page<Chamado> findByStatus(StatusChamado status, Pageable pageable);

    Page<Chamado> findByTituloContainingIgnoreCase(String titulo, Pageable pageable);

    Page<Chamado> findByUsuarioId(Long usuarioId, Pageable pageable);

    Page<Chamado> findByUsuarioIdAndStatus(Long usuarioId, StatusChamado status, Pageable pageable);

    Page<Chamado> findByUsuarioIdAndTituloContainingIgnoreCase(Long usuarioId, String titulo, Pageable pageable);

    Page<Chamado> findByTecnicoIdAndTituloContainingIgnoreCase(Long tecnicoId, String titulo, Pageable pageable);

    Page<Chamado> findByTecnicoIdAndStatus(Long tecnicoId, StatusChamado status, Pageable pageable);

    long countByStatus(StatusChamado status);

    long countByTecnicoId(Long tecnicoId);

    long countByTecnicoIdAndStatus(Long tecnicoId, StatusChamado status);

    long countByUsuarioId(Long usuarioId);

    long countByUsuarioIdAndStatus(Long usuarioId, StatusChamado status);


}