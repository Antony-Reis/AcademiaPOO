package br.com.academiapoo.model;

/**
 * Interface dos planos de cobranca (Implementor do padrao Bridge).
 */
public interface Plano {

    /** Calcula o valor final da aula usando o valor base. */
    double calcularValor(double valorBase);

    /** Retorna o nome do tipo de plano. */
    String getTipoPlano();
}
