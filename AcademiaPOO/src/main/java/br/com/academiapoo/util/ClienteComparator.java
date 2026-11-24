package br.com.academiapoo.util;

import br.com.academiapoo.model.*;

import java.util.Comparator;

/**
 * Implementação de {@link Comparator} para ordenação de objetos {@link Client}
 * por diferentes critérios (nome, id, e-mail ou situação).
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class ClienteComparator implements Comparator<Client> {

    /**
     * Critérios disponíveis para comparação de clientes.
     */
    public enum Criterio {
        NOME,
        ID,
        EMAIL,
        SITUACAO
    }

    /** Critério de ordenação selecionado. */
    private Criterio criterio;

    /**
     * Construtor padrão que ordena clientes por nome.
     */
    public ClienteComparator() {
        this(Criterio.NOME);
    }

    /**
     * Construtor que define o critério de ordenação desejado.
     *
     * @param criterio O critério de comparação.
     */
    public ClienteComparator(Criterio criterio) {
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
     * Compara dois clientes de acordo com o critério configurado.
     *
     * @param c1 Primeiro cliente.
     * @param c2 Segundo cliente.
     * @return Valor negativo, zero ou positivo conforme a ordem.
     */
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

        switch (this.criterio) {
            case ID:
                return Integer.compare(c1.getId(), c2.getId());
            case EMAIL:
                return c1.getEmail().compareToIgnoreCase(c2.getEmail());
            case SITUACAO:
                return Boolean.compare(c2.getSituation(), c1.getSituation());
            case NOME:
            default:
                return c1.getName().compareToIgnoreCase(c2.getName());
        }
    }

    /**
     * Retorna uma representação textual do comparador de clientes.
     *
     * @return String indicando o critério atual.
     */
    @Override
    public String toString() {
        return "ClienteComparator [Critério: " + this.criterio.name() + "]";
    }
}
