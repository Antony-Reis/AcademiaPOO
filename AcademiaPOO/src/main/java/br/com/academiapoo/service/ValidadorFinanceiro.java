package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Elo concreto do padrão Chain of Responsibility que verifica se o cliente
 * possui diária ou mensalidade paga e sem pendências financeiras.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class ValidadorFinanceiro extends ValidadorAcesso {

    /** Lista de agendamentos utilizados para verificar pagamentos confirmados. */
    private List<Agendamento> agendamentos;

    /**
     * Construtor padrão sem agendamentos pré-carregados.
     */
    public ValidadorFinanceiro() {
        super();
        this.agendamentos = new ArrayList<>();
    }

    /**
     * Construtor que recebe a lista de agendamentos para verificação financeira.
     *
     * @param agendamentos Lista de agendamentos registrados no sistema.
     */
    public ValidadorFinanceiro(List<Agendamento> agendamentos) {
        super();
        this.agendamentos = (agendamentos != null) ? new ArrayList<>(agendamentos) : new ArrayList<>();
    }

    /**
     * Adiciona um agendamento à base de verificação financeira.
     *
     * @param agendamento O agendamento a ser considerado.
     */
    public void adicionarAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            this.agendamentos.add(agendamento);
        }
    }

    /**
     * Retorna a lista imutável de agendamentos monitorados.
     *
     * @return Lista de agendamentos.
     */
    public List<Agendamento> getAgendamentos() {
        return Collections.unmodifiableList(this.agendamentos);
    }

    /**
     * Verifica se o cliente possui ao menos um agendamento definitivo com pagamento efetivado.
     *
     * @param cliente O cliente que solicita acesso.
     * @return {@code true} se estiver em dia e os próximos elos aprovarem.
     */
    @Override
    public boolean validar(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca - Financeiro] Acesso negado: cliente não informado.");
            return false;
        }

        boolean possuiPagamentoConfirmado = false;
        for (Agendamento ag : this.agendamentos) {
            if (ag.getCliente().getId().equals(cliente.getId())
                    && ag.getFase() == FaseAgendamento.DEFINITIVO
                    && ag.getPagamento().isPago()) {
                possuiPagamentoConfirmado = true;
                break;
            }
        }

        if (!possuiPagamentoConfirmado) {
            System.out.printf("[Catraca - Financeiro] Acesso negado para %s: sem diária/plano pago e confirmado.%n",
                    cliente.getName());
            return false;
        }

        return validarProximo(cliente);
    }

    /**
     * Retorna uma representação textual do validador financeiro.
     *
     * @return String descrevendo o validador e a quantidade de agendamentos monitorados.
     */
    @Override
    public String toString() {
        return String.format("ValidadorFinanceiro [Agendamentos monitorados: %d] -> %s",
                this.agendamentos.size(), super.toString());
    }
}
