package br.com.academiapoo.model;

/**
 * Plano mensal cobrando o valor padrao da modalidade.
 */
public class PlanoMensal implements Plano {

    /** Construtor padrao. */
    public PlanoMensal() {
        super();
    }

    @Override
    public double calcularValor(double valorBase) {
        return valorBase;
    }

    @Override
    public String getTipoPlano() {
        return "Mensal";
    }

    @Override
    public String toString() {
        return "PlanoMensal [Tipo: " + getTipoPlano() + " | Valor padrão]";
    }
}
