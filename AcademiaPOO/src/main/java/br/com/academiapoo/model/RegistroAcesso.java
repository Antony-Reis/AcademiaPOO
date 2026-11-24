package br.com.academiapoo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um registro cronológico de passagem pela catraca da academia,
 * armazenando o cliente, o tipo de ação (ENTRADA ou SAIDA) e a data/hora exatas.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class RegistroAcesso {

    /** Formatador padrão brasileiro para data e hora. */
    private static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /**
     * Tipos possíveis de movimentação na catraca.
     */
    public enum TipoAcao {
        ENTRADA,
        SAIDA
    }

    /** Cliente que realizou a movimentação. */
    private Client cliente;

    /** Tipo de ação registrada (ENTRADA ou SAIDA). */
    private TipoAcao tipoAcao;

    /** Data e hora exatas da passagem na catraca. */
    private LocalDateTime dataHora;

    /**
     * Construtor completo do registro de acesso.
     *
     * @param cliente O cliente que passou pela catraca.
     * @param tipoAcao O tipo de movimentação (ENTRADA ou SAIDA).
     * @param dataHora A data e hora exatas do registro.
     */
    public RegistroAcesso(Client cliente, TipoAcao tipoAcao, LocalDateTime dataHora) {
        super();
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente do registro de acesso é obrigatório.");
        }
        if (tipoAcao == null) {
            throw new IllegalArgumentException("O tipo de ação (ENTRADA/SAIDA) é obrigatório.");
        }
        if (dataHora == null) {
            throw new IllegalArgumentException("A data e hora do registro são obrigatórias.");
        }

        this.cliente = cliente;
        this.tipoAcao = tipoAcao;
        this.dataHora = dataHora;
    }

    /**
     * Construtor que utiliza a data e hora atuais do sistema.
     *
     * @param cliente O cliente que passou pela catraca.
     * @param tipoAcao O tipo de movimentação (ENTRADA ou SAIDA).
     */
    public RegistroAcesso(Client cliente, TipoAcao tipoAcao) {
        this(cliente, tipoAcao, LocalDateTime.now());
    }

    /**
     * Retorna o cliente associado ao registro.
     *
     * @return O cliente.
     */
    public Client getCliente() {
        return this.cliente;
    }

    /**
     * Retorna o tipo de ação do registro.
     *
     * @return {@code ENTRADA} ou {@code SAIDA}.
     */
    public TipoAcao getTipoAcao() {
        return this.tipoAcao;
    }

    /**
     * Retorna a data e hora do registro.
     *
     * @return A data e hora do acesso.
     */
    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    /**
     * Retorna uma representação textual do registro de acesso.
     *
     * @return String formatada com cliente, ação e data/hora.
     */
    @Override
    public String toString() {
        return String.format(
                "RegistroAcesso [Cliente: %s (ID: %d) | Ação: %s | Data/Hora: %s]",
                this.cliente.getName(),
                this.cliente.getId(),
                this.tipoAcao.name(),
                this.dataHora.format(FORMATADOR_DATA_HORA)
        );
    }
}
