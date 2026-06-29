package br.com.ifba.prg04deskflow.tecnico.service;

import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TecnicoIService {
    Tecnico save(Tecnico tecnico);
    Page<Tecnico> findAll(Pageable pageable);
    Tecnico findById(Long id);
    Tecnico update(Long id, Tecnico tecnico);
    void delete(Long id);
}