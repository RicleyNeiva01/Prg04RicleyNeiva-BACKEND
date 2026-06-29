package br.com.ifba.prg04deskflow.tecnico.dto;

import br.com.ifba.prg04deskflow.usuario.model.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TecnicoGetResponseDTO {

    private Long id;

    private String nome;

    private String cpf;

    private String telefone;

    private String email;

    private PerfilUsuario perfil;

    private String especialidade;
}
