package br.com.ifba.prg04deskflow.usuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioGetResponseDTO {

    private Long id;

    private String nome;

    private String email;
}
