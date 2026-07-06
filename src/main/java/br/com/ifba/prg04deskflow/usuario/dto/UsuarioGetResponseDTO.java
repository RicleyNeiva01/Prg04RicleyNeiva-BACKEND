package br.com.ifba.prg04deskflow.usuario.dto;

import br.com.ifba.prg04deskflow.usuario.model.PerfilUsuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Classe que Envia os dados pro usuario
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDTO {

    private Long id;

    private String nome;

    private String email;

    private String cpf;

    private String telefone;

    private PerfilUsuario perfil;

    private Boolean ativo;
}
