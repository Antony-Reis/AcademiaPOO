package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

/**
 * Elo concreto do padrão Chain of Responsibility que verifica se há vaga
 * disponível na sala/academia antes de liberar o acesso pela catraca.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class ValidadorCapacidadeSala extends ValidadorAcesso {

    /** Quantidade atual de alunos presentes. */
    private int lotacaoAtual;

    /** Capacidade máxima permitida. */
    private int capacidadeMaxima;

    /**
     * Construtor padrão com capacidade máxima de 40 alunos e lotação inicial 0.
     */
    public ValidadorCapacidadeSala() {
        this(0, 40);
    }

    /**
     * Construtor baseado em uma sala ({@link GymRoom}) e sua lotação atual.
     *
     * @param sala A sala cujo limite de capacidade será verificado.
     * @param lotacaoAtual A quantidade atual de alunos na sala.
     */
    public ValidadorCapacidadeSala(GymRoom sala, int lotacaoAtual) {
        this(lotacaoAtual, sala != null ? sala.getCapacidade() : 40);
    }

    /**
     * Construtor completo definindo lotação atual e capacidade máxima.
     *
     * @param lotacaoAtual Quantidade atual de alunos.
     * @param capacidadeMaxima Capacidade máxima permitida.
     */
    public ValidadorCapacidadeSala(int lotacaoAtual, int capacidadeMaxima) {
        super();
        if (lotacaoAtual < 0 || capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Lotação e capacidade devem ser valores válidos.");
        }
        this.lotacaoAtual = lotacaoAtual;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * Retorna a lotação atual considerada pelo validador.
     *
     * @return A lotação atual.
     */
    public int getLotacaoAtual() {
        return this.lotacaoAtual;
    }

    /**
     * Atualiza a lotação atual.
     *
     * @param lotacaoAtual A nova lotação atual.
     */
    public void setLotacaoAtual(int lotacaoAtual) {
        if (lotacaoAtual < 0) {
            throw new IllegalArgumentException("A lotação atual não pode ser negativa.");
        }
        this.lotacaoAtual = lotacaoAtual;
    }

    /**
     * Retorna a capacidade máxima permitida.
     *
     * @return A capacidade máxima.
     */
    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    /**
     * Atualiza a capacidade máxima permitida.
     *
     * @param capacidadeMaxima A nova capacidade máxima.
     */
    public void setCapacidadeMaxima(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser maior que zero.");
        }
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /**
     * Valida se ainda há vaga disponível na sala.
     *
     * @param cliente O cliente que solicita acesso.
     * @return {@code true} se houver vaga e os próximos elos aprovarem.
     */
    @Override
    public boolean validar(Client cliente) {
        if (this.lotacaoAtual >= this.capacidadeMaxima) {
            String nomeCliente = (cliente != null) ? cliente.getName() : "Desconhecido";
            System.out.printf("[Catraca - Capacidade] Acesso negado para %s: sala lotada (%d/%d).%n",
                    nomeCliente, this.lotacaoAtual, this.capacidadeMaxima);
            return false;
        }
        return validarProximo(cliente);
    }

    /**
     * Retorna uma representação textual do validador de capacidade.
     *
     * @return String com lotação atual, capacidade máxima e próximo elo.
     */
    @Override
    public String toString() {
        return String.format("ValidadorCapacidadeSala [Lotação: %d/%d] -> %s",
                this.lotacaoAtual, this.capacidadeMaxima, super.toString());
    }
}
