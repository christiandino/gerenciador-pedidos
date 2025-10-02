package br.ce.christian.service;


import br.ce.christian.dtos.FornecedorRequestDTO;
import br.ce.christian.dtos.FornecedorResponseDTO;
import br.ce.christian.exceptions.*;
import br.ce.christian.model.Fornecedor;
import br.ce.christian.model.Produto;
import br.ce.christian.repository.FornecedorRepository;
import br.ce.christian.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;
    private final ProdutoRepository produtoRepository;

    public List<FornecedorResponseDTO> listar(){
        return fornecedorRepository.findAll().stream().map(FornecedorResponseDTO::new).collect(Collectors.toList());
    }

    public FornecedorResponseDTO buscarPorId(Long id){
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
        return new FornecedorResponseDTO(fornecedor);
    }

    public FornecedorResponseDTO salvar(FornecedorRequestDTO dto){
        if (fornecedorRepository.existsByNome(dto.getNome())) {
            throw new FornecedorJaExistenteException(dto.getNome());
        }

        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setNome(dto.getNome());
        // iniciar lista vazia de produtos
        fornecedor.setProdutos(List.of());

        Fornecedor salvo = fornecedorRepository.save(fornecedor);
        return new FornecedorResponseDTO(salvo);
    }

    public void deletar(Long id){
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));
        fornecedorRepository.delete(fornecedor);
    }

    public FornecedorResponseDTO atualizarNome(Long id, String novoNome) {
        Fornecedor fornecedor = fornecedorRepository.findById(id)
                .orElseThrow(() -> new FornecedorNotFoundException(id));

        // Validação de nome duplicado
        if (fornecedorRepository.existsByNome(novoNome) && !fornecedor.getNome().equals(novoNome)) {
            throw new FornecedorJaExistenteException(novoNome);
        }

        fornecedor.setNome(novoNome);
        Fornecedor atualizado = fornecedorRepository.save(fornecedor);
        return new FornecedorResponseDTO(atualizado);
    }

    public FornecedorResponseDTO adicionarProdutos(Long fornecedorId, List<Long> produtosIds) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new FornecedorNotFoundException(fornecedorId));

        List<Produto> produtosParaAdicionar = produtosIds.stream()
                .map(produtoId -> produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ProdutoNotFoundException(produtoId)))
                .peek(p -> {
                    if (p.getFornecedor() != null && !p.getFornecedor().equals(fornecedor)) {
                        throw new ProdutoJaPertenceAOutroFornecedorException(p.getId(), p.getFornecedor().getNome());
                    }
                })
                .collect(Collectors.toList());

        // Adiciona sem remover os produtos existentes
        fornecedor.getProdutos().addAll(produtosParaAdicionar);

        // Atualiza o fornecedor nos produtos
        produtosParaAdicionar.forEach(p -> p.setFornecedor(fornecedor));

        Fornecedor atualizado = fornecedorRepository.save(fornecedor);
        return new FornecedorResponseDTO(atualizado);
    }

    public FornecedorResponseDTO removerProdutos(Long fornecedorId, List<Long> produtosIds) {
        Fornecedor fornecedor = fornecedorRepository.findById(fornecedorId)
                .orElseThrow(() -> new FornecedorNotFoundException(fornecedorId));

        List<Produto> produtosParaRemover = produtosIds.stream()
                .map(produtoId -> produtoRepository.findById(produtoId)
                        .orElseThrow(() -> new ProdutoNotFoundException(produtoId)))
                .peek(p -> {
                    if (!fornecedor.getProdutos().contains(p)) {
                        throw new ProdutoNaoPertenceFornecedorException(p.getId(), fornecedorId);
                    }
                })
                .collect(Collectors.toList());

        fornecedor.getProdutos().removeAll(produtosParaRemover);

        // Remove a referência do fornecedor nos produtos
        produtosParaRemover.forEach(p -> p.setFornecedor(null));

        Fornecedor atualizado = fornecedorRepository.save(fornecedor);
        return new FornecedorResponseDTO(atualizado);
    }
}
