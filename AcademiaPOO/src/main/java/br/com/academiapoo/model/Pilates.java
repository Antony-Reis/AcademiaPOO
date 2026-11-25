package br.com.academiapoo.model;

/**
 * Modalidade de aula Pilates.
 */
public class Pilates extends Modalidade {

    private static final double VALOR_BASE_PADRAO = 100.00;

    /** Construtor usando o valor base padrao. */
    public Pilates(Plano plano) {
        super("Pilates", VALOR_BASE_PADRAO, plano);
    }

    /** Construtor com valor base customizado. */
    public Pilates(double valorBase, Plano plano) {
        super("Pilates", valorBase, plano);
    }

    @Override
    public String toString() {
        return "Pilates -> " + super.toString();
    }
}
