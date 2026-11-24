package br.com.academiapoo.model;

/**
 * Subclasse concreta de {@link Modalidade} que representa a aula de Spinning.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Spinning extends Modalidade {

    /** Valor base padrão da aula de Spinning. */
    private static final double VALOR_BASE_PADRAO = 80.00;

    /**
     * Construtor que utiliza o valor base padrão de Spinning.
     *
     * @param plano O plano de cobrança vinculado.
     */
    public Spinning(Plano plano) {
        super("Spinning", VALOR_BASE_PADRAO, plano);
    }

    /**
     * Construtor que permite definir um valor base personalizado para Spinning.
     *
     * @param valorBase O valor base da aula.
     * @param plano O plano de cobrança vinculado.
     */
    public Spinning(double valorBase, Plano plano) {
        super("Spinning", valorBase, plano);
    }

    /**
     * Retorna uma representação textual da modalidade Spinning.
     *
     * @return String com os dados da modalidade.
     */
    @Override
    public String toString() {
        return "Spinning -> " + super.toString();
    }
}
