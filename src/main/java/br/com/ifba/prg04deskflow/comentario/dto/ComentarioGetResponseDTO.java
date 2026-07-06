package br.com.ifba.prg04deskflow.comentario.dto;

import br.com.ifba.prg04deskflow.chamado.dto.ChamadoResumoDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioGetResponseDTO {

    private Long id;

    private String mensagem;

    private LocalDateTime dataComentario;

    private UsuarioResumoDTO usuario;

    private ChamadoResumoDTO chamado;
}
