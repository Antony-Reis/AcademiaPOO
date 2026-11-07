package com.mycompany.academiapoo.entities;

/**
 * Representa as fases de um agendamento na academia:
 * Preliminar, Definitivo e Cancelado.
 * 
 * @author Antony/Samuel
 * @version 1.0
 */
public enum FaseAgendamento {

    /** Fase inicial onde a vaga está pré-reservada aguardando confirmação. */
    PRELIMINAR("Preliminar"),

    /** Fase onde o agendamento foi confirmado e o valor foi debitado no cartão. */
    DEFINITIVO("Definitivo"),

    /** Fase onde o agendamento foi cancelado por falta de confirmação ou por desistência. */
    CANCELADO("Cancelado");

    /** Descrição em formato legível da fase. */
    private final String descricao;

    /**
     * Construtor do enum para definir a descrição da fase.
     *
     * @param descricao A descrição da fase.
     */
    FaseAgendamento(String descricao) {
        this.descricao = descricao;
    }

    /**
     * Retorna a descrição da fase.
     *
     * @return A descrição da fase.
     */
    public String getDescricao() {
        return this.descricao;
    }

    /**
     * Retorna a descrição textual da fase.
     *
     * @return A descrição da fase.
     */
    @Override
    public String toString() {
        return this.descricao;
    }
}
