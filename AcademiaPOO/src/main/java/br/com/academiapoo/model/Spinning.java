package br.com.academiapoo.model;

/**
 * Modalidade de aula Spinning.
 */
public class Spinning extends Modalidade {

    private static final double VALOR_BASE_PADRAO = 80.00;

    /** Construtor usando o valor base padrao. */
    public Spinning(Plano plano) {
        super("Spinning", VALOR_BASE_PADRAO, plano);
    }

    /** Construtor com valor base customizado. */
    public Spinning(double valorBase, Plano plano) {
        super("Spinning", valorBase, plano);
    }

    @Override
    public String toString() {
        return "Spinning -> " + super.toString();
    }
}
