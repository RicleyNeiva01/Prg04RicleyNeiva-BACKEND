package br.com.ifba.prg04deskflow.categoria.model;

import br.com.ifba.prg04deskflow.infrastructure.entity.PersistenceEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "categorias")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Categoria extends PersistenceEntity {

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false)
    private String descricao;
}