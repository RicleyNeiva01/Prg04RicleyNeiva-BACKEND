package br.com.ifba.prg04deskflow.comentario.model;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "comentarios")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Comentario extends PersistenceEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensagem;

    @Column(name = "data_comentario", nullable = false, updatable = false)
    private LocalDateTime dataComentario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "chamado_id", nullable = false)
    private Chamado chamado;

    @PrePersist
    protected void onCreate() {
        if (this.dataComentario == null) {
            this.dataComentario = LocalDateTime.now();
        }
    }
}
