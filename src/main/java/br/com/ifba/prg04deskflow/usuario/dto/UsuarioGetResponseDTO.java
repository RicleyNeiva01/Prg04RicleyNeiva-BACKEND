package br.com.ifba.prg04deskflow.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Classe que Envia os dados pro usuario
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDTO {

    //Id
    private Long id;

    //Nome
    private String nome;

    //Email
    private String email;
}
