package br.com.ifba.prg04deskflow.tecnico.model;

import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tecnicos")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Tecnico extends Usuario {

    @Column(nullable = false)
    private String especialidade;
}