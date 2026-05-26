package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.usuario.model.Usuario;

import java.util.List;

public interface UsuarioIService {

    //Salvar usuario
    Usuario save(Usuario usuario);

    //Listar usuarios
    List <Usuario> findAll();

    //Buscar usuarios
    Usuario findById(Long id);

    //Atualizar usuarios
    Usuario update(Long id, Usuario usuario);

    //Deletar usuarios
    void delete(Long id);
}
