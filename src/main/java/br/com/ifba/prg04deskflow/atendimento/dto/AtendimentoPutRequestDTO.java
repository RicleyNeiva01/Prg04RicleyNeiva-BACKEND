package br.com.ifba.prg04deskflow.atendimento.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AtendimentoPutRequestDTO {

    @NotBlank(message = "A descrição é obrigatória")
    private String descricaoSolucao;

}