package br.com.ifba.prg04deskflow.usuario.controller;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.infrastructure.security.JwtService;
import br.com.ifba.prg04deskflow.usuario.dto.LoginRequestDTO;
import br.com.ifba.prg04deskflow.usuario.dto.LoginResponseDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        Usuario usuario = usuarioRepository.findByEmail(dto.email())
                .orElseThrow(() -> new BusinessException("Email ou senha inválidos"));

        if (!passwordEncoder.matches(dto.senha(), usuario.getSenha()))
            throw new BusinessException("Email ou senha inválidos");

        if (Boolean.FALSE.equals(usuario.getAtivo()))
            throw new BusinessException("Usuário inativo");

        String token = jwtService.gerarToken(
                usuario.getEmail(),
                usuario.getPerfil().name(),
                usuario.getId(),
                usuario.getNome()
        );

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}