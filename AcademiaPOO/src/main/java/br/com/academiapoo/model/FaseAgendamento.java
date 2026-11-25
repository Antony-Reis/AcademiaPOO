package br.com.academiapoo.model;

/**
 * Fases possiveis de um agendamento na academia.
 */
public enum FaseAgendamento {

    /** Pre-reserva aguardando confirmacao. */
    PRELIMINAR("Preliminar"),

    /** Agendamento confirmado e pago. */
    DEFINITIVO("Definitivo"),

    /** Agendamento cancelado por expiracao ou desistencia. */
    CANCELADO("Cancelado");

    /** Descricao da fase. */
    private final String descricao;

    FaseAgendamento(String descricao) {
        this.descricao = descricao;
    }

    /** Retorna a descricao da fase. */
    public String getDescricao() {
        return this.descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
