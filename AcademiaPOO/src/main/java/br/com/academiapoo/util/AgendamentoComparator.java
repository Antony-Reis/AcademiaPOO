package br.com.academiapoo.util;

import br.com.academiapoo.model.*;

import java.util.Comparator;

// ideia baseada no exemplo de comparator visto em aula
/**
 * Comparator para ordenar agendamentos por data, valor, cliente ou fase.
 */
public class AgendamentoComparator implements Comparator<Agendamento> {

    /** Criterios disponiveis para comparar agendamentos. */
    public enum Criterio {
        DATA_AULA,
        DATA_AGENDAMENTO,
        VALOR,
        CLIENTE_NOME,
        FASE
    }

    /** Criterio selecionado. */
    private Criterio criterio;

    /** Construtor padrao ordenando pela data da aula. */
    public AgendamentoComparator() {
        this(Criterio.DATA_AULA);
    }

    /** Construtor definindo o criterio de comparacao. */
    public AgendamentoComparator(Criterio criterio) {
        super();
        if (criterio == null) {
            throw new IllegalArgumentException("O critério de comparação não pode ser nulo.");
        }
        this.criterio = criterio;
    }

    /** Retorna o criterio atual. */
    public Criterio getCriterio() {
        return this.criterio;
    }

    /** Altera o criterio de comparacao. */
    public void setCriterio(Criterio criterio) {
        if (criterio == null) {
            throw new IllegalArgumentException("O critério de comparação não pode ser nulo.");
        }
        this.criterio = criterio;
    }

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

        int res = 0;
        switch (this.criterio) {
            case DATA_AGENDAMENTO:
                res = a1.getDataAgendamento().compareTo(a2.getDataAgendamento());
                break;
            case VALOR:
                res = Double.compare(a1.getPreco(), a2.getPreco());
                break;
            case CLIENTE_NOME:
                res = a1.getCliente().getName().compareToIgnoreCase(a2.getCliente().getName());
                break;
            case FASE:
                res = a1.getFase().name().compareTo(a2.getFase().name());
                break;
            case DATA_AULA:
            default:
                int cmpData = a1.getDataAula().compareTo(a2.getDataAula());
                if (cmpData != 0) {
                    res = cmpData;
                } else {
                    res = Integer.compare(a1.getId(), a2.getId());
                }
                break;
        }
        // System.out.println("debug: cmp agendamento = " + res);
        return res;
    }

    @Override
    public String toString() {
        return "AgendamentoComparator [Critério: " + this.criterio.name() + "]";
    }
}
