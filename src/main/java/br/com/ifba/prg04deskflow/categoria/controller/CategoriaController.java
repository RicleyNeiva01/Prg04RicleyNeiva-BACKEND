package br.com.ifba.prg04deskflow.categoria.controller;

import br.com.ifba.prg04deskflow.categoria.dto.CategoriaGetResponseDTO;
import br.com.ifba.prg04deskflow.categoria.dto.CategoriaPostRequestDTO;
import br.com.ifba.prg04deskflow.categoria.model.Categoria;
import br.com.ifba.prg04deskflow.categoria.service.CategoriaIService;
import br.com.ifba.prg04deskflow.infrastructure.mapper.ObjectMapperUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaIService categoriaService;
    private final ObjectMapperUtil objectMapperUtil;

    // Criar categoria (POST /categorias)
    @PostMapping
    public ResponseEntity<CategoriaGetResponseDTO> save(@RequestBody @Valid CategoriaPostRequestDTO dto){
        Categoria categoria = objectMapperUtil.map(dto, Categoria.class);
        Categoria salva = categoriaService.save(categoria);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(objectMapperUtil.map(salva, CategoriaGetResponseDTO.class));
    }

    // Listar categorias de forma paginada (GET /categorias)
    @GetMapping
    public ResponseEntity<Page<CategoriaGetResponseDTO>> findAll(Pageable pageable) {
        Page<CategoriaGetResponseDTO> pagina = categoriaService.findAll(pageable)
                .map(cat -> objectMapperUtil.map(cat, CategoriaGetResponseDTO.class));
        return ResponseEntity.ok(pagina);
    }

    // Buscar categoria por ID (GET /categorias/{id})
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaGetResponseDTO> findById(@PathVariable Long id) {
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(objectMapperUtil.map(categoria, CategoriaGetResponseDTO.class));
    }

    // Atualizar categoria (PUT /categorias/{id})
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaGetResponseDTO> update(@PathVariable Long id, @RequestBody @Valid CategoriaPostRequestDTO dto){
        Categoria categoria = objectMapperUtil.map(dto, Categoria.class);
        Categoria atualizada = categoriaService.update(id, categoria);

        return ResponseEntity.ok(objectMapperUtil.map(atualizada, CategoriaGetResponseDTO.class));
    }

    // Deletar categoria (DELETE /categorias/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoriaService.delete(id);

        return ResponseEntity.noContent().build();
    }

}
