package br.com.ifba.prg04deskflow.tecnico.service;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.tecnico.model.Tecnico;
import br.com.ifba.prg04deskflow.tecnico.repository.TecnicoRepository;
import br.com.ifba.prg04deskflow.usuario.model.PerfilUsuario;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TecnicoService implements TecnicoIService{

    private final TecnicoRepository tecnicoRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Tecnico save(Tecnico tecnico) {

        //valida CPF duplicado
        if (tecnicoRepository.existsByCpf(tecnico.getCpf())) {
            throw new BusinessException("CPF já cadastrado.");
        }

        // Garante que o perfil seja de Técnico antes de salvar
        tecnico.setPerfil(PerfilUsuario.TECNICO);
        tecnico.setAtivo(true);

        return tecnicoRepository.save(tecnico);
    }

    @Override
    public Page<Tecnico> findAll(boolean mostrarInativos, Pageable pageable) {
        if (mostrarInativos) {
            return tecnicoRepository.findAll(pageable);
        }
        return tecnicoRepository.findByAtivoTrue(pageable);
    }

    @Override
    public Tecnico findById(Long id) {

        return tecnicoRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Técnico não encontrado. ID: " + id));
    }

    @Override
    @Transactional
    public Tecnico update(Long id, Tecnico tecnicoAlterado) {
        Tecnico tecnicoExistente = findById(id);

        // Garante que continua sendo técnico
        tecnicoExistente.setPerfil(PerfilUsuario.TECNICO);

        if (!tecnicoExistente.getCpf().equals(tecnicoAlterado.getCpf()) &&
                tecnicoRepository.existsByCpf(tecnicoAlterado.getCpf())) {
            throw new BusinessException("Este CPF já está cadastrado para outro técnico.");
        }

        // Atualiza os campos que vieram da herança de Usuario
        tecnicoExistente.setNome(tecnicoAlterado.getNome());
        tecnicoExistente.setCpf(tecnicoAlterado.getCpf());
        tecnicoExistente.setTelefone(tecnicoAlterado.getTelefone());
        tecnicoExistente.setEmail(tecnicoAlterado.getEmail());

        // Só atualiza a senha se uma nova for enviada (AGORA COM CRIPTOGRAFIA 🔒)
        if (tecnicoAlterado.getSenha() != null && !tecnicoAlterado.getSenha().isBlank()) {
            tecnicoExistente.setSenha(passwordEncoder.encode(tecnicoAlterado.getSenha()));
        }

        // Atualiza o campo específico do Técnico
        tecnicoExistente.setEspecialidade(tecnicoAlterado.getEspecialidade());

        return tecnicoRepository.save(tecnicoExistente);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Tecnico tecnico = findById(id);

        tecnico.setAtivo(false);

        tecnicoRepository.save(tecnico);
    }

    @Override
    public Page<Tecnico> findByNome(String nome, boolean mostrarInativos, Pageable pageable) {
        if (mostrarInativos) {
            return tecnicoRepository.findByNomeContainingIgnoreCase(nome.trim(), pageable);
        }
        return tecnicoRepository.findByNomeContainingIgnoreCaseAndAtivoTrue(nome.trim(), pageable);
    }

    @Override
    @Transactional
    public void reativarTecnico(Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Técnico não encontrado com o ID: " + id));

        tecnico.setAtivo(true); // Ou o nome do campo que você usa para controlar se está ativo (ex: status, habilitado, etc)
        tecnicoRepository.save(tecnico);
    }
}
