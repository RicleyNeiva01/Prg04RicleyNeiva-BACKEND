package br.com.ifba.prg04deskflow.categoria.repository;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    // Usado para garantir que não criaremos categorias com nomes duplicados
    boolean existsByNome(String nome);

    Page<Categoria> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
