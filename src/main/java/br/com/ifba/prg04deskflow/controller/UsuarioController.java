package br.com.ifba.prg04deskflow.controller;

import br.com.ifba.prg04deskflow.model.Usuario;
import br.com.ifba.prg04deskflow.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    //Criar usuario
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
    @GetMapping
    public ResponseEntity<List<Usuario>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    //Buscar Usuario por id
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id){
        try {
            return ResponseEntity.ok(usuarioService.buscarPorId(id));
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    //Atualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizar(@PathVariable Long id, @RequestBody Usuario usuario){
        try {
            return ResponseEntity.ok(usuarioService.atualizar(id, usuario));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().build();
        }
    }

    //Deletar usuario
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
