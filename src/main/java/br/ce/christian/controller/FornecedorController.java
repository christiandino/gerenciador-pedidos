package br.ce.christian.controller;

import br.ce.christian.dtos.FornecedorRequestDTO;
import br.ce.christian.dtos.FornecedorResponseDTO;
import br.ce.christian.service.FornecedorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/fornecedores")
@RequiredArgsConstructor
public class FornecedorController {

    private final FornecedorService fornecedorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    public List<FornecedorResponseDTO> listar() {
        return fornecedorService.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    public ResponseEntity<FornecedorResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(fornecedorService.buscarPorId(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<FornecedorResponseDTO> salvar(@RequestBody @Valid FornecedorRequestDTO dto) {
        return ResponseEntity.ok(fornecedorService.salvar(dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        fornecedorService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // PUT http://localhost:8080/fornecedores/5/nome
    @PutMapping("/{id}/nome")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<FornecedorResponseDTO> atualizarNome(@PathVariable Long id, @RequestBody String novoNome){
        FornecedorResponseDTO atualizado = fornecedorService.atualizarNome(id, novoNome);
        return ResponseEntity.ok(atualizado);
    }

    //PUT http://localhost:8080/fornecedores/5/produtos/adicionar
    @PutMapping("/{id}/produtos/adicionar")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<FornecedorResponseDTO> adicionarProdutos(@PathVariable Long id, @RequestBody List<Long> produtosIds){
        FornecedorResponseDTO atualizado = fornecedorService.adicionarProdutos(id, produtosIds);
        return ResponseEntity.ok(atualizado);
    }

    // PUT http://localhost:8080/fornecedores/5/produtos/remover
    @PutMapping("/{id}/produtos/remover")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<FornecedorResponseDTO> removerProdutos(@PathVariable Long id, @RequestBody List<Long> produtosIds){
        FornecedorResponseDTO atualizado = fornecedorService.removerProdutos(id, produtosIds);
        return ResponseEntity.ok(atualizado);
    }

}
