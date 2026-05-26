package br.com.ifba.prg04deskflow.usuario.controller;

import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioGetResponseDTO;
import br.com.ifba.prg04deskflow.usuario.dto.UsuarioPostRequestDTO;
import br.com.ifba.prg04deskflow.usuario.model.Usuario;
import br.com.ifba.prg04deskflow.usuario.service.UsuarioIService;
import jakarta.validation.Valid;
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
    private final ObjectMapperUtil objectMapperUtil;

    //Criar usuario
    // POST /usuarios — cria novo usuário, retorna 201
    @PostMapping
    public ResponseEntity<UsuarioGetResponseDTO> save(@RequestBody @Valid UsuarioPostRequestDTO dto){
        Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
        Usuario salvo = usuarioService.save(usuario);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salvo, UsuarioGetResponseDTO.class));
    }

    //Listar usuarios
    // GET /usuarios — lista todos os usuários, retorna 200
    @GetMapping
    public ResponseEntity<List<UsuarioGetResponseDTO>> findAll(){
        List<UsuarioGetResponseDTO> lista = usuarioService.findAll()
                .stream()
                .map(u -> objectMapperUtil.map(u, UsuarioGetResponseDTO.class))
                .toList();
        return ResponseEntity.ok(lista);
    }

    //Buscar Usuario por id
    // GET /usuarios/{id} — busca usuário por ID, retorna 200 ou 404
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDTO> findById(@PathVariable Long id){
        Usuario usuario = usuarioService.findById(id);

        return ResponseEntity.ok(objectMapperUtil.map(usuario, UsuarioGetResponseDTO.class));
    }

    //Atualizar usuario
    // PUT /usuarios/{id} — atualiza usuário, retorna 200 ou 400
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UsuarioPostRequestDTO dto){
        Usuario usuario = objectMapperUtil.map(dto, Usuario.class);
        Usuario atualizado = usuarioService.update(id, usuario);

        return ResponseEntity.ok(objectMapperUtil.map(atualizado, UsuarioGetResponseDTO.class));
    }

    //Deletar usuario
    // DELETE /usuarios/{id} — deleta usuário, retorna 204 ou 404
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){

            usuarioService.delete(id);

            return ResponseEntity.noContent().build();
    }

}
