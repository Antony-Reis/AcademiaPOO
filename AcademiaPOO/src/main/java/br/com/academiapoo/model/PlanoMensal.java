package br.com.academiapoo.model;

/**
 * Implementação concreta de {@link Plano} para cobrança mensal.
 * Utiliza o valor padrão da modalidade sem descontos adicionais.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class PlanoMensal implements Plano {

    /**
     * Construtor padrão do Plano Mensal.
     */
    public PlanoMensal() {
        super();
    }

    /**
     * Retorna o valor padrão da modalidade.
     *
     * @param valorBase O valor base da modalidade.
     * @return O valor padrão.
     */
    @Override
    public double calcularValor(double valorBase) {
        return valorBase;
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return {@code "Mensal"}.
     */
    @Override
    public String getTipoPlano() {
        return "Mensal";
    }

    /**
     * Retorna uma representação textual do plano mensal.
     *
     * @return String descrevendo o plano.
     */
    @Override
    public String toString() {
        return "PlanoMensal [Tipo: " + getTipoPlano() + " | Valor padrão]";
    }
}
