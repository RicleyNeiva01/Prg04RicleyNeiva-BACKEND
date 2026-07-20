package br.com.ifba.prg04deskflow.usuario.model;

import br.com.ifba.prg04deskflow.pessoa.model.Pessoa;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "usuarios")
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // Esta é a chave que liga o Usuário à Pessoa no banco
@EqualsAndHashCode(callSuper = true)
public class Usuario extends Pessoa {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    // Define se é USUARIO_COMUM, TECNICO ou ADMIN
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PerfilUsuario perfil;

    @Column(nullable = false)
    private Boolean ativo;

    // Usuario ja nasce ativo
    @PrePersist
    protected void onCreate() {
        if (this.ativo == null) {
            this.ativo = true;
        }
    }
}
