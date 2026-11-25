package br.com.academiapoo.util;

import br.com.academiapoo.model.*;

import java.util.Comparator;

// ideia baseada no exemplo de comparator
/**
 * Comparator para ordenar clientes por nome, id, email ou situacao.
 */
public class ClienteComparator implements Comparator<Client> {

    /** Criterios disponiveis para comparar clientes. */
    public enum Criterio {
        NOME,
        ID,
        EMAIL,
        SITUACAO
    }

    /** Criterio selecionado. */
    private Criterio criterio;

    /** Construtor padrao ordenando por nome. */
    public ClienteComparator() {
        this(Criterio.NOME);
    }

    /** Construtor definindo o criterio de comparacao. */
    public ClienteComparator(Criterio criterio) {
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
    public int compare(Client c1, Client c2) {
        if (c1 == c2) {
            return 0;
        }
        if (c1 == null) {
            return -1;
        }
        if (c2 == null) {
            return 1;
        }

        int aux = 0;
        switch (this.criterio) {
            case ID:
                aux = Integer.compare(c1.getId(), c2.getId());
                break;
            case EMAIL:
                aux = c1.getEmail().compareToIgnoreCase(c2.getEmail());
                break;
            case SITUACAO:
                aux = Boolean.compare(c2.getSituation(), c1.getSituation());
                break;
            case NOME:
            default:
                aux = c1.getName().compareToIgnoreCase(c2.getName());
                break;
        }
        return aux;
    }

    @Override
    public String toString() {
        return "ClienteComparator [Critério: " + this.criterio.name() + "]";
    }
}
