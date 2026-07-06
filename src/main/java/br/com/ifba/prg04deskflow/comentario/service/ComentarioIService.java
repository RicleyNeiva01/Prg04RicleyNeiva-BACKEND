package br.com.ifba.prg04deskflow.comentario.service;

import br.com.ifba.prg04deskflow.comentario.model.Comentario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ComentarioIService {

    Comentario save(Comentario comentario);
    Page<Comentario> findAll(Pageable pageable);
    Comentario findById(Long id);
    void delete(Long id);
    Page<Comentario> findByChamadoId(Long chamadoId, Pageable pageable);
}
