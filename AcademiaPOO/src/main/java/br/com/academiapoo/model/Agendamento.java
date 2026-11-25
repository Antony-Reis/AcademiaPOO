package br.com.academiapoo.model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Agendamento de aula com controle de fase (preliminar, definitivo, cancelado) e pagamento.
 */
public class Agendamento {

    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** Codigo do agendamento. */
    // nao tem setter pra nao mudar o id depois que o agendamento foi criado
    private Integer id;

    /** Cliente dono do agendamento. */
    private Client cliente;

    /** Modalidade e plano escolhidos (Bridge). */
    private Modalidade modalidade;

    /** Data em que o agendamento foi aberto. */
    // nao tem setter porque a data de criacao nao muda
    private LocalDate dataAgendamento;

    /** Data da aula marcada. */
    private LocalDate dataAula;

    /** Nome do instrutor da aula. */
    private String instrutor;

    /** Dados de pagamento no cartao. */
    // nao tem setter porque o pagamento e criado junto com o agendamento
    private Pagamento pagamento;

    /** Fase atual do agendamento. */
    // nao tem setter porque a fase so muda pelos metodos de confirmacao ou cancelamento
    private FaseAgendamento fase;

    /** Construtor completo do agendamento (inicia como preliminar). */
    public Agendamento(Integer id, Client cliente, Modalidade modalidade, LocalDate dataAgendamento,
                       LocalDate dataAula, String instrutor, String numeroCartao) {
        super();
        validarId(id);
        validarCliente(cliente);
        validarModalidade(modalidade);
        validarDatas(dataAgendamento, dataAula);
        validarInstrutor(instrutor);

        this.id = id;
        this.cliente = cliente;
        this.modalidade = modalidade;
        this.dataAgendamento = dataAgendamento;
        this.dataAula = dataAula;
        this.instrutor = instrutor.trim();
        this.pagamento = new Pagamento(numeroCartao, modalidade.calcularValorFinal());
        this.fase = FaseAgendamento.PRELIMINAR;
    }

    /** Construtor usando a data de hoje como data do agendamento. */
    public Agendamento(Integer id, Client cliente, Modalidade modalidade, LocalDate dataAula,
                       String instrutor, String numeroCartao) {
        this(id, cliente, modalidade, LocalDate.now(), dataAula, instrutor, numeroCartao);
    }

    // conta quantos dias uteis tem entre uma data e outra (pulando sabado e domingo)
    private long contarDiasUteis(LocalDate inicio, LocalDate fim) {
        if (inicio == null || fim == null || !inicio.isBefore(fim)) {
            return 0;
        }
        long diasUteis = 0;
        LocalDate atual = inicio;
        while (atual.isBefore(fim)) {
            atual = atual.plusDays(1);
            DayOfWeek diaSemana = atual.getDayOfWeek();
            if (diaSemana != DayOfWeek.SATURDAY && diaSemana != DayOfWeek.SUNDAY) {
                diasUteis++;
            }
        }
        return diasUteis;
    }

    /** Confirma o agendamento se faltar pelo menos 5 dias uteis pra aula; senao cancela. */
    public boolean confirmarAgendamento(LocalDate dataConfirmacao) {
        if (dataConfirmacao == null) {
            throw new IllegalArgumentException("A data de confirmação não pode ser nula.");
        }
        if (this.fase == FaseAgendamento.CANCELADO) {
            throw new IllegalStateException("Não é possível confirmar um agendamento já cancelado.");
        }
        if (this.fase == FaseAgendamento.DEFINITIVO) {
            throw new IllegalStateException("O agendamento já se encontra na fase Definitiva.");
        }

        long diasUteis = contarDiasUteis(dataConfirmacao, this.dataAula);

        if (diasUteis < 5) {
            this.fase = FaseAgendamento.CANCELADO;
            System.out.printf("[Regra de Negócio] Agendamento #%d cancelado: prazo de confirmação expirado (faltam %d dias úteis para a aula; exigido: mínimo de 5 dias úteis).%n",
                    this.id, diasUteis);
            return false;
        }

        this.fase = FaseAgendamento.DEFINITIVO;
        this.pagamento.processarPagamento();
        System.out.printf("[Regra de Negócio] Agendamento #%d confirmado como definitivo com %d dias úteis de antecedência.%n",
                this.id, diasUteis);
        return true;
    }

    /** Confirma o agendamento usando a data atual. */
    public boolean confirmarAgendamento() {
        return confirmarAgendamento(LocalDate.now());
    }

    /** Cancela o agendamento e devolve 50% se faltar 3 dias ou mais; senao retem 100%. */
    public double cancelarComReembolso(LocalDate dataDesistencia) {
        if (dataDesistencia == null) {
            throw new IllegalArgumentException("A data de desistência não pode ser nula.");
        }
        if (this.fase == FaseAgendamento.CANCELADO) {
            throw new IllegalStateException("O agendamento já se encontra cancelado.");
        }

        if (this.fase == FaseAgendamento.PRELIMINAR) {
            this.fase = FaseAgendamento.CANCELADO;
            System.out.printf("[Regra de Negócio] Agendamento preliminar #%d cancelado sem cobranças.%n", this.id);
            return 0.0;
        }

        long diasAteAula = ChronoUnit.DAYS.between(dataDesistencia, this.dataAula);
        this.fase = FaseAgendamento.CANCELADO;

        if (diasAteAula >= 3) {
            double devolvido = this.pagamento.processarReembolso(0.50);
            double retido = this.pagamento.getValorPago() - devolvido;
            System.out.printf("[Regra de Negócio] Desistência aceita com %d dias de antecedência. Devolução de 50%% (R$ %.2f). Retenção de 50%% (R$ %.2f).%n",
                    diasAteAula, devolvido, retido);
            return devolvido;
        } else {
            this.pagamento.processarReembolso(0.0);
            System.out.printf("[Regra de Negócio] Desistência fora do prazo (faltam apenas %d dias para a aula; exigido: mínimo de 3 dias). Retenção total. Devolução: R$ 0,00.%n",
                    diasAteAula);
            return 0.0;
        }
    }

