package br.ce.christian.service;


import br.ce.christian.dtos.CategoriaRequestDTO;
import br.ce.christian.dtos.CategoriaResponseDTO;
import br.ce.christian.dtos.ProdutoRequestDTO;
import br.ce.christian.dtos.ProdutoResponseDTO;
import br.ce.christian.exceptions.*;
import br.ce.christian.model.Categoria;
import br.ce.christian.model.Fornecedor;
import br.ce.christian.model.Produto;
import br.ce.christian.repository.CategoriaRepository;
import br.ce.christian.repository.FornecedorRepository;
import br.ce.christian.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final ProdutoRepository produtoRepository;
    private final FornecedorRepository fornecedorRepository;


    public List<CategoriaResponseDTO> listar(){
        return categoriaRepository.findAll().stream().map(CategoriaResponseDTO::new).collect(Collectors.toList());
    }

    public CategoriaResponseDTO buscarPorId(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        return new CategoriaResponseDTO(categoria);
    }

    public CategoriaResponseDTO salvar(CategoriaRequestDTO dto){
        String nome = dto.getNome();
        if (categoriaRepository.existsByNome(nome)){
            throw new CategoriaJaExistenteException(nome);
        }
        Categoria categoria = new Categoria();
        categoria.setNome(dto.getNome());
        Categoria salva = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(salva);
    }

    public CategoriaResponseDTO atualizar(Long id, CategoriaRequestDTO dto){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        categoria.setNome(dto.getNome());
        Categoria salva = categoriaRepository.save(categoria);
        return new CategoriaResponseDTO(salva);
    }

    public void deletar(Long id){
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException(id));
        categoriaRepository.delete(categoria);
    }

    public List<Categoria> categoriasComMaisDeProdutos(Long numero) {
        if (numero <= 0){
            throw new ParametroCategoriaNumeroInvalidoException(numero);
        }
        return categoriaRepository.categoriasComMaisDe(numero);
    }

    public ProdutoResponseDTO adicionarProdutoNaCategoria(Long categoriaId, ProdutoRequestDTO produtoDTO){
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));

        String nomeProduto = produtoDTO.getNome();

        if (produtoRepository.existsByNomeAndCategoriaId(nomeProduto,categoriaId)){
            throw new ProdutoJaExistenteNaCategoriaException(nomeProduto, categoria.getNome());
        }

        Fornecedor fornecedor = fornecedorRepository.findById(produtoDTO.getFornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(produtoDTO.getFornecedorId()));

        Produto produto = new Produto();
        produto.setNome(produtoDTO.getNome());
        produto.setPreco(produtoDTO.getPreco());
        produto.setFornecedor(fornecedor);
        produto.setCategoria(categoria);

        categoria.addProduto(produto);
        categoriaRepository.save(categoria);

        return new ProdutoResponseDTO(produto);
    }

    public void removerProdutoDaCategoria(Long categoriaId, Long produtoId){
        Categoria categoria = categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new CategoriaNotFoundException(categoriaId));

        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new ProdutoNotFoundException(produtoId));

        if (!produto.getCategoria().equals(categoria)) {
            throw new ProdutoNaoPertenceCategoriaException(produtoId, categoriaId);
        }

        categoria.removeProduto(produto);
        categoriaRepository.save(categoria);
    }
}
