package br.com.ifba.prg04deskflow.atendimento.dto;

import br.com.ifba.prg04deskflow.chamado.dto.ChamadoResumoDTO;
import br.com.ifba.prg04deskflow.tecnico.dto.TecnicoResumoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoGetResponseDTO {

    private Long id;

    private String descricaoSolucao;

    private LocalDateTime dataAtendimento;

    private TecnicoResumoDTO tecnico;

    private ChamadoResumoDTO chamado;
}
