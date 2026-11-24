package br.com.academiapoo.model;

/**
 * Subclasse concreta de {@link Modalidade} que representa a aula de Pilates.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Pilates extends Modalidade {

    /** Valor base padrão da aula de Pilates. */
    private static final double VALOR_BASE_PADRAO = 100.00;

    /**
     * Construtor que utiliza o valor base padrão de Pilates.
     *
     * @param plano O plano de cobrança vinculado.
     */
    public Pilates(Plano plano) {
        super("Pilates", VALOR_BASE_PADRAO, plano);
    }

    /**
     * Construtor que permite definir um valor base personalizado para Pilates.
     *
     * @param valorBase O valor base da aula.
     * @param plano O plano de cobrança vinculado.
     */
    public Pilates(double valorBase, Plano plano) {
        super("Pilates", valorBase, plano);
    }

    /**
     * Retorna uma representação textual da modalidade Pilates.
     *
     * @return String com os dados da modalidade.
     */
    @Override
    public String toString() {
        return "Pilates -> " + super.toString();
    }
}
