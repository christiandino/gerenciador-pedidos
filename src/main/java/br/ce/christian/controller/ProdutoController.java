package br.ce.christian.controller;

import br.ce.christian.dtos.ProdutoRequestDTO;
import br.ce.christian.dtos.ProdutoResponseDTO;
import br.ce.christian.model.Produto;
import br.ce.christian.repository.CategoriaRepository;
import br.ce.christian.repository.FornecedorRepository;
import br.ce.christian.repository.ProdutoRepository;
import br.ce.christian.service.ProdutoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoRepository produtoRepository;
    private final CategoriaRepository categoriaRepository;
    private final FornecedorRepository fornecedorRepository;
    private final ProdutoService produtoService;

    // GET http://localhost:8080/produtos/produtos
    @GetMapping//("/produtos")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> listarProdutos() {
        return produtoService.listar();
    }

    // GET http://localhost:8080/produtos/top3
    @GetMapping("/top3")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> listarTop3(){
        return produtoService.listarTop3();
    }


    // GET http://localhost:8080/produtos/22
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Long id) {
        ProdutoResponseDTO dto = produtoService.buscarPorId(id);
        return ResponseEntity.ok(dto);
    }


    // GET http://localhost:8080/produtos/por-nome?nome=Notebook
    @GetMapping("/por-nome")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorNome(@RequestParam String nome){
        return produtoService.produtoPorNome(nome);
    }

    // GET http://localhost:8080/produtos/por-categoria?categoria=Escritorio
    @GetMapping("/por-categoria")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorCategoria(@RequestParam String categoria){
        return produtoService.produtoPorCategoria(categoria);
    }

    // GET http://localhost:8080/produtos/por-preco-maior?preco=4000
    @GetMapping("/por-preco-maior")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorPrecoMaiorQue(@RequestParam Double preco){
        return produtoService.produtoPorPrecoMaiorQue(preco);
    }

    // GET http://localhost:8080/produtos/por-preco-menor?preco=4000
    @GetMapping("/por-preco-menor")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorPrecoMenorQue(@RequestParam Double preco){
        return produtoService.produtoPorPrecoMenorQue(preco);
    }

    // GET http://localhost:8080/produtos/por-nome-parcial?nome=Cel
    @GetMapping("/por-nome-parcial")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorNomeParcial(@RequestParam String nome){
        return produtoService.produtoPorNomeParcial(nome);
    }

    // GET http://localhost:8080/produtos/produto-cat-preco-asc?categoria=Livros
    @GetMapping("/produto-cat-preco-asc")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutoPorCategoriaPrecoAsc(@RequestParam String categoria){
        return produtoService.produtoPorCategoriaPrecoCrescente(categoria);
    }

    // GET http://localhost:8080/produtos/produto-cat-preco-desc?categoria=Livros
    @GetMapping("/produto-cat-preco-desc")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutoPorCategoriaPrecoDesc(@RequestParam String categoria){
        return produtoService.produtoPorCategoriaPrecoDecrescente(categoria);
    }

    // GET http://localhost:8080/produtos/produtos-por-categoria?categoria=Livros
    @GetMapping("/produtos-por-categoria")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public long produtosPorCategoria(@RequestParam String categoria){
        return produtoService.produtosPorCategoria(categoria);
    }

    // GET http://localhost:8080/produtos/produtos-por-categoria-id?id=2
    @GetMapping("/produtos-por-categoria-id")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public long produtosPorCategoriaID(@RequestParam Long id) {
        return produtoService.contarProdutosPorCategoriaId(id);
    }

    // GET http://localhost:8080/produtos/contagem-produtos-preco-maior-que?preco=7000
    @GetMapping("/contagem-produtos-preco-maior-que")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public long contagemProdutosComPrecoMaiorQue(@RequestParam Double preco){
        return produtoService.contarProdutosPorPrecoMaiorQue(preco);
    }

    // GET http://localhost:8080/produtos/produto-por-preco-menor-que-ou-nome-parcial?preco=4500&nome=harry
    @GetMapping("/produto-por-preco-menor-que-ou-nome-parcial")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> produtoPorPrecoMenorQueOuNomeParcial(@RequestParam Double preco, String nome){
        return produtoService.produtosPorPrecoMenorQueOuNomeParcial(preco,nome);
    }

    // GET http://localhost:8080/produtos/top5-produtos-mais-baratos-por-categoria?categoria=Escritorio
    @GetMapping("/top5-produtos-mais-baratos-por-categoria")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> top5ProdutosMaisBaratosPorCategoria(String categoria){
        return produtoService.produtosTop5ProdutosMaisBaratoPorCategoria(categoria);
    }

    // GET http://localhost:8080/produtos/produto-preco-maior?preco200
    @GetMapping("/produto-preco-maior")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarPorPrecoMaior(@RequestParam Double preco){
        return produtoService.buscarPorPrecoMaior(preco);
    }

    // GET http://localhost:8080/produtos/produto-preco-ordem-asc
    @GetMapping("/produto-preco-ordem-asc")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutosOrderCrescente(){
        return produtoService.buscarProdutosOrderCrescente();
    }

    // GET http://localhost:8080/produtos/produto-preco-ordem-desc
    @GetMapping("/produto-preco-ordem-desc")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutosOrderDecrescente(){
        return produtoService.buscarProdutosOrderDecrescente();
    }

    // GET http://localhost:8080/produtos/por-letra-inicial?letra=A
    @GetMapping("/por-letra-inicial")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutosPorLetraInicial(@RequestParam String letra){
        return produtoService.buscarProdutosPorLetraInicial(letra);
    }

    // GET http://localhost:8080/produtos/media-precos
    @GetMapping("/media-precos")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public Double mediaPrecos(){
        return produtoService.mediaPrecos();
    }

    // GET http://localhost:8080/produtos/produto-max-preco-por-categoria?categoria=Livros
    @GetMapping("/produto-max-preco-por-categoria")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public Double maxPrecoProdutoPorCategoria(@RequestParam String categoria){
        return produtoService.maxPrecoProdutoPorCategoria(categoria);
    }

    // GET http://localhost:8080/produtos/count-por-categorias
    @GetMapping("/count-por-categorias")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<Object[]> contarProdutosPorTodasCategorias() {
        return produtoService.contarProdutosPorTodasCategorias();
    }

    // GET http://localhost:8080/produtos/filtrar?nome=Notebook
    // GET http://localhost:8080/produtos/filtrar?categoria=Livros
    @GetMapping("/filtrar")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> buscarProdutosFiltrados(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria) {
        return produtoService.buscarProdutosFiltrados(nome, categoria);
    }

    // GET http://localhost:8080/produtos/top5maiscaros
    @GetMapping("/top5maiscaros")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE','VENDEDOR','CLIENTE')")
    public List<ProdutoResponseDTO> getTop5ProdutosMaisCaros() {
        return produtoService.buscarTop5ProdutosMaisCaros();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<ProdutoResponseDTO> salvar(@RequestBody @Valid ProdutoRequestDTO dto) {
        ProdutoResponseDTO produto = produtoService.salvar(dto);
        return ResponseEntity.ok(produto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GERENTE')")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid ProdutoRequestDTO dto) {
        Produto atualizado = produtoService.atualizar(id, dto);
        return ResponseEntity.ok(new ProdutoResponseDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtoService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
