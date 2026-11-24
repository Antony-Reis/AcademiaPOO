package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Controla a catraca eletrônica da academia utilizando o padrão Chain of Responsibility.
 * Executa a cadeia de validadores configurada e, em caso de aprovação,
 * grava cronologicamente os registros de entrada e saída em {@link RegistroAcesso}.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Catraca {

    /** Primeiro elo da cadeia de validação de acesso. */
    private ValidadorAcesso cadeiaValidacao;

    /** Histórico cronológico de entradas e saídas liberadas. */
    private List<RegistroAcesso> historicoAcessos;

    /**
     * Construtor da catraca recebendo o início da cadeia de validação.
     *
     * @param cadeiaValidacao O primeiro validador da cadeia.
     */
    public Catraca(ValidadorAcesso cadeiaValidacao) {
        super();
        this.cadeiaValidacao = cadeiaValidacao;
        this.historicoAcessos = new ArrayList<>();
    }

    /**
     * Valida o acesso do cliente pela cadeia de validadores e, se aprovado,
     * registra a entrada com data e hora atuais.
     *
     * @param cliente O cliente que deseja entrar na academia.
     * @return {@code true} se a entrada foi liberada e registrada; {@code false} caso contrário.
     */
    public boolean registrarEntrada(Client cliente) {
        boolean aprovado = (this.cadeiaValidacao == null) || this.cadeiaValidacao.validar(cliente);
        if (!aprovado) {
            return false;
        }

        RegistroAcesso registro = new RegistroAcesso(cliente, RegistroAcesso.TipoAcao.ENTRADA, LocalDateTime.now());
        this.historicoAcessos.add(registro);
        System.out.printf("[Catraca] Entrada liberada para %s.%n", cliente.getName());
        return true;
    }

    /**
     * Registra a saída do cliente pela catraca com data e hora atuais.
     *
     * @param cliente O cliente que está saindo da academia.
     * @return {@code true} se a saída foi registrada com sucesso.
     */
    public boolean registrarSaida(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca] Saída não registrada: cliente não informado.");
            return false;
        }

        RegistroAcesso registro = new RegistroAcesso(cliente, RegistroAcesso.TipoAcao.SAIDA, LocalDateTime.now());
        this.historicoAcessos.add(registro);
        System.out.printf("[Catraca] Saída registrada para %s.%n", cliente.getName());
        return true;
    }

    /**
     * Retorna a cadeia de validadores configurada na catraca.
     *
     * @return O primeiro validador da cadeia.
     */
    public ValidadorAcesso getCadeiaValidacao() {
        return this.cadeiaValidacao;
    }

    /**
     * Atualiza a cadeia de validadores da catraca.
     *
     * @param cadeiaValidacao O novo primeiro elo da cadeia.
     */
    public void setCadeiaValidacao(ValidadorAcesso cadeiaValidacao) {
        this.cadeiaValidacao = cadeiaValidacao;
    }

    /**
     * Retorna o histórico imutável de registros de acesso.
     *
     * @return Lista cronológica de {@link RegistroAcesso}.
     */
    public List<RegistroAcesso> getHistoricoAcessos() {
        return Collections.unmodifiableList(this.historicoAcessos);
    }

    /**
     * Retorna uma representação textual do estado atual da catraca e seu histórico.
     *
     * @return String descrevendo a cadeia e o total de registros.
     */
    @Override
    public String toString() {
        return String.format(
                "Catraca [Cadeia: %s | Total de Registros: %d]",
                (this.cadeiaValidacao != null ? this.cadeiaValidacao.toString() : "Nenhuma"),
                this.historicoAcessos.size()
        );
    }
}
