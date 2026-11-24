package br.com.academiapoo.model;

/**
 * Implementação concreta de {@link Plano} para cobrança anual.
 * Aplica 15% de desconto sobre o valor base da modalidade.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class PlanoAnual implements Plano {

    /** Percentual de desconto concedido no plano anual (15%). */
    private static final double DESCONTO_ANUAL = 0.15;

    /**
     * Construtor padrão do Plano Anual.
     */
    public PlanoAnual() {
        super();
    }

    /**
     * Calcula o valor final aplicando 15% de desconto sobre o valor base.
     *
     * @param valorBase O valor base da modalidade.
     * @return O valor com 15% de desconto.
     */
    @Override
    public double calcularValor(double valorBase) {
        return valorBase * (1.0 - DESCONTO_ANUAL);
    }

    /**
     * Retorna o tipo do plano.
     *
     * @return {@code "Anual"}.
     */
    @Override
    public String getTipoPlano() {
        return "Anual";
    }

    /**
     * Retorna uma representação textual do plano anual.
     *
     * @return String descrevendo o plano.
     */
    @Override
    public String toString() {
        return "PlanoAnual [Tipo: " + getTipoPlano() + " | Desconto: 15%]";
    }
}
