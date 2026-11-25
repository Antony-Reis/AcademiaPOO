package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Gera o balanco mensal de receitas e despesas da academia (restrito a Administrador).
 */
public class BalancoMensal {

    /** Mes e ano de referencia do balanco. */
    // nao tem setter pra nao mudar o mes depois de fechar o objeto
    private YearMonth mesReferencia;

    /** Receita vinda de aulas e planos. */
    private double receitasAulasPlanos;

    /** Receita vinda da lojinha. */
    private double receitasLoja;

    /** Gasto com limpeza. */
    private double despesaLimpeza;

    /** Gasto com instrutores e funcionarios. */
    private double despesaInstrutores;

    /** Outras despesas gerais. */
    private double outrasDespesas;

    /** Despesas extras lancadas dinamicamente. */
    // nao tem setter porque cada despesa entra pelo metodo registrarDespesa (com trava de admin)
    private List<String> despesasLancadas;

    /** Construtor completo do balanco mensal. */
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
        this.despesasLancadas = new ArrayList<>();
    }

    // trava pra so administrador conseguir abrir o balanco ou mexer nas despesas
    private void validarAcessoAdministrador(Usuario usuarioLogado) {
        if (!(usuarioLogado instanceof Administrador)) {
            String nome;
            if (usuarioLogado != null) {
                nome = usuarioLogado.getNome();
            } else {
                nome = "Não autenticado";
            }
            throw new SecurityException("Acesso negado para '" + nome
                    + "': apenas Administradores podem gerenciar despesas ou visualizar o Balanço Mensal.");
        }
    }

    /** Lanca uma nova despesa no mes (somente Administrador). */
    public void registrarDespesa(Usuario usuarioLogado, String descricao, double valor) {
        validarAcessoAdministrador(usuarioLogado);
        if (descricao == null || descricao.trim().isEmpty()) {
            throw new IllegalArgumentException("A descrição da despesa é obrigatória.");
        }
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0.0) {
            throw new IllegalArgumentException("O valor da despesa é inválido.");
        }
        this.outrasDespesas += valor;
        this.despesasLancadas.add(String.format("%s (R$ %.2f)", descricao.trim(), valor));
    }

    /** Retorna a lista de despesas extras lancadas (somente leitura). */
    public List<String> getDespesasLancadas() {
        return Collections.unmodifiableList(this.despesasLancadas);
    }

    /** Gera o texto completo do balanco mensal (somente Administrador). */
    public String gerarBalanco(Usuario usuarioLogado) {
        validarAcessoAdministrador(usuarioLogado);

        double totalReceitas = calcularTotalReceitas();
        double totalDespesas = calcularTotalDespesas();
        double saldoLiquido = totalReceitas - totalDespesas;

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(
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
        ));

        if (!this.despesasLancadas.isEmpty()) {
            sb.append("\nDespesas extras lançadas:");
            for (int i = 0; i < this.despesasLancadas.size(); i++) {
                sb.append("\n - ").append(this.despesasLancadas.get(i));
            }
        }
        return sb.toString();
    }

    /** Soma as receitas de aulas e loja. */
    public double calcularTotalReceitas() {
        return this.receitasAulasPlanos + this.receitasLoja;
    }

    /** Soma todas as despesas do mes. */
    public double calcularTotalDespesas() {
        return this.despesaLimpeza + this.despesaInstrutores + this.outrasDespesas;
    }

    /** Calcula o saldo liquido (receitas menos despesas). */
    public double calcularSaldoLiquido() {
        return calcularTotalReceitas() - calcularTotalDespesas();
    }

    /** Retorna o mes de referencia. */
    public YearMonth getMesReferencia() {
        return this.mesReferencia;
    }

    /** Retorna a receita de aulas e planos. */
    public double getReceitasAulasPlanos() {
        return this.receitasAulasPlanos;
    }

    /** Retorna a receita da loja. */
    public double getReceitasLoja() {
        return this.receitasLoja;
    }

    /** Retorna o gasto com limpeza. */
    public double getDespesaLimpeza() {
        return this.despesaLimpeza;
    }

    /** Retorna o gasto com instrutores. */
    public double getDespesaInstrutores() {
        return this.despesaInstrutores;
    }

    /** Retorna outras despesas. */
    public double getOutrasDespesas() {
        return this.outrasDespesas;
    }

    @Override
    public String toString() {
        return String.format("BalancoMensal [Referência: %02d/%d | Acesso restrito a Administrador]",
                this.mesReferencia.getMonthValue(), this.mesReferencia.getYear());
    }
}
