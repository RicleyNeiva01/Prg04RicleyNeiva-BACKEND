package br.com.ifba.prg04deskflow.chamado.service;

import br.com.ifba.prg04deskflow.chamado.dto.DashboardDTO;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.tecnico.repository.TecnicoRepository;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ChamadoIService chamadoService;
    private final UsuarioRepository usuarioRepository;
    private final TecnicoRepository tecnicoRepository;

    public DashboardDTO getDashboardAdmin() {
        DashboardDTO dto = new DashboardDTO();
        dto.setTotal(chamadoService.countTotal());
        dto.setAbertos(chamadoService.countByStatus(StatusChamado.ABERTO));
        dto.setEmAndamento(chamadoService.countByStatus(StatusChamado.EM_ANDAMENTO));
        dto.setResolvidos(chamadoService.countByStatus(StatusChamado.RESOLVIDO));
        dto.setTecnicos(tecnicoRepository.count());
        dto.setUsuarios(usuarioRepository.count());
        return dto;
    }

    public DashboardDTO getDashboardTecnico(String email) {
        var tecnico = tecnicoRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Técnico não encontrado."));
        DashboardDTO dto = new DashboardDTO();
        dto.setTotal(chamadoService.countByTecnicoId(tecnico.getId()));
        dto.setAbertos(chamadoService.countByTecnicoIdAndStatus(tecnico.getId(), StatusChamado.ABERTO));
        dto.setEmAndamento(chamadoService.countByTecnicoIdAndStatus(tecnico.getId(), StatusChamado.EM_ANDAMENTO));
        dto.setResolvidos(chamadoService.countByTecnicoIdAndStatus(tecnico.getId(), StatusChamado.RESOLVIDO));
        return dto;
    }

    public DashboardDTO getDashboardUsuario(String email) {
        var usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado."));
        DashboardDTO dto = new DashboardDTO();
        dto.setTotal(chamadoService.countByUsuarioId(usuario.getId()));
        dto.setAbertos(chamadoService.countByUsuarioIdAndStatus(usuario.getId(), StatusChamado.ABERTO));
        dto.setEmAndamento(chamadoService.countByUsuarioIdAndStatus(usuario.getId(), StatusChamado.EM_ANDAMENTO));
        dto.setResolvidos(chamadoService.countByUsuarioIdAndStatus(usuario.getId(), StatusChamado.RESOLVIDO));
        return dto;
    }
}