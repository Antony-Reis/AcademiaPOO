package br.com.academiapoo.model;

/**
 * Subclasse concreta de {@link Modalidade} que representa a aula de Fit Dance.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class FitDance extends Modalidade {

    /** Valor base padrão da aula de Fit Dance. */
    private static final double VALOR_BASE_PADRAO = 60.00;

    /**
     * Construtor que utiliza o valor base padrão de Fit Dance.
     *
     * @param plano O plano de cobrança vinculado.
     */
    public FitDance(Plano plano) {
        super("Fit Dance", VALOR_BASE_PADRAO, plano);
    }

    /**
     * Construtor que permite definir um valor base personalizado para Fit Dance.
     *
     * @param valorBase O valor base da aula.
     * @param plano O plano de cobrança vinculado.
     */
    public FitDance(double valorBase, Plano plano) {
        super("Fit Dance", valorBase, plano);
    }

    /**
     * Retorna uma representação textual da modalidade Fit Dance.
     *
     * @return String com os dados da modalidade.
     */
    @Override
    public String toString() {
        return "FitDance -> " + super.toString();
    }
}
