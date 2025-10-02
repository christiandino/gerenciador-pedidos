package br.ce.christian.repository;

import br.ce.christian.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByNomeEqualsIgnoreCase (@Param("nome") String nome);

    List<Produto> findByCategoria_NomeEqualsIgnoreCase(String nome);

    List<Produto> findByPrecoGreaterThan(Double preco);

    List<Produto> findByPrecoLessThan(Double preco);

    List<Produto> findByNomeContainingIgnoreCase(String nome);

    List<Produto> findByCategoria_NomeOrderByPrecoAsc(String categoria);

    List<Produto> findByCategoria_NomeOrderByPrecoDesc(String categoria);

    long countByCategoria_NomeEqualsIgnoreCase(String categoria);

    long countByCategoria_Id(Long categoriaId);

    long countByPrecoGreaterThan(Double preco);

    List<Produto> findByPrecoLessThanOrNomeContainingIgnoreCase(Double preco, String nome);

    List<Produto> findTop3ByOrderByPrecoDesc();

    List<Produto> findTop5ByCategoria_NomeOrderByPrecoAsc(String categoria);

    @Query("SELECT p FROM Produto p WHERE p.preco > :preco")
    List<Produto> buscarPorPrecoMaior(@Param("preco") Double preco);

    @Query("SELECT p FROM Produto p ORDER BY p.preco ASC")
    List<Produto> buscarProdutosOrderCrescente ();

    @Query("SELECT p FROM Produto p ORDER BY p.preco DESC")
    List<Produto> buscarProdutosOrderDecrescente ();

    // @Query("SELECT p FROM Produto p WHERE p.nome LIKE :letra%")
    @Query("SELECT p FROM Produto p WHERE LOWER(p.nome) LIKE LOWER(CONCAT(:letra, '%'))")
    List<Produto> buscarProdutosPorLetraInicial (@Param("letra") String letra);

    @Query("SELECT AVG(p.preco) FROM Produto p")
    Double mediaPrecos();

    @Query("SELECT MAX(p.preco) FROM Produto p JOIN p.categoria c WHERE c.nome = :categoria")
    Double maxPrecoProdutoPorCategoria(@Param("categoria") String categoria);

    @Query("SELECT c.nome, COUNT(p) FROM Produto p JOIN p.categoria c GROUP BY c.nome")
    List<Object[]> contarProdutosPorTodasCategorias();

    @Query("SELECT p FROM Produto p WHERE (:nome IS NULL OR p.nome = :nome) AND (:categoria IS NULL OR p.categoria.nome = :categoria)")
    List<Produto> buscarProdutosFiltrados(@Param("nome") String nome, @Param("categoria") String categoria);

    @Query(value = "SELECT * FROM produto ORDER BY valor DESC LIMIT 5", nativeQuery = true)
    List<Produto> top5ProdutosMaisCaros();

    boolean existsByNomeAndCategoriaId(String nome, Long categoriaId);
}
