package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UsuarioIService {

    //Salvar usuario
    Usuario save(Usuario usuario);

    //Listar usuarios
    Page<Usuario> findAll(boolean mostrarInativos, Pageable pageable);

    //Buscar usuarios
    Usuario findById(Long id);

    //Atualizar usuarios
    Usuario update(Long id, Usuario usuario);

    //Deletar usuarios
    void delete(Long id);

    //Buscar por nome
    Page<Usuario> findByNome(String nome, boolean mostrarInativos, Pageable pageable);
}
