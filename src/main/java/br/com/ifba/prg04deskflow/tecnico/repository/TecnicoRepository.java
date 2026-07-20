package br.com.ifba.prg04deskflow.tecnico.repository;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    boolean existsByCpf(String cpf);

    Page<Tecnico> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    Page<Tecnico> findByAtivoTrue(Pageable pageable);

    Page<Tecnico> findByNomeContainingIgnoreCaseAndAtivoTrue(String nome, Pageable pageable);

    Optional<Tecnico> findByEmail(String email);
}
