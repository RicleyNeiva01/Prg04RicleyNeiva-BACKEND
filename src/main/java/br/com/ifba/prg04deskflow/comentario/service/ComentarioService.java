package br.com.ifba.prg04deskflow.comentario.service;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.chamado.repository.ChamadoRepository;
import br.com.ifba.prg04deskflow.comentario.model.Comentario;
import br.com.ifba.prg04deskflow.comentario.repository.ComentarioRepository;
import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComentarioService implements ComentarioIService{

    private final ComentarioRepository comentarioRepository;
    private final ChamadoRepository chamadoRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    @Transactional
    public Comentario save(Comentario comentario) {
        if (comentario.getUsuario() == null || comentario.getUsuario().getId() == null){
            throw new BusinessException("Usuário não informado para o comentário.");
        }

        if (comentario.getChamado() == null || comentario.getChamado().getId() == null){
            throw new BusinessException("Chamado não informado para o comentário.");
        }

        Long idUsuario = comentario.getUsuario().getId();
        Long idChamado = comentario.getChamado().getId();

        // Verifica se o usuário existe
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado. ID: " + idUsuario));

        // Verifica se o chamado existe
        Chamado chamado = chamadoRepository.findById(idChamado)
                .orElseThrow(() -> new BusinessException("Chamado não encontrado. ID: " + idChamado));

        //Bloqueia se o chamado ja estiver sido RESOLVIDO
        if (chamado.getStatus() != null && "RESOLVIDO".equalsIgnoreCase(chamado.getStatus().toString())) {
            throw new BusinessException("Não é possível adicionar comentários a um chamado que já está resolvido.");
        }

        // Vincula as entidades validadas ao comentário
        comentario.setUsuario(usuario);
        comentario.setChamado(chamado);

        return comentarioRepository.save(comentario);
    }

    @Override
    public Page<Comentario> findAll(Pageable pageable) {
        return comentarioRepository.findAll(pageable);
    }

    @Override
    public Comentario findById(Long id) {
        return comentarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Comentário não encontrado. ID: " + id));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!comentarioRepository.existsById(id)) {
            throw new BusinessException("Comentário não encontrado. ID: " + id);
        }
        comentarioRepository.deleteById(id);
    }

    @Override
    public Page<Comentario> findByChamadoId(Long chamadoId, Pageable pageable) {
        return comentarioRepository.findByChamadoId(chamadoId, pageable);
    }
}
