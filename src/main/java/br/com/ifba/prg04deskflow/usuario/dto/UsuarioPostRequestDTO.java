package br.com.ifba.prg04deskflow.usuario.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

//Classe que Pega os dados passados pelo usuario
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioPostRequestDTO {

    //Nome
    @JsonProperty(value = "nome")
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    //Email
    @JsonProperty(value = "email")
    @Email(message = "Email invalido")
    private String email;

    //Senha
    @JsonProperty(value = "senha")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;
}
