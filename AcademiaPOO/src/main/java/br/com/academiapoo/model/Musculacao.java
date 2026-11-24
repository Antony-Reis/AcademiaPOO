package br.com.academiapoo.model;

/**
 * Subclasse concreta de {@link Modalidade} que representa a aula de Musculação.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Musculacao extends Modalidade {

    /** Valor base padrão da aula de Musculação. */
    private static final double VALOR_BASE_PADRAO = 70.00;

    /**
     * Construtor que utiliza o valor base padrão de Musculação.
     *
     * @param plano O plano de cobrança vinculado.
     */
    public Musculacao(Plano plano) {
        super("Musculação", VALOR_BASE_PADRAO, plano);
    }

    /**
     * Construtor que permite definir um valor base personalizado para Musculação.
     *
     * @param valorBase O valor base da aula.
     * @param plano O plano de cobrança vinculado.
     */
    public Musculacao(double valorBase, Plano plano) {
        super("Musculação", valorBase, plano);
    }

    /**
     * Retorna uma representação textual da modalidade Musculação.
     *
     * @return String com os dados da modalidade.
     */
    @Override
    public String toString() {
        return "Musculacao -> " + super.toString();
    }
}
