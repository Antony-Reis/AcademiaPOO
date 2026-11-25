package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Emite extratos diarios, mensais e a conta individual de cada cliente.
 */
public class Extrato {

    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Lista de agendamentos registrados no extrato. */
    // nao tem setter pra evitar que a lista inteira seja substituida fora dos metodos
    private List<Agendamento> agendamentos;

    /** Lista de vendas de produtos da loja. */
    // nao tem setter porque as vendas entram por registrarVendaProduto
    private List<VendaProduto> vendasLoja;

    private static class VendaProduto {
        private final Client cliente;
        private final Produto produto;
        private final LocalDate dataVenda;

        private VendaProduto(Client cliente, Produto produto, LocalDate dataVenda) {
            this.cliente = cliente;
            this.produto = produto;
            this.dataVenda = dataVenda;
        }
    }

    /** Construtor padrao. */
    public Extrato() {
        super();
        this.agendamentos = new ArrayList<>();
        this.vendasLoja = new ArrayList<>();
    }

    /** Adiciona um agendamento ao extrato. */
    public void adicionarAgendamento(Agendamento agendamento) {
        if (agendamento != null) {
            this.agendamentos.add(agendamento);
        }
    }

    /** Registra venda de produto sem cliente vinculado. */
    public void registrarVendaProduto(Produto produto, LocalDate dataVenda) {
        registrarVendaProduto(null, produto, dataVenda);
    }

    /** Registra venda de produto vinculada a um cliente. */
    public void registrarVendaProduto(Client cliente, Produto produto, LocalDate dataVenda) {
        if (produto == null || dataVenda == null) {
            throw new IllegalArgumentException("Produto e data da venda são obrigatórios.");
        }
        this.vendasLoja.add(new VendaProduto(cliente, produto, dataVenda));
    }

    /** Soma a receita liquida das aulas pagas. */
    public double calcularReceitaAulas() {
        double total = 0.0;
        for (Agendamento ag : this.agendamentos) {
            if (ag.getPagamento().isPago()) {
                total += (ag.getPagamento().getValorPago() - ag.getPagamento().getValorDevolvido());
            }
        }
        return total;
    }

    /** Soma a receita das vendas da lojinha. */
    public double calcularReceitaLoja() {
        double total = 0.0;
        for (VendaProduto vp : this.vendasLoja) {
            total += vp.produto.getPreco();
        }
        return total;
    }

    /** Gera o relatorio diario de aulas e vendas da loja. */
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

    /** Gera o relatorio mensal consolidado de aulas e loja. */
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

    /** Emite a conta individual (nota fiscal) de um cliente com aulas, reembolsos e compras. */
    public String gerarExtratoCliente(Client cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente é obrigatório para emitir a conta.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== CONTA / EXTRATO DO CLIENTE: ").append(cliente.getName())
          .append(" (ID ").append(cliente.getId()).append(") ===\n");

        double totalCliente = 0.0;
        for (int i = 0; i < this.agendamentos.size(); i++) {
            Agendamento ag = this.agendamentos.get(i);
            if (ag.getCliente().getId().equals(cliente.getId())) {
                double pago = ag.getPagamento().isPago() ? ag.getPagamento().getValorPago() : 0.0;
                double dev = ag.getPagamento().getValorDevolvido();
                double liq = pago - dev;
                totalCliente += liq;
                sb.append(String.format(" - Aula #%d (%s - %s) | Pago: R$ %.2f | Reembolso: R$ %.2f | Líquido: R$ %.2f%n",
                        ag.getId(), ag.getModalidade().getNome(), ag.getFase().getDescricao(), pago, dev, liq));
            }
        }

        for (int i = 0; i < this.vendasLoja.size(); i++) {
            VendaProduto vp = this.vendasLoja.get(i);
            if (vp.cliente != null && vp.cliente.getId().equals(cliente.getId())) {
                totalCliente += vp.produto.getPreco();
                sb.append(String.format(" - Produto (%s): %s | Valor: R$ %.2f%n",
                        vp.dataVenda.format(FORMATADOR_DATA), vp.produto.getNome(), vp.produto.getPreco()));
            }
        }

        sb.append(String.format("Total da Conta: R$ %.2f", totalCliente));
        return sb.toString();
    }

    /** Retorna a lista de agendamentos (somente leitura). */
    public List<Agendamento> getAgendamentos() {
        return Collections.unmodifiableList(this.agendamentos);
    }

    @Override
    public String toString() {
        return String.format("Extrato [Aulas: R$ %.2f | Loja: R$ %.2f | Total: R$ %.2f]",
                calcularReceitaAulas(), calcularReceitaLoja(), (calcularReceitaAulas() + calcularReceitaLoja()));
    }
}
