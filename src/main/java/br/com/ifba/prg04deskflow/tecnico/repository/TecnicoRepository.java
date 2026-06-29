package br.com.ifba.prg04deskflow.tecnico.repository;

import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {

    boolean existsByCpf(String cpf);
}
