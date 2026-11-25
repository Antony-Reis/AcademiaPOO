package br.com.academiapoo.model;

/**
 * Plano diaria (avulso) sem desconto, focado em turistas.
 */
public class PlanoDiaria implements Plano {

    /** Construtor padrao. */
    public PlanoDiaria() {
        super();
    }

    @Override
    public double calcularValor(double valorBase) {
        return valorBase;
    }

    @Override
    public String getTipoPlano() {
        return "Diária";
    }

    @Override
    public String toString() {
        return "PlanoDiaria [Tipo: " + getTipoPlano() + " | Desconto: 0% (Ideal para turistas)]";
    }
}
