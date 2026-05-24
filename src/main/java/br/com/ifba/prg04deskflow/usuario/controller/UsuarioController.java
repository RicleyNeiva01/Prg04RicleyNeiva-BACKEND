package br.com.ifba.prg04deskflow.usuario.controller;

import br.com.ifba.prg04deskflow.usuario.dto.UsuarioGetResponseDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPostRequestDTO;
import br.com.ifba.prg04deskflow.usuario.service.UsuarioIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioIService usuarioService;

    //Criar usuario
    // POST /usuarios — cria novo usuário, retorna 201
    @PostMapping
    public ResponseEntity<UsuarioGetResponseDTO> criar(@RequestBody UsuarioPostRequestDTO dto){

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(dto));
    }

    //Listar usuarios
    // GET /usuarios — lista todos os usuários, retorna 200
    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDTO>> listarTodos(){

        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    //Buscar Usuario por id
    // GET /usuarios/{id} — busca usuário por ID, retorna 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDTO> buscarPorId(@PathVariable Long id){

        return ResponseEntity.ok(usuarioService.buscarPorId(id));
    }

    //Atualizar usuario
    // PUT /usuarios/{id} — atualiza usuário, retorna 200 ou 400
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDTO> atualizar(@PathVariable Long id, @RequestBody UsuarioPostRequestDTO usuario){

        return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
    }

    //Deletar usuario
    // DELETE /usuarios/{id} — deleta usuário, retorna 204 ou 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){

            usuarioService.deletar(id);

            return ResponseEntity.noContent().build();
    }

}
