package com.mycompany.academiapoo.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Representa o Estoque da lojinha da academia.
 * Esta classe gerencia a lista de produtos disponíveis, controlando
 * operações de cadastro, remoção e verificação de itens em estoque.
 * 
 * @author Antony/Samuel
 * @version 1.0
 */
public class Estoque {

    /** A lista de produtos armazenados no estoque da academia. */
    private final List<Produto> produtos;

    /**
     * Construtor padrão para inicializar um estoque vazio.
     * Utiliza super() para inicializar a classe base.
     */
    public Estoque() {
        super();
        this.produtos = new ArrayList<>();
    }

    /**
     * Construtor com lista inicial de produtos.
     *
     * @param produtosIniciais Lista inicial de produtos para o estoque.
     */
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

    /**
     * Adiciona um novo produto ao estoque.
     * Não permite adicionar produtos nulos nem produtos com id repetido.
     *
     * @param produto O produto a ser adicionado.
     * @return true se o produto foi adicionado, false caso contrário.
     */
    public boolean adicionarProduto(Produto produto) {
        if (produto == null) {
            throw new IllegalArgumentException("Não é possível adicionar um produto nulo ao estoque.");
        }
        if (verificarProdutoPorId(produto.getId())) {
            System.err.println("Aviso: O produto com ID " + produto.getId() + " já está cadastrado.");
            return false;
        }
        return this.produtos.add(produto);
    }

    /**
     * Remove um produto do estoque.
     *
     * @param produto O produto a ser removido.
     * @return true se foi removido, false caso não tenha sido encontrado.
     */
    public boolean removerProduto(Produto produto) {
        if (produto == null) {
            return false;
        }
        return this.produtos.remove(produto);
    }

    /**
     * Remove um produto do estoque pelo seu identificador id.
     *
     * @param id O identificador do produto a ser removido.
     * @return true se foi removido, false caso não tenha sido encontrado.
     */
    public boolean removerProdutoPorId(Integer id) {
        if (id == null) {
            return false;
        }
        return this.produtos.removeIf(produto -> produto.getId().equals(id));
    }

    /**
     * Verifica se um produto está cadastrado no estoque.
     *
     * @param produto O produto a ser verificado.
     * @return true se o produto estiver no estoque, false caso contrário.
     */
    public boolean verificarProduto(Produto produto) {
        if (produto == null) {
            return false;
        }
        return this.produtos.contains(produto);
    }

    /**
     * Verifica se existe um produto no estoque com o id informado.
     *
     * @param id O id a ser verificado.
     * @return true se o produto estiver no estoque, false caso contrário.
     */
    public boolean verificarProdutoPorId(Integer id) {
        if (id == null) {
            return false;
        }
        return this.produtos.stream().anyMatch(p -> p.getId().equals(id));
    }

    /**
     * Realiza a busca de um produto pelo id.
     *
     * @param id O id do produto procurado.
     * @return Optional contendo o produto se for encontrado.
     */
    public Optional<Produto> buscarProdutoPorId(Integer id) {
        if (id == null) {
            return Optional.empty();
        }
        return this.produtos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Retorna a quantidade total de produtos cadastrados no estoque.
     *
     * @return A quantidade de itens.
     */
    public int getQuantidadeTotal() {
        return this.produtos.size();
    }

    /**
     * Calcula o valor total somado de todos os produtos do estoque.
     *
     * @return A soma dos preços dos produtos.
     */
    public double calcularValorTotalEstoque() {
        return this.produtos.stream()
                .mapToDouble(Produto::getPreco)
                .sum();
    }

    /**
     * Retorna a lista de produtos em estoque como uma lista somente leitura.
     *
     * @return A lista de produtos não modificável.
     */
    public List<Produto> getProdutos() {
        return Collections.unmodifiableList(this.produtos);
    }

    /*
     * Justificativa da ausência do método setProdutos:
     * Não fornecemos um setter para a lista completa para proteger o estoque.
     * Qualquer inclusão ou remoção de produtos deve ser feita exclusivamente
     * pelos métodos adicionarProduto e removerProduto, garantindo que as regras
     * e validações sejam sempre respeitadas.
     */

    /**
     * Retorna uma representação em formato de texto com os dados do estoque.
     *
     * @return Uma String contendo o resumo e a lista de produtos em estoque.
     */
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
