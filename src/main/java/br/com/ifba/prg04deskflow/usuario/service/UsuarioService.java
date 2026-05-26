package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    private final UsuarioRepository usuarioRepository;

    //Salvar usuario, com validações
    @Override
    @Transactional
    public Usuario save(Usuario usuario){
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new BusinessException("O Email ja existe no sistema");
        }

        return usuarioRepository.save(usuario);
    }

    //Lista todos os usuarios
    @Override
    public List <Usuario> findAll(){
       return usuarioRepository.findAll();
    }

    //Busca um ID
    @Override
    public Usuario findById(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID: " + id));
    }

    //Atualizar usuario
    @Override
    @Transactional
    public Usuario update(Long id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID:" + id));

        if(!usuarioExistente.getEmail().equals(usuario.getEmail())){
            if(usuarioRepository.existsByEmail(usuario.getEmail())){
                throw new BusinessException("Email ja esta em uso");
            }
        }

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());

        return usuarioRepository.save(usuarioExistente);
    }

    //Deletar usuario
    @Override
    @Transactional
    public void delete(Long id){
        if(!usuarioRepository.existsById(id))
            throw new BusinessException("Usuario nao encontrado. ID:" + id);
        usuarioRepository.deleteById(id);
    }


}


