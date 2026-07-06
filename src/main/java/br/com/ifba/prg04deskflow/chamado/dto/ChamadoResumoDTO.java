package br.com.ifba.prg04deskflow.chamado.dto;

import br.com.ifba.prg04deskflow.chamado.model.StatusChamado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamadoResumoDTO {

    private Long id;

    private String titulo;

    private StatusChamado status;
}
