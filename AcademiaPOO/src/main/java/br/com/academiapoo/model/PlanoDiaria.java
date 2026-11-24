package br.com.academiapoo.model;

/**
 * Implementação concreta de {@link Plano} para cobrança diária (avulsa).
 * Não aplica descontos sobre o valor base, sendo ideal para turistas.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class PlanoDiaria implements Plano {

    /**
     * Construtor padrão do Plano Diária.
     */
    public PlanoDiaria() {
        super();
    }

    /**
     * Retorna o valor base integral, sem descontos.
     *
     * @param valorBase O valor base da modalidade.
     * @return O valor final sem desconto.
     */
    @Override
    public double calcularValor(double valorBase) {
        return valorBase;
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return {@code "Diária"}.
     */
    @Override
    public String getTipoPlano() {
        return "Diária";
    }

    /**
     * Retorna uma representação textual do plano diária.
     *
     * @return String descrevendo o plano.
     */
    @Override
    public String toString() {
        return "PlanoDiaria [Tipo: " + getTipoPlano() + " | Desconto: 0% (Ideal para turistas)]";
    }
}
