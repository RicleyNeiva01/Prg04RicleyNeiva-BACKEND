package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.usuario.dto.UsuarioGetResponseDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPostRequestDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;

import java.util.List;

public interface UsuarioIService {

    //Salvar usuario
    UsuarioGetResponseDTO salvar(UsuarioPostRequestDTO dto);

    //Listar usuarios
    List <UsuarioGetResponseDTO> listarTodos();

    //Buscar usuarios
    UsuarioGetResponseDTO buscarPorId(Long id);

    //Atualizar usuarios
    UsuarioGetResponseDTO atualizar(Long id, UsuarioPostRequestDTO dto);

    //Deletar usuarios
    void deletar(Long id);
}
