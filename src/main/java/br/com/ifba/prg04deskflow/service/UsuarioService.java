package br.com.ifba.prg04deskflow.service;

import br.com.ifba.prg04deskflow.model.Usuario;
import br.com.ifba.prg04deskflow.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    //Salvar usuario, com validações
    public Usuario salvar(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new RuntimeException("O Email ja existe no sistema");
        }
        return usuarioRepository.save(usuario);
    }

    //Lista todos os usuarios
    public List <Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    //Busca um ID
    public Usuario buscarPorId(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado. ID:" + id));
    }

    //Atualizar usuario
    public Usuario atualizar(Long id, Usuario novoUsuario){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario não encontrado. ID:" + id));

        //Valida se o novo email ja esta em uso por outro usuario
        if(!usuarioExistente.getEmail().equals(novoUsuario.getEmail())){
            if(usuarioRepository.existsByEmail(novoUsuario.getEmail())){
                throw new RuntimeException("Email ja esta em uso");
            }
        }

        usuarioExistente.setNome(novoUsuario.getNome());
        usuarioExistente.setEmail(novoUsuario.getEmail());
        usuarioExistente.setSenha(novoUsuario.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }

    //Deletar usuario
    public void deletar(Long id){
        if(!usuarioRepository.existsById(id))
            throw new RuntimeException("Usuario nao encontrado. ID"+ id);

        usuarioRepository.deleteById(id);
    }


}


