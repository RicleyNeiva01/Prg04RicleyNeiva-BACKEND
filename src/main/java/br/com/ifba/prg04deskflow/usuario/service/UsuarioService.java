package br.com.ifba.prg04deskflow.usuario.service;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService implements UsuarioIService{

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder; // adiciona isso

    @Override
    @Transactional
    public Usuario save(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail()))
            throw new BusinessException("O Email ja existe no sistema");

        if (usuarioRepository.existsByCpf(usuario.getCpf()))
            throw new BusinessException("O CPF já existe no sistema");

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // adiciona isso
        return usuarioRepository.save(usuario);
    }

    //Lista todos os usuarios
    @Override
    public Page<Usuario> findAll(boolean mostrarInativos, Pageable pageable) {

        if (mostrarInativos) {
            return usuarioRepository.findAll(pageable);
        }

        return usuarioRepository.findByAtivoTrue(pageable);
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
    public Usuario update(Long id, Usuario usuario) {
        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario não encontrado. ID:" + id));

        if (Boolean.FALSE.equals(usuarioExistente.getAtivo()))
            throw new BusinessException("Não é possível atualizar um usuário inativo");

        if (!usuarioExistente.getEmail().equals(usuario.getEmail()))
            if (usuarioRepository.existsByEmail(usuario.getEmail()))
                throw new BusinessException("Email ja esta em uso");

        if (!usuarioExistente.getCpf().equals(usuario.getCpf()))
            if (usuarioRepository.existsByCpf(usuario.getCpf()))
                throw new BusinessException("CPF já está em uso");

        usuarioExistente.setNome(usuario.getNome());
        usuarioExistente.setCpf(usuario.getCpf());
        usuarioExistente.setTelefone(usuario.getTelefone());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setPerfil(usuario.getPerfil());

        if (usuario.getSenha() != null && !usuario.getSenha().isBlank())
            usuarioExistente.setSenha(passwordEncoder.encode(usuario.getSenha())); // criptografa aqui também

        return usuarioRepository.save(usuarioExistente);
    }

    //Deletar usuario
    @Override
    @Transactional //Transação
    public void delete(Long id){
        // Buscamos o usuário no banco
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuario nao encontrado. ID:" + id));

        if(Boolean.FALSE.equals(usuario.getAtivo())){
            throw new BusinessException("Usuário já está inativo");
        }

        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }

    @Override
    public Page<Usuario> findByNome(String nome, boolean mostrarInativos, Pageable pageable) {

        if (mostrarInativos) {
            return usuarioRepository.findByNomeContainingIgnoreCase(nome.trim(), pageable);
        }

        return usuarioRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome.trim(), pageable);
    }

    @Override
    @Transactional // Garante a transação segura com o banco de dados
    public void reativar(Long id) {
        // Busca o usuário no banco ou lança erro se não existir
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado. ID: " + id));

        // Validação de segurança: se já estiver ativo, não faz nada
        if (Boolean.TRUE.equals(usuario.getAtivo())) {
            throw new BusinessException("O usuário já está ativo.");
        }

        // Altera para ativo e salva
        usuario.setAtivo(true);
        usuarioRepository.save(usuario);
    }

}


