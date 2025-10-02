package br.ce.christian.config;

import br.ce.christian.model.*;
import br.ce.christian.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataLoader {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private FornecedorRepository fornecedorRepository;

    @PostConstruct
    public void loadData(){

        if (produtoRepository.count() > 0) {
            System.out.println("⚠ Dados já existem, DataLoader não rodou.");
            return;
        }

        // categorias
        Categoria categoriaEletronicos = new Categoria(null, "Eletrônicos", null);
        Categoria categoriaLivros = new Categoria(null, "Livros", null);
        categoriaRepository.saveAll(List.of(categoriaEletronicos, categoriaLivros));

        // fornecedores
        Fornecedor fornecedorTech = new Fornecedor(null,"Tech Supplier", null);
        Fornecedor fornecedorLivros = new Fornecedor(null, "Livraria Global", null);
        fornecedorRepository.saveAll(List.of(fornecedorTech, fornecedorLivros));

        // produtos
        Produto produto1 = new Produto(null, "Notebook", 3500.0, categoriaEletronicos, fornecedorTech);
        produto1.setCategoria(categoriaEletronicos);
        Produto produto2 = new Produto(null, "Smartphone", 2500.0, categoriaEletronicos, fornecedorTech);
        produto2.setCategoria(categoriaEletronicos);
        Produto produto3 = new Produto(null, "Livro de Java", 100.0, categoriaLivros ,fornecedorLivros);
        produto3.setCategoria(categoriaLivros);
        produtoRepository.saveAll(List.of(produto1, produto2, produto3));

        // pedidos
        Pedido pedido1 = new Pedido(null, List.of(produto1, produto3), LocalDate.now(), null, null);
        pedido1.setProdutos(List.of(produto1, produto3));
        Pedido pedido2 = new Pedido(null, List.of(produto2), LocalDate.now().minusDays(1), null, null);
        pedido2.setProdutos(List.of(produto2));
        pedidoRepository.saveAll(List.of(pedido1, pedido2));

        System.out.println("✅ Dados iniciais carregados no banco!");

    }

}
