package br.com.academiapoo.model;

import br.com.academiapoo.config.Sistema;

import java.util.Objects;

/**
 * Representa um produto vendido na lojinha da academia.
 */
public class Produto {

    /** Identificador unico do produto. */
    // nao tem setter pra nao alterar o codigo do produto depois de cadastrado
    private Integer id;

    /** Nome do produto. */
    private String nome;

    /** Preco unitario do produto. */
    private double preco;

    /** Construtor completo do produto. */
    public Produto(Integer id, String nome, double preco) {
        super();
        validarId(id);
        validarNome(nome);
        validarPreco(preco);

        this.id = id;
        this.nome = nome.trim();
        this.preco = preco;
        Sistema.registrarNovoProduto();
    }

    /** Retorna o id do produto. */
    public Integer getId() {
        return this.id;
    }

    /** Retorna o nome do produto. */
    public String getNome() {
        return this.nome;
    }

    /** Atualiza o nome do produto. */
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    /** Retorna o preco do produto. */
    public double getPreco() {
        return this.preco;
    }

    /** Atualiza o preco do produto. */
    public void setPreco(double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    private void validarId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("O ID do produto não pode ser nulo.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("O ID do produto deve ser maior que zero.");
        }
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio.");
        }
    }

    private void validarPreco(double preco) {
        if (Double.isNaN(preco) || Double.isInfinite(preco)) {
            throw new IllegalArgumentException("O preço fornecido é inválido.");
        }
        if (preco < 0.0) {
            throw new IllegalArgumentException("O preço do produto não pode ser negativo.");
        }
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return String.format("Produto [ID: %d | Nome: %s | Preço: R$ %.2f]",
                this.id, this.nome, this.preco);
    }
}
