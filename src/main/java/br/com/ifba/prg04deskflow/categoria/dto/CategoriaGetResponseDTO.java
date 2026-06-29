package br.com.ifba.prg04deskflow.categoria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaGetResponseDTO {

    private Long id;

    private String nome;

    private String descricao;
}