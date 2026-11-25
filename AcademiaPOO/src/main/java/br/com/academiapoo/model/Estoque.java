package br.com.academiapoo.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Gerencia os produtos em estoque da lojinha da academia.
 */
public class Estoque {

    /** Lista interna de produtos em estoque. */
    // nao tem setProdutos pra ninguem trocar a lista inteira por fora
    private final List<Produto> produtos;

    /** Construtor padrao com estoque vazio. */
    public Estoque() {
        super();
        this.produtos = new ArrayList<>();
    }

    /** Construtor recebendo uma lista inicial de produtos. */
    public Estoque(List<Produto> produtosIniciais) {
        super();
        this.produtos = new ArrayList<>();
        if (produtosIniciais != null) {
            for (Produto p : produtosIniciais) {
                if (p != null) {
                    adicionarProduto(p);
                }
            }
        }
    }

    /** Adiciona um produto ao estoque se o ID ainda nao existir. */
    public boolean adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Não é possível adicionar um produto nulo ao estoque.");
        }
        if (verficarProdutoPorId(produto.getId())) {
            System.err.println("Aviso: O produto com ID " + produto.getId() + " já está cadastrado.");
            return false;
        }
        return this.produtos.add(produto);
    }

    /** Remove um produto do estoque. */
    public boolean removerProduto(Produto produto) {
        if (produto == null) {
            return false;
        }
        return this.produtos.remove(produto);
    }

    /** Remove um produto pelo id usando Iterator. */
    public boolean removerProdutoPorId(Integer id) {
        if (id == null) {
            return false;
        }
        Iterator<Produto> it = this.produtos.iterator();
        while (it.hasNext()) {
            Produto p = it.next();
            if (p.getId().equals(id)) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    /** Verifica se o produto esta no estoque. */
    public boolean verificarProduto(Produto produto) {
        if (produto == null) {
            return false;
        }
        return this.produtos.contains(produto);
    }

    /** Verifica se existe um produto com o id informado. */
    public boolean verficarProdutoPorId(Integer id) {
        if (id == null) {
            return false;
        }
        for (int i = 0; i < this.produtos.size(); i++) {
            Produto p = this.produtos.get(i);
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    /** Busca um produto pelo id no estoque. */
    public Optional<Produto> buscarProdutoPorId(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        for (Produto p : this.produtos) {
            if (p.getId().equals(id)) {
                return Optional.of(p);
            }
        }
        return Optional.empty();
    }

    /** Retorna a quantidade total de itens no estoque. */
    public int getQuantidadeTotal() {
        return this.produtos.size();
    }

    /** Soma o valor de todos os produtos em estoque. */
    public double calcularValorTotalEstoque() {
        double soma = 0.0;
        for (int i = 0; i < this.produtos.size(); i++) {
            soma += this.produtos.get(i).getPreco();
        }
        return soma;
    }

    /** Retorna a lista de produtos (somente leitura). */
    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(this.produtos);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         ESTOQUE DA LOJINHA DA ACADEMIA \n");
        sb.append("========================================\n");
        sb.append(String.format("Total de Itens: %d | Valor Total: R$ %.2f\n",
                getQuantidadeTotal(), calcularValorTotalEstoque()));
        sb.append("----------------------------------------\n");

        if (this.produtos.isEmpty()) {
            sb.append("  (Nenhum produto cadastrado no momento)\n");
        } else {
            for (int i = 0; i < this.produtos.size(); i++) {
                sb.append(String.format("  [%02d] %s\n", (i + 1), this.produtos.get(i).toString()));
            }
        }
        sb.append("========================================");
        return sb.toString();
    }
}
