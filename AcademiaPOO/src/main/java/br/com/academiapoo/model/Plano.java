package br.com.academiapoo.model;

/**
 * Interface que define o contrato de implementação (Implementor) do padrão Bridge
 * para os planos de cobrança da academia.
 *
 * Permite desacoplar a regra de cálculo de preço da hierarquia de modalidades.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public interface Plano {

    /**
     * Calcula o valor final da aula a partir do valor base da modalidade.
     *
     * @param valorBase O valor base da modalidade.
     * @return O valor final calculado de acordo com o plano.
     */
    double calcularValor(double valorBase);

    /**
     * Retorna o nome identificador do tipo de plano.
     *
     * @return O tipo do plano (ex: Diária, Mensal, Anual).
     */
    String getTipoPlano();
}
