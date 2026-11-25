package br.com.academiapoo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Registro de entrada ou saida de um cliente na catraca.
 */
public class RegistoAcesso {

    private static final DateTimeFormatter FORMATADOR_DATA_HORA =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /** Tipos de passagem na catraca. */
    public enum TipoAcao {
        ENTRADA,
        SAIDA
    }

    // registro de catraca e historico imutavel, por isso esses atributos nao tem setter
    private Client cliente;
    private TipoAcao tipoAcao;
    private LocalDateTime dataHora;

    /** Construtor completo com data e hora especificas. */
    public RegistoAcesso(Client cliente, TipoAcao tipoAcao, LocalDateTime dataHora) {
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

    /** Construtor usando a data e hora atuais. */
    public RegistoAcesso(Client cliente, TipoAcao tipoAcao) {
        this(cliente, tipoAcao, LocalDateTime.now());
    }

    /** Retorna o cliente do registro. */
    public Client getCliente() {
        return this.cliente;
    }

    /** Retorna se foi ENTRADA ou SAIDA. */
    public TipoAcao getTipoAcao() {
        return this.tipoAcao;
    }

    /** Retorna a data e hora do registro. */
    public LocalDateTime getDataHora() {
        return this.dataHora;
    }

    @Override
    public String toString() {
        return String.format(
                "RegistoAcesso [Cliente: %s (ID: %d) | Ação: %s | Data/Hora: %s]",
                this.cliente.getName(),
                this.cliente.getId(),
                this.tipoAcao.name(),
                this.dataHora.format(FORMATADOR_DATA_HORA)
        );
    }
}
