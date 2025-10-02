package br.ce.christian.controller;

import br.ce.christian.dtos.PedidoRequestDTO;
import br.ce.christian.dtos.PedidoResponseDTO;
import br.ce.christian.enums.PedidoStatus;
import br.ce.christian.repository.PedidoRepository;
import br.ce.christian.repository.ProdutoRepository;
import br.ce.christian.service.PedidoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final PedidoService pedidoService;
    private final ProdutoRepository produtoRepository;

    // GET http://localhost:8080/pedidos/com-produtos
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR')")
    public List<PedidoResponseDTO> listarPedidosComProdutos() {
        return pedidoService.listarPedidos();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(@PathVariable Long id){
        PedidoResponseDTO pedidoDTO = pedidoService.findById(id);
        if (pedidoDTO != null) {
            return ResponseEntity.ok(pedidoDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // GET http://localhost:8080/pedidos/data-maior?dataInicio=2025-08-07
    @GetMapping("/data-maior")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> listarPedidosDeDataMaior(@RequestParam LocalDate dataInicio){
        return pedidoService.buscarPedidosDeDataMaior(dataInicio);
    }

    // GET http://localhost:8080/pedidos/data-menor?dataInicio=2025-08-07
    @GetMapping("/data-menor")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> listarPedidosDeDataMenor(@RequestParam LocalDate dataInicio){
        return pedidoService.buscarPedidosDeDataMenor(dataInicio);
    }

    // GET http://localhost:8080/pedidos/sem-data
    @GetMapping("/sem-data")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> listarPedidosSemDataEntrega(){
        return pedidoService.pedidosSemDataEntrega();
    }

    // GET http://localhost:8080/pedidos/com-data
    @GetMapping("/com-data")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> listarPedidosComDataEntrega(){
        return pedidoService.pedidosComDataEntrega();
    }

    // GET http://localhost:8080/pedidos/intervalo?dataInicio=2025-08-07&dataFim=2025-08-08
    @GetMapping("/intervalo")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> listarPedidosPorIntervalo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim){
        return pedidoService.pedidosPorIntervaloDeDatas(dataInicio, dataFim);
    }

    // GET http://localhost:8080/pedidos/periodo?dataInicio=2025-08-07&dataFim=2025-08-08
    @GetMapping("/periodo")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public List<PedidoResponseDTO> buscarPedidosPorPeriodo(
            @RequestParam LocalDate dataInicio,
            @RequestParam LocalDate dataFim){
        return pedidoService.buscarPedidosPorPeriodo(dataInicio, dataFim);
    }

    // GET http://localhost:8080/pedidos/cliente/1
    @GetMapping("/cliente/{clienteId}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<PedidoResponseDTO> buscarPedidosPorCliente(@PathVariable Long clienteId){
        return pedidoService.buscarPedidosPorCliente(clienteId);
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public PedidoResponseDTO salvar(@RequestBody @Valid PedidoRequestDTO pedidoRequestDTO) {
        return pedidoService.criarPedido(pedidoRequestDTO);
    }


    @PutMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<PedidoResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestParam String status) {

        PedidoStatus novoStatus;
        try {
            novoStatus = PedidoStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }

        PedidoResponseDTO atualizado = pedidoService.atualizarStatus(id, novoStatus);
        return ResponseEntity.ok(atualizado);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<Void> deletar (@PathVariable Long id){
        try {
            pedidoService.deletarPedido(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
