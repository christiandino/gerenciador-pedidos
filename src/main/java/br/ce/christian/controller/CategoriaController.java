package br.ce.christian.controller;

import br.ce.christian.dtos.CategoriaRequestDTO;
import br.ce.christian.dtos.CategoriaResponseDTO;
import br.ce.christian.dtos.ProdutoRequestDTO;
import br.ce.christian.dtos.ProdutoResponseDTO;
import br.ce.christian.model.Categoria;
import br.ce.christian.repository.CategoriaRepository;
import br.ce.christian.repository.ProdutoRepository;
import br.ce.christian.service.CategoriaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaService categoriaService;
    private final ProdutoRepository produtoRepository;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<CategoriaResponseDTO> listar(){
        return categoriaService.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR', 'CLIENTE')")
    public ResponseEntity<CategoriaResponseDTO> buscarPorId(@PathVariable Long id){
        return ResponseEntity.ok(categoriaService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<CategoriaResponseDTO> salvar(@RequestBody @Valid CategoriaRequestDTO dto){
        return ResponseEntity.ok(categoriaService.salvar(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<CategoriaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaRequestDTO dto) {
        return ResponseEntity.ok(categoriaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }


    // GET http://localhost:8080/categorias/mais-de?numero=10
    @GetMapping("/mais-de")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public List<Categoria> categoriasComMaisDeProdutos(@RequestParam Long numero){
        return categoriaService.categoriasComMaisDeProdutos(numero);
    }

    @PostMapping("/{categoriaId}/produtos")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ProdutoResponseDTO> adicionarProdutoNaCategoria(
            @PathVariable Long categoriaId,
            @RequestBody ProdutoRequestDTO produtoDTO){
        ProdutoResponseDTO novoProduto = categoriaService.adicionarProdutoNaCategoria(categoriaId, produtoDTO);
        return ResponseEntity.ok(novoProduto);
    }

    @DeleteMapping("/{categoriaId}/produtos/{produtoId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<String> removerProdutoDaCategoria(@PathVariable Long categoriaId, @PathVariable Long produtoId){
        categoriaService.removerProdutoDaCategoria(categoriaId, produtoId);
        return ResponseEntity.ok("Produto removido da categoria com sucesso!");
    }



}