/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academiapoo.entities;

/**
 * Representa os tipos de salas ou modalidades disponíveis na academia.
 *
 * Cada modalidade possui um nome identificador e uma capacidade máxima
 * de alunos permitida. Essas informações podem ser utilizadas pela classe
 * GymClass para identificar a modalidade da aula e consultar a capacidade
 * da sala associada.
 *
 * @author Antony/Samuel
 * @version 1.0
 */
public enum GymRoom {

    /** Sala destinada às aulas de Spinning, com capacidade para 20 alunos. */
    SPINNING("Spinning", 20),

    /** Sala destinada às atividades de musculação, com capacidade para 40 alunos. */
    MUSCULACAO("Musculação", 40),

    /** Sala destinada às aulas de Fit Dance, com capacidade para 20 alunos. */
    FIT_DANCE("Fit Dance", 20),

    /** Sala destinada às aulas de Pilates, com capacidade para 20 alunos. */
    PILATES("Pilates", 20);

    /** Nome da modalidade ou identificação da sala. */
    private final String nome;

    /** Capacidade máxima de alunos permitida na sala. */
    private final int capacidade;

    /**
     * Inicializa uma modalidade com seu nome e sua capacidade máxima.
     *
     * Este construtor é utilizado internamente para definir as constantes
     * do enum.
     *
     * @param nome nome da modalidade ou identificação da sala.
     * @param capacidade quantidade máxima de alunos permitida na sala.
     */
    GymRoom(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
    }

    /**
     * Retorna o nome da modalidade ou a identificação da sala.
     *
     * @return nome da modalidade.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a capacidade máxima de alunos permitida na sala.
     *
     * @return quantidade máxima de alunos.
     */
    public int getCapacidade() {
        return capacidade;
    }
}