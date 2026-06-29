package br.com.ifba.prg04deskflow.chamado.model;

import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "chamados")
@NoArgsConstructor
@AllArgsConstructor
public class Chamado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @PrePersist
    protected void onCreate() {
        this.dataAbertura = LocalDateTime.now();
        if (this.status == null) {
            this.status = StatusChamado.ABERTO;
        }
    }
}
