package com.mycompany.academiapoo.entities;

import com.mycompany.academiapoo.entities.GymRoom;

/**
 * Representa uma aula ou modalidade oferecida na academia, como Spinning,
 * Musculação, Fit Dance ou Pilates.
 * 
 * Armazena o identificador da aula, o nome do instrutor responsável
 * e a sala onde a atividade é realizada. O tipo da aula e a capacidade
 * máxima de alunos são obtidos a partir dos dados da sala associada.
 *
 * @author Antony/Samuel
 * @version 1.1
 */
public class GymClass {

    /** Identificador único da aula. */
    private int id;

    /** Nome do instrutor responsável pela aula. */
    private String instructor;

    /** Sala onde a aula é realizada, contendo seu nome e sua capacidade. */
    private GymRoom room;

    /**
     * Cria uma nova aula na academia, associando-a a uma sala
     * e a um instrutor responsável.
     *
     * @param id identificador único da aula.
     * @param instructor nome do instrutor responsável.
     * @param room sala onde a aula será realizada.
     */
    public GymClass(int id, String instructor, GymRoom room) {
        this.id = id;
        this.room = room;
        this.instructor = instructor;
        this.room = room;
    }

    /**
     * Retorna o identificador único da aula.
     *
     * @return identificador da aula.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o objeto que representa a sala associada à aula.
     *
     * @return sala vinculada à aula.
     */
    public GymRoom getRoom() {
        return room;
    }

    /**
     * Retorna o nome do instrutor responsável pela aula.
     *
     * @return nome do instrutor.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Retorna o tipo ou a modalidade da aula, utilizando o nome
     * cadastrado na sala associada.
     *
     * @return nome da modalidade definido na sala.
     */
    public String getType() {
        return room.getNome();
    }

    /**
     * Retorna a capacidade máxima de alunos permitida na aula,
     * conforme a capacidade cadastrada na sala associada.
     *
     * @return quantidade máxima de alunos permitida na sala.
     */
    public int getCapacity() {
        return room.getCapacidade();
    }

    /**
     * Atualiza o nome do instrutor responsável pela aula.
     *
     * @param instructor nome do novo instrutor.
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Retorna uma representação textual dos dados da aula,
     * incluindo seu identificador, a sala associada e o instrutor.
     * Sobrescreve o método toString() da classe Object.
     *
     * @return representação textual do identificador, da sala e do instrutor.
     */
    @Override
    public String toString() {
        return "GymClass{" + "id: " + id +
                ", room: " + room +
                ", instructor: " + instructor;
    }
}