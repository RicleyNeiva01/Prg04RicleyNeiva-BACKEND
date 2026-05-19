package br.com.ifba.prg04deskflow.controller;

import br.com.ifba.prg04deskflow.model.Usuario;
import br.com.ifba.prg04deskflow.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //Criar usuario
    // POST /usuarios — cria novo usuário, retorna 201
    @PostMapping
    public ResponseEntity<Usuario> criar(@RequestBody Usuario usuario){
        try {
            Usuario novo = usuarioService.salvar(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(novo);
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Listar usuarios
    // GET /usuarios — lista todos os usuários, retorna 200
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    //Buscar Usuario por id
    // GET /usuarios/{id} — busca usuário por ID, retorna 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Atualizar usuario
    // PUT /usuarios/{id} — atualiza usuário, retorna 200 ou 400
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Deletar usuario
    // DELETE /usuarios/{id} — deleta usuário, retorna 204 ou 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        try {
            usuarioService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
