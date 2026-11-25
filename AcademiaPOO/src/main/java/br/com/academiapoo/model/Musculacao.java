package br.com.academiapoo.model;

/**
 * Modalidade de aula Musculacao.
 */
public class Musculacao extends Modalidade {

    private static final double VALOR_BASE_PADRAO = 70.00;

    /** Construtor usando o valor base padrao. */
    public Musculacao(Plano plano) {
        super("Musculação", VALOR_BASE_PADRAO, plano);
    }

    /** Construtor com valor base customizado. */
    public Musculacao(double valorBase, Plano plano) {
        super("Musculação", valorBase, plano);
    }

    @Override
    public String toString() {
        return "Musculacao -> " + super.toString();
    }
}