    /** Cancela o agendamento com reembolso usando a data atual. */
    public double cancelarComReembolso() {
        return cancelarComReembolso(LocalDate.now());
    }

    /** Cancela agendamento preliminar se ja passou do limite de 5 dias uteis. */
    public boolean verificarExpiracaoPrazo(LocalDate dataReferencia) {
        if (this.fase == FaseAgendamento.PRELIMINAR) {
            long diasUteis = contarDiasUteis(dataReferencia, this.dataAula);
            if (diasUteis < 5) {
                this.fase = FaseAgendamento.CANCELADO;
                return true;
            }
        }
        return false;
    }

    /** Retorna o id do agendamento. */
    public Integer getId() {
        return this.id;
    }

    /** Retorna o cliente do agendamento. */
    public Client getCliente() {
        return this.cliente;
    }

    /** Atualiza o cliente do agendamento. */
    public void setCliente(Client cliente) {
        validarCliente(cliente);
        this.cliente = cliente;
    }

    /** Retorna a modalidade da aula. */
    public Modalidade getModalidade() {
        return this.modalidade;
    }

    /** Atualiza a modalidade e recalcula o valor se ainda for preliminar. */
    public void setModalidade(Modalidade modalidade) {
        validarModalidade(modalidade);
        this.modalidade = modalidade;
        if (this.fase == FaseAgendamento.PRELIMINAR && !this.pagamento.isPago()) {
            this.pagamento.setValorPago(modalidade.calcularValorFinal());
        }
    }

    /** Retorna a data de criacao do agendamento. */
    public LocalDate getDataAgendamento() {
        return this.dataAgendamento;
    }

    /** Retorna a data da aula. */
    public LocalDate getDataAula() {
        return this.dataAula;
    }

    /** Atualiza a data da aula. */
    public void setDataAula(LocalDate dataAula) {
        validarDatas(this.dataAgendamento, dataAula);
        this.dataAula = dataAula;
    }

    /** Retorna o nome do instrutor. */
    public String getInstrutor() {
        return this.instrutor;
    }

    /** Atualiza o nome do instrutor. */
    public void setInstrutor(String instrutor) {
        validarInstrutor(instrutor);
        this.instrutor = instrutor.trim();
    }

    /** Retorna o objeto Pagamento do agendamento. */
    public Pagamento getPagamento() {
        return this.pagamento;
    }

    /** Retorna o valor da aula. */
    public double getPreco() {
        return this.pagamento.getValorPago();
    }

    /** Retorna o numero do cartao. */
    public String getNumeroCartao() {
        return this.pagamento.getNumeroCartao();
    }

    /** Retorna o numero do cartao mascarado. */
    public String getNumeroCartaoMascarado() {
        return this.pagamento.getNumeroCartaoMascarado();
    }

    /** Atualiza o numero do cartao. */
    public void setNumeroCartao(String numeroCartao) {
        this.pagamento.setNumeroCartao(numeroCartao);
    }

    /** Retorna a fase atual do agendamento. */
    public FaseAgendamento getFase() {
        return this.fase;
    }

    /** Retorna o valor reembolsado. */
    public double getValorReembolsado() {
        return this.pagamento.getValorDevolvido();
    }

    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do agendamento deve ser maior que zero.");
        }
    }

    private void validarCliente(Client cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("O cliente do agendamento deve ser informado.");
        }
    }

    private void validarModalidade(Modalidade modalidade) {
        if (modalidade == null) {
            throw new IllegalArgumentException("A modalidade da aula deve ser informada.");
        }
    }

    private void validarDatas(LocalDate dataAgendamento, LocalDate dataAula) {
        if (dataAgendamento == null || dataAula == null) {
            throw new IllegalArgumentException("As datas de agendamento e da aula são obrigatórias.");
        }
        if (dataAula.isBefore(dataAgendamento)) {
            throw new IllegalArgumentException("A data da aula não pode ser anterior à data do agendamento.");
        }
    }

    private void validarInstrutor(String instrutor) {
        if (instrutor == null || instrutor.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do instrutor não pode ser vazio.");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Agendamento other = (Agendamento) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return String.format(
                "Agendamento [ID: %d | Cliente: %s | Fase: %s | Modalidade: %s | Instrutor: %s | Data da Aula: %s | %s]",
                this.id,
                this.cliente.getName(),
                this.fase.getDescricao(),
                this.modalidade.toString(),
                this.instrutor,
                this.dataAula.format(FORMATADOR_DATA),
                this.pagamento.toString()
        );
    }
}
