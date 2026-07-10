package br.com.ifba.prg04deskflow.chamado.service;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.categoria.repository.CategoriaRepository;
import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.chamado.repository.ChamadoRepository;
import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.tecnico.repository.TecnicoRepository;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ChamadoService implements ChamadoIService{

    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CategoriaRepository categoriaRepository;
    private final TecnicoRepository tecnicoRepository;

    @Override
    @Transactional
    public Chamado save(Chamado chamado) {
        if (chamado.getUsuario() == null || chamado.getUsuario().getId() == null) {
            throw new BusinessException("Usuário não informado.");
        }

        if (chamado.getCategoria() == null || chamado.getCategoria().getId() == null) {
            throw new BusinessException("Categoria não informada.");
        }

        Long idUsu = chamado.getUsuario().getId();
        Long idCat = chamado.getCategoria().getId();

        Usuario usuario = usuarioRepository.findById(idUsu)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado. ID: " + idUsu));

        Categoria categoria = categoriaRepository.findById(idCat)
                .orElseThrow(() -> new BusinessException("Categoria não encontrada. ID: " + idCat));

        chamado.setUsuario(usuario);
        chamado.setCategoria(categoria);
        chamado.setStatus(StatusChamado.ABERTO);

        return chamadoRepository.save(chamado);
    }

    @Override
    public Page<Chamado> findAll(Pageable pageable) {
        return chamadoRepository.findAll(pageable);
    }

    @Override
    public Chamado findById(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Chamado não encontrado. ID: " + id));
    }

    @Override
    @Transactional
    public Chamado update(Long id, Chamado chamado) {
        Chamado chamadoExistente = findById(id);

        // Só atualiza a categoria se ela for enviada na requisição
        if (chamado.getCategoria() != null && chamado.getCategoria().getId() != null) {
            Long idCat = chamado.getCategoria().getId();

            Categoria categoria = categoriaRepository.findById(idCat)
                    .orElseThrow(() -> new BusinessException(
                            "Categoria não encontrada. ID: " + idCat));

            chamadoExistente.setCategoria(categoria);
        }

        // Atualiza campos simples
        if (chamado.getTitulo() != null) {
            chamadoExistente.setTitulo(chamado.getTitulo());
        }

        if (chamado.getDescricao() != null) {
            chamadoExistente.setDescricao(chamado.getDescricao());
        }

        if (chamado.getPrioridade() != null) {
            chamadoExistente.setPrioridade(chamado.getPrioridade());
        }

        return chamadoRepository.save(chamadoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!chamadoRepository.existsById(id)) {
            throw new BusinessException("Chamado não encontrado. ID: " + id);
        }
        chamadoRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Chamado updateStatus(Long id, StatusChamado novoStatus) {
        Chamado chamado = findById(id);

        if (chamado.getStatus() == StatusChamado.RESOLVIDO && novoStatus == StatusChamado.ABERTO) {
            throw new BusinessException("Chamado já resolvido não pode ser reaberto.");
        }

        // Se o chamado estiver sendo movido para RESOLVIDO, grava a data/hora atual
        if (novoStatus == StatusChamado.RESOLVIDO && chamado.getDataFechamento() == null) {
            chamado.setDataFechamento(LocalDateTime.now());
        }

        if (novoStatus != StatusChamado.RESOLVIDO) {
            chamado.setDataFechamento(null);
        }

        chamado.setStatus(novoStatus);
        return chamadoRepository.save(chamado);
    }

    @Override
    @Transactional
    public Chamado atribuirTecnico(Long idChamado, Long idTecnico) {
        Chamado chamado = findById(idChamado);

        Tecnico tecnico = tecnicoRepository.findById(idTecnico)
                .orElseThrow(() -> new BusinessException("Técnico não encontrado. ID: " + idTecnico));

        // Não mexe se já fechou
        if (chamado.getStatus() == StatusChamado.RESOLVIDO) {
            throw new BusinessException("Não é possível atribuir técnico a um chamado resolvido.");
        }

        // Evita atribuir o mesmo cara duas vezes à toa
        if (chamado.getTecnico() != null && chamado.getTecnico().getId().equals(idTecnico)) {
            throw new BusinessException("Este chamado já está com este técnico.");
        }

        // Só muda para EM_ANDAMENTO se ele ainda estiver ABERTO
        if (chamado.getStatus() == StatusChamado.ABERTO) {
            chamado.setStatus(StatusChamado.EM_ANDAMENTO);
        }

        chamado.setTecnico(tecnico);
        return chamadoRepository.save(chamado);
    }

    @Override
    public Page<Chamado> findByTecnicoId(Long tecnicoId, Pageable pageable) {
        return chamadoRepository.findByTecnicoId(tecnicoId, pageable);
    }

    @Override
    public Page<Chamado> findByStatus(StatusChamado status, Pageable pageable) {
        return chamadoRepository.findByStatus(status, pageable);
    }
}
