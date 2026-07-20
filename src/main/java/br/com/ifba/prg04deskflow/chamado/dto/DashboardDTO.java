package br.com.ifba.prg04deskflow.chamado.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardDTO {
    private long total;
    private long abertos;
    private long emAndamento;
    private long resolvidos;
    private long tecnicos;
    private long usuarios;
}