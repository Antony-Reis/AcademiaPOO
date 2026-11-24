package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.YearMonth;

/**
 * Responsável pelo fechamento mensal (Balanço Mensal) da academia,
 * confrontando receitas (diárias, mensalidades/planos e lojinha) com despesas
 * operacionais (limpeza, instrutores e outras despesas).
 *
 * Possui trava de segurança: apenas usuários do tipo {@link Administrador}
 * podem gerar ou visualizar o balanço, barrando {@link Colaborador} comum.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class BalancoMensal {

    /** Mês/ano de referência do balanço. */
    private YearMonth mesReferencia;

    /** Total de receitas oriundas de diárias, mensalidades e planos. */
    private double receitasAulasPlanos;

    /** Total de receitas oriundas da lojinha de produtos. */
    private double receitasLoja;

    /** Despesas com equipe e materiais de limpeza. */
    private double despesaLimpeza;

    /** Despesas com pagamento de instrutores. */
    private double despesaInstrutores;

    /** Outras despesas administrativas ou operacionais. */
    private double outrasDespesas;

    /**
     * Construtor completo do balanço mensal.
     *
     * @param mesReferencia Mês/ano do fechamento.
     * @param receitasAulasPlanos Receita de diárias, mensalidades e planos.
     * @param receitasLoja Receita da lojinha.
     * @param despesaLimpeza Gastos com limpeza.
     * @param despesaInstrutores Gastos com instrutores.
     * @param outrasDespesas Demais despesas operacionais.
     */
    public BalancoMensal(YearMonth mesReferencia, double receitasAulasPlanos, double receitasLoja,
                         double despesaLimpeza, double despesaInstrutores, double outrasDespesas) {
        super();
        if (mesReferencia == null) {
            throw new IllegalArgumentException("O mês de referência é obrigatório.");
        }
        this.mesReferencia = mesReferencia;
        this.receitasAulasPlanos = receitasAulasPlanos;
        this.receitasLoja = receitasLoja;
        this.despesaLimpeza = despesaLimpeza;
        this.despesaInstrutores = despesaInstrutores;
        this.outrasDespesas = outrasDespesas;
    }

    /**
     * Valida se o usuário autenticado possui perfil de {@link Administrador}.
     *
     * @param usuarioLogado O usuário autenticado no sistema.
     * @throws SecurityException Caso o usuário seja nulo ou não seja {@link Administrador}.
     */
    private void validarAcessoAdministrador(Usuario usuarioLogado) {
        if (!(usuarioLogado instanceof Administrador)) {
            String nome = (usuarioLogado != null) ? usuarioLogado.getNome() : "Não autenticado";
            throw new SecurityException("Acesso negado para '" + nome
                    + "': apenas Administradores podem visualizar o Balanço Mensal.");
        }
    }

    /**
     * Gera e retorna o relatório completo do Balanço Mensal, desde que o usuário
     * autenticado seja um {@link Administrador}.
     *
     * @param usuarioLogado O usuário autenticado pelo subsistema de login.
     * @return Relatório formatado com receitas, despesas e saldo líquido.
     */
    public String gerarBalanco(Usuario usuarioLogado) {
        validarAcessoAdministrador(usuarioLogado);

        double totalReceitas = calcularTotalReceitas();
        double totalDespesas = calcularTotalDespesas();
        double saldoLiquido = totalReceitas - totalDespesas;

        return String.format(
                "=== BALANÇO MENSAL (%02d/%d) [Emitido por: %s] ===%n"
                        + "Receitas (Aulas/Planos): R$ %.2f%n"
                        + "Receitas (Lojinha):      R$ %.2f%n"
                        + "Total de Receitas:       R$ %.2f%n"
                        + "Despesas (Limpeza):      R$ %.2f%n"
                        + "Despesas (Instrutores):  R$ %.2f%n"
                        + "Outras Despesas:         R$ %.2f%n"
                        + "Total de Despesas:       R$ %.2f%n"
                        + "Saldo Líquido:           R$ %.2f",
                this.mesReferencia.getMonthValue(),
                this.mesReferencia.getYear(),
                usuarioLogado.getNome(),
                this.receitasAulasPlanos,
                this.receitasLoja,
                totalReceitas,
                this.despesaLimpeza,
                this.despesaInstrutores,
                this.outrasDespesas,
                totalDespesas,
                saldoLiquido
        );
    }

    /**
     * Calcula o total de receitas do mês.
     *
     * @return Soma das receitas de aulas/planos e loja.
     */
    public double calcularTotalReceitas() {
        return this.receitasAulasPlanos + this.receitasLoja;
    }

    /**
     * Calcula o total de despesas do mês.
     *
     * @return Soma das despesas de limpeza, instrutores e outras.
     */
    public double calcularTotalDespesas() {
        return this.despesaLimpeza + this.despesaInstrutores + this.outrasDespesas;
    }

    /**
     * Calcula o saldo líquido (receitas menos despesas).
     *
     * @return O resultado financeiro do mês.
     */
    public double calcularSaldoLiquido() {
        return calcularTotalReceitas() - calcularTotalDespesas();
    }

    public YearMonth getMesReferencia() {
        return this.mesReferencia;
    }

    public double getReceitasAulasPlanos() {
        return this.receitasAulasPlanos;
    }

    public double getReceitasLoja() {
        return this.receitasLoja;
    }

    public double getDespesaLimpeza() {
        return this.despesaLimpeza;
    }

    public double getDespesaInstrutores() {
        return this.despesaInstrutores;
    }

    public double getOutrasDespesas() {
        return this.outrasDespesas;
    }

    /**
     * Retorna uma representação textual resumida do balanço mensal.
     *
     * @return String identificando o mês de referência.
     */
    @Override
    public String toString() {
        return String.format("BalancoMensal [Referência: %02d/%d | Acesso restrito a Administrador]",
                this.mesReferencia.getMonthValue(), this.mesReferencia.getYear());
    }
}
