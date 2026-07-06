package br.com.ifba.prg04deskflow.comentario.repository;

import br.com.ifba.prg04deskflow.comentario.model.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    //buscar todos os comentários de um chamado específico:
    Page<Comentario> findByChamadoId(Long chamadoId, Pageable pageable);
}
