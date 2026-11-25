package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

/**
 * Valida na catraca se ainda tem vaga na sala antes de liberar o acesso.
 */
public class ValidadorCapacidadeSala extends ValidadorAcesso {

    /** Quantidade atual de pessoas na sala. */
    private int lotacaoAtual;

    /** Capacidade maxima da sala. */
    private int capacidadeMaxima;

    /** Construtor padrao com capacidade 40 e lotacao 0. */
    public ValidadorCapacidadeSala() {
        this(0, 40);
    }

    /** Construtor usando uma sala do enum GymRoom e a lotacao atual. */
    public ValidadorCapacidadeSala(GymRoom sala, int lotacaoAtual) {
        super();
        int cap = 40;
        if (sala != null) {
            cap = sala.getCapacidade();
        }
        if (lotacaoAtual < 0 || cap <= 0) {
            throw new IllegalArgumentException("Lotação e capacidade devem ser valores válidos.");
        }
        this.lotacaoAtual = lotacaoAtual;
        this.capacidadeMaxima = cap;
    }

    /** Construtor completo com lotacao e capacidade maxima. */
    public ValidadorCapacidadeSala(int lotacaoAtual, int capacidadeMaxima) {
        super();
        if (lotacaoAtual < 0 || capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("Lotação e capacidade devem ser valores válidos.");
        }
        this.lotacaoAtual = lotacaoAtual;
        this.capacidadeMaxima = capacidadeMaxima;
    }

    /** Retorna a lotacao atual. */
    public int getLotacaoAtual() {
        return this.lotacaoAtual;
    }

    /** Atualiza a lotacao atual. */
    public void setLotacaoAtual(int lotacaoAtual) {
        if (lotacaoAtual < 0) {
            throw new IllegalArgumentException("A lotação atual não pode ser negativa.");
        }
        this.lotacaoAtual = lotacaoAtual;
    }

    /** Retorna a capacidade maxima. */
    public int getCapacidadeMaxima() {
        return this.capacidadeMaxima;
    }

    /** Atualiza a capacidade maxima. */
    public void setCapacidadeMaxima(int capacidadeMaxima) {
        if (capacidadeMaxima <= 0) {
            throw new IllegalArgumentException("A capacidade máxima deve ser maior que zero.");
        }
        this.capacidadeMaxima = capacidadeMaxima;
    }

    @Override
    public boolean validar(Client cliente) {
        if (this.lotacaoAtual >= this.capacidadeMaxima) {
            String tempNome;
            if (cliente != null) {
                tempNome = cliente.getName();
            } else {
                tempNome = "Desconhecido";
            }
            System.out.printf("[Catraca - Capacidade] Acesso negado para %s: sala lotada (%d/%d).%n",
                    tempNome, this.lotacaoAtual, this.capacidadeMaxima);
            return false;
        }
        return validarProximo(cliente);
    }

    @Override
    public String toString() {
        return String.format("ValidadorCapacidadeSala [Lotação: %d/%d] -> %s",
                this.lotacaoAtual, this.capacidadeMaxima, super.toString());
    }
}
