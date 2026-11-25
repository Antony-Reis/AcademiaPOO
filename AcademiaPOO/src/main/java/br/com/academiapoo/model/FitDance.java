package br.com.academiapoo.model;

/**
 * Modalidade de aula Fit Dance.
 */
public class FitDance extends Modalidade {

    private static final double VALOR_BASE_PADRAO = 60.00;

    /** Construtor usando o valor base padrao. */
    public FitDance(Plano plano) {
        super("Fit Dance", VALOR_BASE_PADRAO, plano);
    }

    /** Construtor com valor base customizado. */
    public FitDance(double valorBase, Plano plano) {
        super("Fit Dance", valorBase, plano);
    }

    @Override
    public String toString() {
        return "FitDance -> " + super.toString();
    }
}
