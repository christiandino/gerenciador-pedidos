package br.ce.christian.service;


import br.ce.christian.dtos.ProdutoRequestDTO;
import br.ce.christian.dtos.ProdutoResponseDTO;
import br.ce.christian.exceptions.CategoriaNotFoundException;
import br.ce.christian.exceptions.FornecedorNotFoundException;
import br.ce.christian.exceptions.ProdutoNotFoundException;
import br.ce.christian.exceptions.ProdutoNotFoundStringException;
import br.ce.christian.model.Categoria;
import br.ce.christian.model.Fornecedor;
import br.ce.christian.model.Produto;
import br.ce.christian.repository.CategoriaRepository;
import br.ce.christian.repository.FornecedorRepository;
import br.ce.christian.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;

    public ProdutoResponseDTO salvar(ProdutoRequestDTO dto){
        Produto produto = new Produto();
        produto.setNome(dto.getNome());
        produto.setPreco(dto.getPreco());

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                .orElseThrow(() -> new CategoriaNotFoundException(dto.getCategoriaId()));
        produto.setCategoria(categoria);

        Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                .orElseThrow(() -> new FornecedorNotFoundException(dto.getFornecedorId()));
        produto.setFornecedor(fornecedor);

        Produto salvo = produtoRepository.save(produto);
        return new ProdutoResponseDTO(salvo);
    }

    public ProdutoResponseDTO buscarPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        return new ProdutoResponseDTO(produto);
    }

    public Produto atualizar(Long id, ProdutoRequestDTO dto) {
        // Busca o produto existente
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        // Atualiza os campos simples
        produtoExistente.setNome(dto.getNome());
        produtoExistente.setPreco(dto.getPreco());

        // Atualiza a categoria
        if (dto.getCategoriaId() != null) {
            Categoria categoria = categoriaRepository.findById(dto.getCategoriaId())
                    .orElseThrow(() -> new CategoriaNotFoundException(dto.getCategoriaId()));
            produtoExistente.setCategoria(categoria);
        }

        // Atualiza o fornecedor
        if (dto.getFornecedorId() != null) {
            Fornecedor fornecedor = fornecedorRepository.findById(dto.getFornecedorId())
                    .orElseThrow(() -> new FornecedorNotFoundException(dto.getFornecedorId()));
            produtoExistente.setFornecedor(fornecedor);
        }

        return produtoRepository.save(produtoExistente);
    }

    public void deletar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException(id));

        produtoRepository.delete(produto);
    }

    public List<ProdutoResponseDTO> listar() {
        return produtoRepository.findAll().stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> listarTop3(){
        return produtoRepository.findTop3ByOrderByPrecoDesc().stream()
                .map(ProdutoResponseDTO::new)
                .collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorNome(String nome){
        List<Produto> produtosFiltrados = produtoRepository.findByNomeEqualsIgnoreCase(nome);
        return  produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorNome2(String nome) {
        List<Produto> produtosFiltrados = produtoRepository.findByNomeEqualsIgnoreCase(nome);
        if (produtosFiltrados.isEmpty()) {
            throw new ProdutoNotFoundStringException("Nenhum produto encontrado com o nome: " + nome);
        }
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorCategoria(String categoria){
        List<Produto> produtosFiltrados = produtoRepository.findByCategoria_NomeEqualsIgnoreCase(categoria);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorPrecoMaiorQue(Double preco){
        List<Produto> produtosFiltrados = produtoRepository.findByPrecoGreaterThan(preco);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorPrecoMenorQue(Double preco){
        List<Produto> produtosFiltrados = produtoRepository.findByPrecoLessThan(preco);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorNomeParcial(String nome){
        List<Produto> produtosFiltrados = produtoRepository.findByNomeContainingIgnoreCase(nome);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorCategoriaPrecoCrescente(String categoria){
        List<Produto> produtosFiltrados = produtoRepository.findByCategoria_NomeOrderByPrecoAsc(categoria);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtoPorCategoriaPrecoDecrescente(String categoria){
        List<Produto> produtosFiltrados = produtoRepository.findByCategoria_NomeOrderByPrecoDesc(categoria);
        return produtosFiltrados.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public long produtosPorCategoria(String categoria){
        return produtoRepository.countByCategoria_NomeEqualsIgnoreCase(categoria);
    }

    public long contarProdutosPorCategoriaId(Long categoriaId) {
        return produtoRepository.countByCategoria_Id(categoriaId);
    }

    public long contarProdutosPorPrecoMaiorQue(Double preco){
        return produtoRepository.countByPrecoGreaterThan(preco);
    }

    public List<ProdutoResponseDTO> produtosPorPrecoMenorQueOuNomeParcial(Double preco, String nome){
        List<Produto> produtosFiltrado = produtoRepository.findByPrecoLessThanOrNomeContainingIgnoreCase(preco, nome);
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> produtosTop5ProdutosMaisBaratoPorCategoria(String categoria){
        List<Produto> produtosFiltrado = produtoRepository.findTop5ByCategoria_NomeOrderByPrecoAsc(categoria);
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> buscarPorPrecoMaior(Double preco){
        List<Produto> produtosFiltrado = produtoRepository.buscarPorPrecoMaior(preco);
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> buscarProdutosOrderCrescente(){
        List<Produto> produtosFiltrado = produtoRepository.buscarProdutosOrderCrescente();
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> buscarProdutosOrderDecrescente(){
        List<Produto> produtosFiltrado = produtoRepository.buscarProdutosOrderDecrescente();
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public Double mediaPrecos(){
        return produtoRepository.mediaPrecos();
    }

    public List<ProdutoResponseDTO> buscarProdutosPorLetraInicial(String letra){
        List<Produto> produtosFiltrado = produtoRepository.buscarProdutosPorLetraInicial(letra);
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public Double maxPrecoProdutoPorCategoria(String categoria){
        return produtoRepository.maxPrecoProdutoPorCategoria(categoria);
    }


    public List<Object[]> contarProdutosPorTodasCategorias() {
        return produtoRepository.contarProdutosPorTodasCategorias();
    }

    public List<ProdutoResponseDTO> buscarProdutosFiltrados(String nome, String categoria) {

        // Transforma strings vazias em null para não atrapalhar o filtro
        nome = (nome != null && !nome.trim().isEmpty()) ? nome.trim() : null;
        categoria = (categoria != null && !categoria.trim().isEmpty()) ? categoria.trim() : null;

        List<Produto> produtosFiltrado = produtoRepository.buscarProdutosFiltrados(nome, categoria);
        return produtosFiltrado.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    public List<ProdutoResponseDTO> buscarTop5ProdutosMaisCaros() {
        List<Produto> produtos = produtoRepository.top5ProdutosMaisCaros();
        return produtos.stream().map(ProdutoResponseDTO::new).collect(Collectors.toList());
    }

    // Método privado que carrega Categoria e Fornecedor completos do banco pelo id
    private void carregarEntidadesRelacionadas(Produto produto) {
        if (produto.getCategoria() != null && produto.getCategoria().getId() != null) {
            Optional<Categoria> categoriaOpt = categoriaRepository.findById(produto.getCategoria().getId());
            categoriaOpt.ifPresent(produto::setCategoria);
        }

        if (produto.getFornecedor() != null && produto.getFornecedor().getId() != null) {
            Optional<Fornecedor> fornecedorOpt = fornecedorRepository.findById(produto.getFornecedor().getId());
            fornecedorOpt.ifPresent(produto::setFornecedor);
        }
    }
}
