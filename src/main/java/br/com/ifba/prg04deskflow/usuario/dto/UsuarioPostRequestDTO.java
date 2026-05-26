package br.com.ifba.prg04deskflow.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Classe que Pega os dados passados pelo usuario
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestDTO {

    //Nome
    private String nome;

    //Email
    private String email;

    //Senha
    private String senha;
}
