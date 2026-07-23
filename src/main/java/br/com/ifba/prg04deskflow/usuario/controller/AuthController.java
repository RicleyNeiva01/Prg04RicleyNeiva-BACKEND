package br.com.ifba.prg04deskflow.usuario.controller;

import br.com.ifba.prg04deskflow.infrastructure.exception.BusinessException;
import br.com.ifba.prg04deskflow.infrastructure.security.JwtService;
import br.com.ifba.prg04deskflow.usuario.dto.LoginRequestDTO;
import br.com.ifba.prg04deskflow.usuario.dto.LoginResponseDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final JavaMailSender mailSender; // 👇 INJETADO AQUI!

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

    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        // Procura o utilizador na base de dados
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "E-mail não encontrado."));
        }

        Usuario usuario = usuarioOpt.get();

        // Gera um código aleatório de 6 dígitos
        String codigo = String.format("%06d", new Random().nextInt(999999));
        usuario.setCodigoRecuperacao(codigo);
        usuarioRepository.save(usuario);

        // 👇 AQUI FAZEMOS O ENVIO REAL DO E-MAIL
        try {
            SimpleMailMessage mensagem = new SimpleMailMessage();
            mensagem.setTo(email);
            mensagem.setSubject("Recuperação de Senha - DeskFlow");

            mensagem.setText("Olá, " + usuario.getNome() + "!\n\n" +
                    "Você solicitou a recuperação de senha no DeskFlow.\n" +
                    "Seu código de segurança é: " + codigo + "\n\n" +
                    "Se não foi você que solicitou, por favor ignore este e-mail.");

            mailSender.send(mensagem);

        } catch (Exception e) {
            System.err.println("Erro ao enviar e-mail: " + e.getMessage());
            return ResponseEntity.internalServerError().body(Map.of("message", "Erro ao enviar o e-mail de recuperação. Verifique as configurações do servidor."));
        }

        return ResponseEntity.ok(Map.of("message", "Código enviado para o seu e-mail."));
    }

    // 2. Rota para validar o código e atualizar a palavra-passe
    @PostMapping("/resetar-senha")
    public ResponseEntity<?> resetarSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String codigo = request.get("codigo");
        String novaSenha = request.get("novaSenha");

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "E-mail não encontrado."));
        }

        Usuario usuario = usuarioOpt.get();

        // Verifica se o código existe e se é igual ao que o utilizador enviou
        if (usuario.getCodigoRecuperacao() == null || !usuario.getCodigoRecuperacao().equals(codigo)) {
            return ResponseEntity.badRequest().body(Map.of("message", "Código inválido ou expirado."));
        }

        // Encripta a nova palavra-passe, atualiza no utilizador e limpa o código usado
        usuario.setSenha(passwordEncoder.encode(novaSenha));
        usuario.setCodigoRecuperacao(null); // Limpamos para que não possa ser usado duas vezes
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("message", "Senha alterada com sucesso!"));
    }

}