package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Valida na catraca se o cliente tem agendamento definitivo e pago.
 */
public class ValidadorFinanceiro extends ValidadorAcesso {

    /** Lista de agendamentos usados na checagem. */
    // nao tem setter porque novos agendamentos entram via adicionarAgendamento
    private List<Agendamento> agendamentos;

    /** Construtor padrao. */
    public ValidadorFinanceiro() {
        super();
        this.agendamentos = new ArrayList<>();
    }

    /** Construtor recebendo uma lista de agendamentos. */
    public ValidadorFinanceiro(List<Agendamento> agendamentos) {
        super();
        if (agendamentos != null) {
            this.agendamentos = new ArrayList<>(agendamentos);
        } else {
            this.agendamentos = new ArrayList<>();
        }
    }

    /** Adiciona um agendamento na lista do validador. */
    public void adicionarAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            this.agendamentos.add(agendamento);
        }
    }

    /** Retorna a lista de agendamentos (somente leitura). */
    public List<Agendamento> getAgendamentos() {
        return Collections.unmodifiableList(this.agendamentos);
    }

    @Override
    public boolean validar(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca - Financeiro] Acesso negado: cliente não informado.");
            return false;
        }

        boolean achou = false;
        // percorre a lsita de agendamentos
        for (int i = 0; i < this.agendamentos.size(); i++) {
            Agendamento temp = this.agendamentos.get(i);
            if (temp.getCliente().getId().equals(cliente.getId())
                    && temp.getFase() == FaseAgendamento.DEFINITIVO
                    && temp.getPagamento().isPago()) {
                achou = true;
                break;
            }
        }
        // System.out.println("debug: validador financeiro achou=" + achou);

        if (!achou) {
            System.out.printf("[Catraca - Financeiro] Acesso negado para %s: sem diária/plano pago e confirmado.%n",
                    cliente.getName());
            return false;
        }

        return validarProximo(cliente);
    }

    @Override
    public String toString() {
        return String.format("ValidadorFinanceiro [Agendamentos monitorados: %d] -> %s",
                this.agendamentos.size(), super.toString());
    }
}
