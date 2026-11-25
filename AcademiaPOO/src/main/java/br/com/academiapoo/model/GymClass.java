package br.com.academiapoo.model;

/**
 * Representa uma aula da academia ligada a uma sala e a um instrutor.
 */
public class GymClass {

    /** Codigo da aula. */
    // nao tem setter pra nao trocar o id da aula depois de criada
    private int id;

    /** Nome do instrutor responsavel. */
    private String instructor;

    /** Sala onde a aula acontece. */
    // nao tem setter pra manter a aula fixa na sala escolhida
    private GymRoom room;

    /** Construtor da aula. */
    public GymClass(int id, String instructor, GymRoom room) {
        this.id = id;
        this.instructor = instructor;
        this.room = room;
    }

    /** Retorna o id da aula. */
    public int getId() {
        return id;
    }

    /** Retorna a sala da aula. */
    public GymRoom getRoom() {
        return room;
    }

    /** Retorna o nome do instrutor. */
    public String getInstructor() {
        return instructor;
    }

    /** Retorna o nome da modalidade da sala. */
    public String getType() {
        return room.getNome();
    }

    /** Retorna a capacidade maxima da sala. */
    public int getCapacity() {
        return room.getCapacidade();
    }

    /** Atualiza o nome do instrutor. */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "GymClass{" + "id: " + id +
                ", room: " + room +
                ", instructor: " + instructor + "}";
    }
}
