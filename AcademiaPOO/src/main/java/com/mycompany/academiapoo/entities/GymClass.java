package entities;

import com.mycompany.academiapoo.entities.GymRoom;

/**
 * Representa uma aula ou modalidade oferecida na academia (ex: Spinning, Musculação, Fit Dance, Pilates).
 * Esta classe armazena informações sobre o instrutor, a sala onde ocorre, o tipo de atividade e a capacidade de alunos.
 * 
 * @author Antony/Samuel
 * @version 1.0
 */
public class GymClass {
    
    /** O identificador único da aula. */
    private int id;
    
    
    /** O nome do instrutor responsável por acompanhar as atividades. */
    private String instructor;
    
    private GymRoom room;
    
    /**
     * Construtor completo para inicializar uma nova aula na academia.
     *
     * @param id O identificador único da aula.
     * @param room A sala onde a aula será realizada.
     * @param instructor O instrutor responsável.

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
     * @return O id da aula.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna a sala onde a aula é realizada.
     *
     * @return O nome ou identificação da sala.
     */
    public GymRoom getRoom() {
        return room;
    }

    /**
     * Retorna o instrutor responsável pela aula.
     *
     * @return O nome do instrutor.
     */
    public String getInstructor() {
        return instructor;
    }

    /**
     * Retorna o tipo ou modalidade da aula.
     *
     * @return O tipo da aula.
     */
    public String getType() {
        return room.getNome();
    }

    /**
     * Retorna a capacidade máxima de alunos para esta aula.
     *
     * @return A quantidade máxima de alunos permitida.
     */
    public int getCapacity() {
        return room.getCapacidade();
    }

    /**
     * Altera o instrutor responsável por acompanhar as atividades da aula.
     *
     * @param instructor O nome do novo instrutor.
     */
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    /**
     * Retorna uma representação em formato de texto (String) com todos os dados da aula.
     * Sobrescreve o método padrão da classe Object.
     *
     * @return Uma String contendo o id, sala, instrutor, tipo e capacidade da aula.
     */
    @Override
    public String toString() {
        return "GymClass{" + "id: " + id + 
                ", room: " + room + 
                ", instructor: " + instructor;
    }
}
