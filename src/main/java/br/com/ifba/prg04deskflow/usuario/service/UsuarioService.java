package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    private final UsuarioRepository usuarioRepository;

    //Salvar usuario, com validações e Transaction
    @Override
    @Transactional
    public Usuario save(Usuario usuario){
        //Validação de Email
        if(usuarioRepository.existsByEmail(usuario.getEmail())){
            throw new BusinessException("O Email ja existe no sistema");
        }

        //Validação de CPF
        if(usuarioRepository.existsByCpf(usuario.getCpf())){
            throw new BusinessException("O CPF já existe no sistema");
        }

        return usuarioRepository.save(usuario);
    }

    //Lista todos os usuarios
    @Override
    public Page<Usuario> findAll(Pageable pageable){
        return usuarioRepository.findAll(pageable);
    }

    //Busca um ID
    @Override
    public Usuario findById(Long id){
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID: " + id));
    }

    //Atualizar usuario
    @Override
    @Transactional //Transação
    public Usuario update(Long id, Usuario usuario){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID:" + id));

        //Validar email se mudou
        if(!usuarioExistente.getEmail().equals(usuario.getEmail())){
            if(usuarioRepository.existsByEmail(usuario.getEmail())){
                throw new BusinessException("Email ja esta em uso");
            }
        }

        //Validar CPF se mudou
        if(!usuarioExistente.getCpf().equals(usuario.getCpf())){
            if(usuarioRepository.existsByCpf(usuario.getCpf())){
                throw new BusinessException("CPF já está em uso");
            }
        }

        //Atualizar todos os campos
        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setCpf(usuario.getCpf());
        usuarioExistente.setTelefone(usuario.getTelefone());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setSenha(usuario.getSenha());
        usuarioExistente.setPerfil(usuario.getPerfil());

        return usuarioRepository.save(usuarioExistente);
    }

    //Deletar usuario
    @Override
    @Transactional //Transação
    public void delete(Long id){
        if(!usuarioRepository.existsById(id))
            throw new BusinessException("Usuario nao encontrado. ID:" + id);
        usuarioRepository.deleteById(id);
    }


}


