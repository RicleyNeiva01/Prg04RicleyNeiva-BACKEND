package br.com.ifba.prg04deskflow.atendimento.model;

import br.com.ifba.prg04deskflow.chamado.model.Chamado;
import br.com.ifba.prg04deskflow.infrastructure.entity.PersistenceEntity;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "atendimentos")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Atendimento extends PersistenceEntity {

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descricaoSolucao;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataAtendimento;

    @OneToOne(optional = false)
    @JoinColumn(name = "chamado_id", nullable = false, unique = true)
    private Chamado chamado;

    @ManyToOne(optional = false)
    @JoinColumn(name = "tecnico_id", nullable =false)
    private Tecnico tecnico;

    @PrePersist
    protected void onCreate(){
        dataAtendimento = LocalDateTime.now();
    }
}
