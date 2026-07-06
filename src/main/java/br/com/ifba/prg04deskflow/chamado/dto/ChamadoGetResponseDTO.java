package br.com.ifba.prg04deskflow.chamado.dto;

import br.com.ifba.prg04deskflow.categoria.dto.CategoriaResumoDTO;
import br.com.ifba.prg04deskflow.chamado.model.Prioridade;
import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoResumoDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoGetResponseDTO {

    private Long id;

    private String titulo;

    private String descricao;

    private LocalDateTime dataAbertura;

    private LocalDateTime dataFechamento;

    private StatusChamado status;

    private Prioridade prioridade;

    private UsuarioResumoDTO usuario;

    private CategoriaResumoDTO categoria;

    private TecnicoResumoDTO tecnico; // Quem está resolvendo o chamado
}
