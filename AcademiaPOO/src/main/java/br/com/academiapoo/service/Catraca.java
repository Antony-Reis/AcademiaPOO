package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// catraca usando chain of responsibility (baseado no exemplo de validadores)
/**
 * Controla a catraca da academia, valida a cadeia de acesso e registra entradas e saidas.
 */
public class Catraca {

    private static final DateTimeFormatter FMT_DATA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FMT_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Primeiro validador da cadeia. */
    private ValidadorAcesso cadeiaValidacao;

    /** Historico cronologico de entradas e saidas. */
    // nao tem setter pro historico nao ser apagado ou trocado manualmente
    private List<RegistoAcesso> historicoAcessos;

    /** Construtor da catraca com o inicio da cadeia de validacao. */
    public Catraca(ValidadorAcesso cadeiaValidacao) {
        super();
        this.cadeiaValidacao = cadeiaValidacao;
        this.historicoAcessos = new ArrayList<>();
    }

    /** Valida o cliente na cadeia e registra a entrada se passar. */
    public boolean registrarEntrada(Client cliente) {
        boolean achouBloqueio = false;
        if (this.cadeiaValidacao != null) {
            boolean temp = this.cadeiaValidacao.validar(cliente);
            if (!temp) {
                achouBloqueio = true;
            }
        }
        if (achouBloqueio) {
            return false;
        }

        RegistoAcesso reg = new RegistoAcesso(cliente, RegistoAcesso.TipoAcao.ENTRADA, LocalDateTime.now());
        this.historicoAcessos.add(reg);
        // System.out.println("debug: catraca liberou " + cliente.getName());
        System.out.printf("[Catraca] Entrada liberada para %s.%n", cliente.getName());
        return true;
    }

    /** Registra a saida do cliente na catraca. */
    public boolean registrarSaida(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca] Saída não registrada: cliente não informado.");
            return false;
        }

        RegistoAcesso aux = new RegistoAcesso(cliente, RegistoAcesso.TipoAcao.SAIDA, LocalDateTime.now());
        this.historicoAcessos.add(aux);
        System.out.printf("[Catraca] Saída registrada para %s.%n", cliente.getName());
        return true;
    }

    /** Lista os clientes que entraram e ainda nao registraram saida. */
    public List<Client> listarPessoasPresentes() {
        List<Client> presentes = new ArrayList<>();
        for (int i = 0; i < this.historicoAcessos.size(); i++) {
            RegistoAcesso reg = this.historicoAcessos.get(i);
            Client c = reg.getCliente();
            if (reg.getTipoAcao() == RegistoAcesso.TipoAcao.ENTRADA) {
                if (!jaEstaNaLista(presentes, c.getId())) {
                    presentes.add(c);
                }
            } else if (reg.getTipoAcao() == RegistoAcesso.TipoAcao.SAIDA) {
                removerDaListaPorId(presentes, c.getId());
            }
        }
        return presentes;
    }

    private boolean jaEstaNaLista(List<Client> lista, Integer id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    private void removerDaListaPorId(List<Client> lista, Integer id) {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId().equals(id)) {
                lista.remove(i);
                return;
            }
        }
    }

    /** Conta quantas pessoas estao dentro da academia no momento. */
    public int contarPessoasPresentes() {
        return listarPessoasPresentes().size();
    }

    /** Gera o relatorio diario de entradas e saidas da catraca. */
    public String gerarRelatorioDiario(LocalDate dia) {
        if (dia == null) {
            throw new IllegalArgumentException("A data é obrigatória.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO CATRACA DIÁRIO (").append(dia.format(FMT_DATA)).append(") ===\n");
        int total = 0;
        for (int i = 0; i < this.historicoAcessos.size(); i++) {
            RegistoAcesso reg = this.historicoAcessos.get(i);
            if (reg.getDataHora().toLocalDate().equals(dia)) {
                sb.append(" - ").append(reg.getCliente().getName())
                  .append(" | ").append(reg.getTipoAcao().name())
                  .append(" | ").append(reg.getDataHora().format(FMT_DATA_HORA)).append("\n");
                total++;
            }
        }
        sb.append("Total de marcações no dia: ").append(total);
        return sb.toString();
    }

    /** Gera o relatorio mensal de entradas e saidas da catraca. */
    public String gerarRelatorioMensal(YearMonth mes) {
        if (mes == null) {
            throw new IllegalArgumentException("O mês é obrigatório.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("=== RELATÓRIO CATRACA MENSAL (%02d/%d) ===%n", mes.getMonthValue(), mes.getYear()));
        int entradas = 0;
        int saidas = 0;
        for (int i = 0; i < this.historicoAcessos.size(); i++) {
            RegistoAcesso reg = this.historicoAcessos.get(i);
            if (YearMonth.from(reg.getDataHora()).equals(mes)) {
                sb.append(" - ").append(reg.getCliente().getName())
                  .append(" | ").append(reg.getTipoAcao().name())
                  .append(" | ").append(reg.getDataHora().format(FMT_DATA_HORA)).append("\n");
                if (reg.getTipoAcao() == RegistoAcesso.TipoAcao.ENTRADA) {
                    entradas++;
                } else {
                    saidas++;
                }
            }
        }
        sb.append("Entradas: ").append(entradas).append(" | Saídas: ").append(saidas);
        return sb.toString();
    }

    /** Gera o relatorio anual de entradas e saidas da catraca. */
    public String gerarRelatorioAnual(int ano) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO CATRACA ANUAL (").append(ano).append(") ===\n");
        int entradas = 0;
        int saidas = 0;
        for (int i = 0; i < this.historicoAcessos.size(); i++) {
            RegistoAcesso reg = this.historicoAcessos.get(i);
            if (reg.getDataHora().getYear() == ano) {
                if (reg.getTipoAcao() == RegistoAcesso.TipoAcao.ENTRADA) {
                    entradas++;
                } else {
                    saidas++;
                }
            }
        }
        sb.append("Total de entradas no ano: ").append(entradas)
          .append(" | Total de saídas no ano: ").append(saidas);
        return sb.toString();
    }

    /** Retorna o primeiro validador da cadeia. */
    public ValidadorAcesso getCadeiaValidacao() {
        return this.cadeiaValidacao;
    }

    /** Altera a cadeia de validacao da catraca. */
    public void setCadeiaValidacao(ValidadorAcesso cadeiaValidacao) {
        this.cadeiaValidacao = cadeiaValidacao;
    }

    /** Retorna o historico de acessos (somente leitura). */
    public List<RegistoAcesso> getHistoricoAcessos() {
        return Collections.unmodifiableList(this.historicoAcessos);
    }

    @Override
    public String toString() {
        String nomeCadeia;
        if (this.cadeiaValidacao != null) {
            nomeCadeia = this.cadeiaValidacao.toString();
        } else {
            nomeCadeia = "Nenhuma";
        }
        return String.format(
                "Catraca [Cadeia: %s | Total de Registros: %d]",
                nomeCadeia,
                this.historicoAcessos.size()
        );
    }
}
