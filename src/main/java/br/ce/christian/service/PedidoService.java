package br.ce.christian.service;

import br.ce.christian.dtos.PedidoRequestDTO;
import br.ce.christian.dtos.PedidoResponseDTO;
import br.ce.christian.enums.PedidoStatus;
import br.ce.christian.exceptions.ClienteNotFoundException;
import br.ce.christian.exceptions.PedidoNotFoundException;
import br.ce.christian.exceptions.PedidoSemProdutoException;
import br.ce.christian.exceptions.ProdutoNotFoundException;
import br.ce.christian.model.Cliente;
import br.ce.christian.model.Pedido;
import br.ce.christian.model.Produto;
import br.ce.christian.repository.ClienteRepository;
import br.ce.christian.repository.PedidoRepository;
import br.ce.christian.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoResponseDTO findById(Long id){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        return new PedidoResponseDTO(pedido);
    }

    public PedidoResponseDTO criarPedido(PedidoRequestDTO pedidoRequestDTO) {

        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                        .orElseThrow(() -> new ClienteNotFoundException(pedidoRequestDTO.getClienteId()));

        if (pedidoRequestDTO.getProdutosIds() == null || pedidoRequestDTO.getProdutosIds().isEmpty()) {
            throw new PedidoSemProdutoException();
        }

        Pedido pedido = new Pedido();
        pedido.setData(pedidoRequestDTO.getData());
        pedido.setProdutos(buscarProdutosPorIds(pedidoRequestDTO.getProdutosIds()));
        pedido.setCliente(cliente);
        pedido.setStatus(PedidoStatus.CRIADO);

        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedidoSalvo);
    }

    public List<PedidoResponseDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAllWithProdutos();
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarPedidosDeDataMaior(LocalDate dataInicio){
        List<Pedido> pedidos = pedidoRepository.findByDataGreaterThanEqual(dataInicio);
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarPedidosDeDataMenor(LocalDate dataInicio){
        List<Pedido> pedidos = pedidoRepository.findByDataLessThanEqual(dataInicio);
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> pedidosSemDataEntrega(){
        List<Pedido> pedidos = pedidoRepository.findByDataIsNull();
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> pedidosComDataEntrega(){
        List<Pedido> pedidos = pedidoRepository.findByDataIsNotNull();
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> pedidosPorIntervaloDeDatas(LocalDate dataInicio, LocalDate dataFim){
        List<Pedido> pedidos = pedidoRepository.findByDataBetween(dataInicio, dataFim);
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public List<PedidoResponseDTO> buscarPedidosPorPeriodo(LocalDate dataInicio, LocalDate dataFim){
        List<Pedido> pedidos = pedidoRepository.buscarPedidosPorPeriodo(dataInicio, dataFim);
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    public void deletarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));
        pedidoRepository.delete(pedido);
    }

    public PedidoResponseDTO atualizarStatus(Long id, PedidoStatus novoStatus){
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new PedidoNotFoundException(id));

        pedido.setStatus(novoStatus);

        Pedido atualizado = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(atualizado);
    }

    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId){
        List<Pedido> pedidos = pedidoRepository.findByClienteId(clienteId);
        pedidos.forEach(this::carregarProdutos);
        return pedidos.stream().map(PedidoResponseDTO::new).collect(Collectors.toList());
    }

    private void carregarProdutos(Pedido pedido) {
        if (pedido.getProdutos() != null && !pedido.getProdutos().isEmpty()){
            List<Long> idsProdutos = pedido.getProdutos().stream().map(Produto::getId).collect(Collectors.toList());
            List<Produto> produtosCompletos = produtoRepository.findAllById(idsProdutos);
            pedido.setProdutos(produtosCompletos);
        }
    }

    private List<Produto> buscarProdutosPorIds(List<Long> produtosIds) {
        return produtosIds.stream()
                .map(id -> produtoRepository.findById(id)
                        .orElseThrow(() -> new ProdutoNotFoundException(id)))
                .collect(Collectors.toList());
    }
}
