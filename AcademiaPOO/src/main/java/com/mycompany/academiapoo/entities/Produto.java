package com.mycompany.academiapoo.entities;

import java.util.Objects;

/**
 * Representa um produto comercializado na lojinha da academia.
 * Esta classe armazena os dados do produto como id, nome e preço de venda,
 * aplicando regras de validação para garantir a integridade dos dados.
 * 
 * @author Antony/Samuel
 * @version 1.0
 */
public class Produto {

    /** O identificador único do produto no sistema. */
    private Integer id;

    /** O nome descritivo do produto. */
    private String nome;

    /** O preço unitário de venda do produto. */
    private double preco;

    /**
     * Construtor completo para cadastrar um novo produto na lojinha.
     * Utiliza super() para inicializar a classe base.
     *
     * @param id O identificador único do produto.
     * @param nome O nome do produto.
     * @param preco O preço unitário de venda.
     */
    public Produto(Integer id, String nome, double preco) {
        super();
        validarId(id);
        validarNome(nome);
        validarPreco(preco);

        this.id = id;
        this.nome = nome.trim();
        this.preco = preco;
    }

    /**
     * Retorna o identificador único do produto.
     *
     * @return O id do produto.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Retorna o nome do produto.
     *
     * @return O nome do produto.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Atualiza o nome do produto com validação.
     *
     * @param nome O novo nome do produto.
     */
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    /**
     * Retorna o preço de venda do produto.
     *
     * @return O preço unitário.
     */
    public double getPreco() {
        return this.preco;
    }

    /**
     * Atualiza o preço unitário do produto com validação.
     *
     * @param preco O novo preço do produto.
     */
    public void setPreco(double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    /**
     * Valida se o id é válido.
     *
     * @param id O identificador a ser validado.
     */
    private void validarId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do produto não pode ser nulo.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do produto deve ser maior que zero.");
        }
    }

    /**
     * Valida se o nome é válido.
     *
     * @param nome O nome a ser validado.
     */
    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio.");
        }
    }

    /**
     * Valida se o preço é válido.
     *
     * @param preco O preço a ser validado.
     */
    private void validarPreco(double preco) {
        if (Double.isNaN(preco) || Double.isInfinite(preco)) {
            throw new IllegalArgumentException("O preço fornecido é inválido.");
        }
        if (preco < 0.0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
    }

    /**
     * Compara dois produtos pelo identificador id.
     *
     * @param obj O objeto a ser comparado.
     * @return true se tiverem o mesmo id, false caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Produto other = (Produto) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Retorna o código hash baseado no identificador do produto.
     *
     * @return O valor hash do id.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * Retorna uma representação em formato de texto com os dados do produto.
     *
     * @return Uma String contendo os detalhes do produto.
     */
    @Override
    public String toString() {
        return String.format("Produto [ID: %d | Nome: %s | Preço: R$ %.2f]",
                this.id, this.nome, this.preco);
    }
}
