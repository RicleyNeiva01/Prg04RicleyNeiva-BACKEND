package br.com.ifba.prg04deskflow.comentario.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioPostRequestDTO {

    @NotBlank(message = "A mensagem do comentario é obrigatória")
    private String mensagem;

    @NotNull(message = "O ID do usuario é obrigatório")
    private Long usuarioId;

    @NotNull(message = "O ID do chamado é obrigatório")
    private Long chamadoId;
}
