package br.com.ifba.prg04deskflow.categoria.service;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.categoria.repository.CategoriaRepository;
import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoriaService implements CategoriaIService{

    private final CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public Categoria save(Categoria categoria) {

        // Validação: Impede duplicidade de nomes de categorias no banco
        if (categoriaRepository.existsByNome(categoria.getNome())){
            throw new BusinessException("Ja existe uma categoria cadastrada com esse nome");
        }

        return categoriaRepository.save(categoria);
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return categoriaRepository.findAll(pageable);
    }

    @Override
    public Categoria findById(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada. ID: " + id));
    }

    @Override
    @Transactional
    public Categoria update(Long id, Categoria categoria) {

        Categoria categoriaExistente = categoriaRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada. ID: " + id));

        // Se mudou o nome da categoria, valida se o novo nome já não está em uso
        if (!categoriaExistente.getNome().equals(categoria.getNome())) {
            if (categoriaRepository.existsByNome(categoria.getNome())) {
                throw new BusinessException("Este nome de categoria já está em uso.");
            }
        }

        // Atualizando os campos
        categoriaExistente.setNome(categoria.getNome());
        categoriaExistente.setDescricao(categoria.getDescricao());

        return categoriaRepository.save(categoriaExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new BusinessException("Categoria não encontrada. ID: " + id);
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public Page<Categoria> findByNome(String nome, Pageable pageable) {
        return categoriaRepository.findByNomeContainingIgnoreCase(nome.trim(), pageable);
    }
}
