package br.ce.christian.dtos;

import java.util.List;

public class FornecedorProdutosDTO {

    private List<Long> produtosIds;

    public List<Long> getProdutosIds() {
        return produtosIds;
    }

    public void setProdutosIds(List<Long> produtosIds) {
        this.produtosIds = produtosIds;
    }
}
