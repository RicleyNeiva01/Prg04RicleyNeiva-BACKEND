package br.com.ifba.prg04deskflow.usuario.dto;

import br.com.ifba.prg04deskflow.usuario.model.PerfilUsuario;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    //Cpf
    @NotBlank(message = "CPF é obrigatório")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
    private String cpf;

    //Telefone
    @JsonProperty(value = "telefone")
    @NotBlank(message = "Telefone é obrigatório")
    private String telefone;

    //Email
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email invalido")
    private String email;

    //Senha
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String senha;

    // Campo específico de Usuario
    @NotNull(message = "O perfil de usuário é obrigatório")
    private PerfilUsuario perfil;
}
