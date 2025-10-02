package br.ce.christian.controller;

import br.ce.christian.dtos.FuncionarioRequestDTO;
import br.ce.christian.dtos.FuncionarioResponseDTO;
import br.ce.christian.service.FuncionarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FuncionarioResponseDTO> salvar(@RequestBody @Valid FuncionarioRequestDTO dto){
        FuncionarioResponseDTO criado = funcionarioService.salvar(dto);
        return ResponseEntity
                .created(URI.create("/funcionarios/" + criado.getId()))
                .body(criado);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    public ResponseEntity<FuncionarioResponseDTO> buscarPorId(@PathVariable Long id){
        FuncionarioResponseDTO funcionario = funcionarioService.buscarFuncionarioPorId(id);
        return ResponseEntity.ok(funcionario);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodos(){
        List<FuncionarioResponseDTO> funcionarios = funcionarioService.listar();
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<FuncionarioResponseDTO> atualizarFuncionario(@PathVariable Long id, @RequestBody FuncionarioRequestDTO dto){
        FuncionarioResponseDTO atualizado = funcionarioService.atualizarFuncionario(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar (@PathVariable Long id){
        funcionarioService.deletarFuncionario(id);
        return ResponseEntity.noContent().build();
    }


}
