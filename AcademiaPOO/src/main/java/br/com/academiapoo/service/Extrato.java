package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Responsável pela emissão de extratos e relatórios de vendas (diário e mensal)
 * consolidando receitas de agendamentos/planos e vendas da lojinha de produtos.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Extrato {

    /** Formatador padrão para exibição de datas. */
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Lista de agendamentos considerados no extrato. */
    private List<Agendamento> agendamentos;

    /** Lista de vendas de produtos da loja com suas respectivas datas. */
    private List<VendaProduto> vendasLoja;

    /**
     * Registro interno de uma venda de produto com data.
     */
    private static class VendaProduto {
        private final Produto produto;
        private final LocalDate dataVenda;

        private VendaProduto(Produto produto, LocalDate dataVenda) {
            this.produto = produto;
            this.dataVenda = dataVenda;
        }
    }

    /**
     * Construtor padrão do extrato.
     */
    public Extrato() {
        super();
        this.agendamentos = new ArrayList<>();
        this.vendasLoja = new ArrayList<>();
    }

    /**
     * Adiciona um agendamento ao extrato.
     *
     * @param agendamento O agendamento a registrar.
     */
    public void adicionarAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            this.agendamentos.add(agendamento);
        }
    }

    /**
     * Registra a venda de um produto na lojinha em uma data específica.
     *
     * @param produto O produto vendido.
     * @param dataVenda A data da venda.
     */
    public void registrarVendaProduto(Produto produto, LocalDate dataVenda) {
        if (produto == null || dataVenda == null) {
            throw new IllegalArgumentException("Produto e data da venda são obrigatórios.");
        }
        this.vendasLoja.add(new VendaProduto(produto, dataVenda));
    }

    /**
     * Calcula a receita líquida gerada por agendamentos (valor pago menos eventuais reembolsos).
     *
     * @return Total líquido arrecadado em aulas/planos.
     */
    public double calcularReceitaAulas() {
        double total = 0.0;
        for (Agendamento ag : this.agendamentos) {
            if (ag.getPagamento().isPago()) {
                total += (ag.getPagamento().getValorPago() - ag.getPagamento().getValorDevolvido());
            }
        }
        return total;
    }

    /**
     * Calcula a receita total das vendas da lojinha.
     *
     * @return Total arrecadado com produtos.
     */
    public double calcularReceitaLoja() {
        double total = 0.0;
        for (VendaProduto vp : this.vendasLoja) {
            total += vp.produto.getPreco();
        }
        return total;
    }

    /**
     * Gera um relatório diário de vendas e agendamentos pagos para a data informada.
     *
     * @param dia A data de referência do relatório.
     * @return Texto formatado com o resumo diário.
     */
    public String gerarRelatorioDiario(LocalDate dia) {
        if (dia == null) {
            throw new IllegalArgumentException("A data para o relatório diário é obrigatória.");
        }
        double totalDia = 0.0;
        StringBuilder sb = new StringBuilder();
        sb.append("=== EXTRATO DIÁRIO (").append(dia.format(FORMATADOR_DATA)).append(") ===\n");

        for (Agendamento ag : this.agendamentos) {
            if (ag.getDataAgendamento().equals(dia) && ag.getPagamento().isPago()) {
                double liquido = ag.getPagamento().getValorPago() - ag.getPagamento().getValorDevolvido();
                totalDia += liquido;
                sb.append(String.format(" - Aula #%d (%s) | Cliente: %s | Líquido: R$ %.2f%n",
                        ag.getId(), ag.getModalidade().getNome(), ag.getCliente().getName(), liquido));
            }
        }
        for (VendaProduto vp : this.vendasLoja) {
            if (vp.dataVenda.equals(dia)) {
                totalDia += vp.produto.getPreco();
                sb.append(String.format(" - Produto: %s | Valor: R$ %.2f%n",
                        vp.produto.getNome(), vp.produto.getPreco()));
            }
        }
        sb.append(String.format("Total do Dia: R$ %.2f", totalDia));
        return sb.toString();
    }

    /**
     * Gera um relatório mensal consolidando aulas e vendas da loja no mês informado.
     *
     * @param mes O mês/ano de referência.
     * @return Texto formatado com o resumo mensal.
     */
    public String gerarRelatorioMensal(YearMonth mes) {
        if (mes == null) {
            throw new IllegalArgumentException("O mês de referência é obrigatório.");
        }
        double totalAulasMes = 0.0;
        double totalLojaMes = 0.0;

        for (Agendamento ag : this.agendamentos) {
            if (YearMonth.from(ag.getDataAgendamento()).equals(mes) && ag.getPagamento().isPago()) {
                totalAulasMes += (ag.getPagamento().getValorPago() - ag.getPagamento().getValorDevolvido());
            }
        }
        for (VendaProduto vp : this.vendasLoja) {
            if (YearMonth.from(vp.dataVenda).equals(mes)) {
                totalLojaMes += vp.produto.getPreco();
            }
        }

        return String.format(
                "=== RELATÓRIO MENSAL (%02d/%d) ===%nReceita Aulas/Planos: R$ %.2f%nReceita Lojinha: R$ %.2f%nTotal Geral: R$ %.2f",
                mes.getMonthValue(), mes.getYear(), totalAulasMes, totalLojaMes, (totalAulasMes + totalLojaMes)
        );
    }

    /**
     * Retorna a lista imutável de agendamentos do extrato.
     *
     * @return Lista de agendamentos.
     */
    public List<Agendamento> getAgendamentos() {
        return Collections.unmodifiableList(this.agendamentos);
    }

    /**
     * Retorna uma representação textual resumida do extrato.
     *
     * @return String com totais consolidados.
     */
    @Override
    public String toString() {
        return String.format("Extrato [Aulas: R$ %.2f | Loja: R$ %.2f | Total: R$ %.2f]",
                calcularReceitaAulas(), calcularReceitaLoja(), (calcularReceitaAulas() + calcularReceitaLoja()));
    }
}
