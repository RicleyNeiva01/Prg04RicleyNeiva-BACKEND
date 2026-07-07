package br.com.ifba.prg04deskflow.chamado.model;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.comentario.model.Comentario;
import br.com.ifba.prg04deskflow.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "chamados")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true) //Muito importante para o Lombok ler o ID herdado
public class Chamado extends PersistenceEntity {

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "data_abertura", nullable = false, updatable = false)
    private LocalDateTime dataAbertura;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusChamado status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Prioridade prioridade;

    @Column(name = "data_fechamento")
    private LocalDateTime dataFechamento;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    //Se o chamado for deletado, os comentarios desse chamado tambem sao deletados
    @OneToMany(mappedBy = "chamado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comentario> comentarios = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (this.dataAbertura == null) {
            this.dataAbertura = LocalDateTime.now();
        }
        if (this.status == null) {
            this.status = StatusChamado.ABERTO;
        }
    }
}
