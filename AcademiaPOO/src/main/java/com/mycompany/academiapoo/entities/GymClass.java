package entities;

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
    
    /** O nome ou identificação da sala onde a aula será realizada. */
    private String room;
    
    /** O nome do instrutor responsável por acompanhar as atividades. */
    private String instructor;
    
    /** O tipo ou modalidade da aula (ex: Fit Dance, Pilates, etc.). */
    private String type;
    
    /** A capacidade máxima de alunos permitida na aula. */
    private int capacity;
    
    /**
     * Construtor completo para inicializar uma nova aula na academia.
     *
     * @param id O identificador único da aula.
     * @param room A sala onde a aula será realizada.
     * @param instructor O instrutor responsável.
     * @param type O tipo/modalidade da aula.
     * @param capacity A capacidade máxima de alunos.
     */
    public GymClass(int id, String room, String instructor, String type, int capacity) {
        this.id = id;
        this.room = room;
        this.instructor = instructor;
        this.type = type;
        this.capacity = capacity;
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
    public String getRoom() {
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
        return type;
    }

    /**
     * Retorna a capacidade máxima de alunos para esta aula.
     *
     * @return A quantidade máxima de alunos permitida.
     */
    public int getCapacity() {
        return capacity;
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
     * Altera a capacidade máxima de alunos da aula. 
     * A capacidade deve ser obrigatoriamente maior do que zero.
     *
     * @param capacity A nova capacidade máxima de alunos.
     */
    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            System.out.println("Não pode existir capacidade menor ou igual a 0.");
            return; // Impede que uma capacidade inválida seja atribuída
        }        
        this.capacity = capacity;
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
                ", instructor: " + instructor + 
                ", type: " + type + 
                ", capacity: " + capacity + '}';
    }
}
