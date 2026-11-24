package br.com.academiapoo.util;

import br.com.academiapoo.model.*;

import java.util.Comparator;

/**
 * Implementação de {@link Comparator} para ordenação de objetos {@link Agendamento}
 * por diferentes critérios (data da aula, data do agendamento, valor, nome do cliente ou fase).
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class AgendamentoComparator implements Comparator<Agendamento> {

    /**
     * Critérios disponíveis para comparação de agendamentos.
     */
    public enum Criterio {
        DATA_AULA,
        DATA_AGENDAMENTO,
        VALOR,
        CLIENTE_NOME,
        FASE
    }

    /** Critério de ordenação selecionado. */
    private Criterio criterio;

    /**
     * Construtor padrão que ordena agendamentos pela data da aula.
     */
    public AgendamentoComparator() {
        this(Criterio.DATA_AULA);
    }

    /**
     * Construtor que define o critério de ordenação desejado.
     *
     * @param criterio O critério de comparação.
     */
    public AgendamentoComparator(Criterio criterio) {
        super();
        if (criterio == null) {
            throw new IllegalArgumentException("O critério de comparação não pode ser nulo.");
        }
        this.criterio = criterio;
    }

    /**
     * Retorna o critério atual de ordenação.
     *
     * @return O critério configurado.
     */
    public Criterio getCriterio() {
        return this.criterio;
    }

    /**
     * Altera o critério de ordenação.
     *
     * @param criterio O novo critério.
     */
    public void setCriterio(Criterio criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("O critério de comparação não pode ser nulo.");
        }
        this.criterio = criterio;
    }

    /**
     * Compara dois agendamentos de acordo com o critério configurado.
     *
     * @param a1 Primeiro agendamento.
     * @param a2 Segundo agendamento.
     * @return Valor negativo, zero ou positivo conforme a ordem.
     */
    @Override
    public int compare(Agendamento a1, Agendamento a2) {
        if (a1 == a2) {
            return 0;
        }
        if (a1 == null) {
            return -1;
        }
        if (a2 == null) {
            return 1;
        }

        switch (this.criterio) {
            case DATA_AGENDAMENTO:
                return a1.getDataAgendamento().compareTo(a2.getDataAgendamento());
            case VALOR:
                return Double.compare(a1.getPreco(), a2.getPreco());
            case CLIENTE_NOME:
                return a1.getCliente().getName().compareToIgnoreCase(a2.getCliente().getName());
            case FASE:
                return a1.getFase().name().compareTo(a2.getFase().name());
            case DATA_AULA:
            default:
                int cmpData = a1.getDataAula().compareTo(a2.getDataAula());
                return (cmpData != 0) ? cmpData : Integer.compare(a1.getId(), a2.getId());
        }
    }

    /**
     * Retorna uma representação textual do comparador de agendamentos.
     *
     * @return String indicando o critério atual.
     */
    @Override
    public String toString() {
        return "AgendamentoComparator [Critério: " + this.criterio.name() + "]";
    }
}
