package br.com.ifba.prg04deskflow.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestDTO {

    private String nome;

    private String email;

    private String senha;
}
