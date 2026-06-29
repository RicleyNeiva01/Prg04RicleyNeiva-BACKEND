package br.com.ifba.prg04deskflow.chamado.dto;

import br.com.ifba.prg04deskflow.chamado.model.Prioridade;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoPostRequestDTO {

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    @NotNull(message = "A prioridade é obrigatória")
    private Prioridade prioridade;

    @NotNull(message = "O ID do usuário é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O ID da categoria é obrigatório")
    private Long categoriaId;
}
