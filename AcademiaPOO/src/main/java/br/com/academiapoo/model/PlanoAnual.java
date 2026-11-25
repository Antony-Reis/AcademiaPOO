package br.com.academiapoo.model;

/**
 * Plano anual com 15% de desconto sobre o valor base.
 */
public class PlanoAnual implements Plano {

    private static final double DESCONTO_ANUAL = 0.15;

    /** Construtor padrao. */
    public PlanoAnual() {
        super();
    }

    @Override
    public double calcularValor(double valorBase) {
        // calcula o valro com desconto
        return valorBase * (1.0 - DESCONTO_ANUAL);
    }

    @Override
    public String getTipoPlano() {
        return "Anual";
    }

    @Override
    public String toString() {
        return "PlanoAnual [Tipo: " + getTipoPlano() + " | Desconto: 15%]";
    }
}
