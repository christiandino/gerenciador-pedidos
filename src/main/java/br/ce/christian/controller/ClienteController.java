package br.ce.christian.controller;

import br.ce.christian.dtos.ClienteRequestDTO;
import br.ce.christian.dtos.ClienteResponseDTO;
import br.ce.christian.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {


    private final ClienteService  clienteService;


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<ClienteResponseDTO> salvar(@RequestBody @Valid ClienteRequestDTO dto){

        ClienteResponseDTO cliente = clienteService.salvar(dto);
        return ResponseEntity
                .created(URI.create("/clientes/" + cliente.getId()))
                .body(cliente);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    public ResponseEntity<List<ClienteResponseDTO>> listar(){
        return ResponseEntity.ok(clienteService.listar());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR') or (hasRole('CLIENTE') and #id == principal.id)")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id){
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarPorId(id);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE') or (hasRole('CLIENTE') and #id == principal.id)")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO dto){
        ClienteResponseDTO atualizado = clienteService.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id){
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
