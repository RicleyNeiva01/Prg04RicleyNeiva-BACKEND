package br.com.ifba.prg04deskflow.usuario.repository;

import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    //Busca usuario pelo email(usado para validar duplicidade)
    Optional <Usuario> findByEmail(String email);

    //Verifica se o email ja existe
    boolean existsByEmail(String email);

    //Verifica se o cpf ja existe
    boolean existsByCpf(String cpf);

}
