package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioGetResponseDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPostRequestDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    private final UsuarioRepository usuarioRepository;
    private final ObjectMapperUtil objectMapperUtil;

    //Salvar usuario, com validações
    @Override
    public UsuarioGetResponseDTO salvar(UsuarioPostRequestDTO dto){
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new BusinessException("O Email ja existe no sistema");
        }

        Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
        Usuario salvo = usuarioRepository.save(usuario);

        return objectMapperUtil.map(salvo, UsuarioGetResponseDTO.class);
    }

    //Lista todos os usuarios
    @Override
    public List <UsuarioGetResponseDTO> listarTodos(){
        return usuarioRepository.findAll()
                .stream()
                .map(u -> objectMapperUtil.map(u, UsuarioGetResponseDTO.class))
                .toList();
    }

    //Busca um ID
    @Override
    public UsuarioGetResponseDTO buscarPorId(Long id){
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID:" + id));

        return objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class);
    }

    //Atualizar usuario
    @Override
    public UsuarioGetResponseDTO atualizar(Long id, UsuarioPostRequestDTO dto){
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID:" + id));

        if(!usuarioExistente.getEmail().equals(dto.getEmail())){
            if(usuarioRepository.existsByEmail(dto.getEmail())){
                throw new BusinessException("Email ja esta em uso");
            }
        }

        usuarioExistente.setNome(dto.getNome());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setSenha(dto.getSenha());

        return objectMapperUtil.map(usuarioRepository.save(usuarioExistente), UsuarioGetResponseDTO.class);
    }

    //Deletar usuario
    @Override
    public void deletar(Long id){
        if(!usuarioRepository.existsById(id))
            throw new BusinessException("Usuario nao encontrado. ID:" + id);
        usuarioRepository.deleteById(id);
    }


}


