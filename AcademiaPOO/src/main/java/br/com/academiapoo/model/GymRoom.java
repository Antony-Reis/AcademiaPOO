package br.com.academiapoo.model;

/**
 * Enum com as 4 salas da academia e suas capacidades.
 */
public enum GymRoom {

    /** Sala de Spinning (20 vagas). */
    SPINNING("Spinning", 20),

    /** Sala de Musculacao (40 vagas). */
    MUSCULACAO("Musculação", 40),

    /** Sala de Fit Dance (20 vagas). */
    FIT_DANCE("Fit Dance", 20),

    /** Sala de Pilates (20 vagas). */
    PILATES("Pilates", 20);

    /** Nome da sala. */
    private final String nome;

    /** Capacidade maxima de alunos. */
    private final int capacidade;

    GymRoom(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
    }

    /** Retorna o nome da sala. */
    public String getNome() {
        return nome;
    }

    /** Retorna a capacidade da sala. */
    public int getCapacidade() {
        return capacidade;
    }

    @Override
    public String toString() {
        return nome + " (Capacidade: " + capacidade + ")";
    }
}
