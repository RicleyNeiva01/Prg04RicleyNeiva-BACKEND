package br.com.ifba.prg04deskflow.categoria.service;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoriaIService {

    Categoria save(Categoria categoria);

    Page<Categoria> findAll(Pageable pageable);

    Categoria findById(Long id);

    Categoria update(Long id, Categoria categoria);

    void delete(Long id);
}
