package br.com.ifba.prg04deskflow.atendimento.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoPostRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricaoSolucao;

    @NotNull(message = "O técnico é obrigatório")
    private Long tecnicoId;

    @NotNull(message = "O chamado é obrigatório")
    private Long chamadoId;
}
