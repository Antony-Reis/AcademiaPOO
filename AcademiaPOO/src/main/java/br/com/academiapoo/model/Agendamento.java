package br.com.academiapoo.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa um Agendamento de aula em uma academia.
 * Esta classe integra o {@link Client}, a {@link Modalidade} (já vinculada a um {@link Plano}
 * pelo padrão Bridge), datas, instrutor e o {@link Pagamento} no cartão de crédito.
 *
 * Controla a máquina de estados entre Preliminar, Definitivo e Cancelado, aplicando as regras
 * de confirmação com até 5 dias de antecedência e retenção de 50% em caso de desistência
 * com até 3 dias antes da aula.
 *
 * @author Antony/Samuel
 * @version 2.0
 */
public class Agendamento {

    /** Formatador padrão para exibição das datas no padrão brasileiro. */
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** O identificador único do agendamento no sistema. */
    private Integer id;

    /** O cliente titular do agendamento. */
    private Client cliente;

    /** A modalidade da aula já vinculada ao respectivo plano (Padrão Bridge). */
    private Modalidade modalidade;

    /** A data de criação do agendamento. */
    private LocalDate dataAgendamento;

    /** A data em que a aula será realizada. */
    private LocalDate dataAula;

    /** O nome do instrutor responsável pela aula. */
    private String instrutor;

    /** Registro financeiro responsável pela cobrança e eventual reembolso. */
    private Pagamento pagamento;

    /** A fase atual do agendamento (Preliminar, Definitivo ou Cancelado). */
    private FaseAgendamento fase;

    /**
     * Construtor completo para inicializar um novo agendamento.
     * Utiliza {@code super()} para inicializar a classe base. O agendamento sempre inicia na fase Preliminar.
     *
     * @param id O identificador único do agendamento.
     * @param cliente O cliente que está realizando o agendamento.
     * @param modalidade A modalidade escolhida (já associada ao seu Plano).
     * @param dataAgendamento A data em que o agendamento foi feito.
     * @param dataAula A data da realização da aula.
     * @param instrutor O nome do instrutor.
     * @param numeroCartao O número do cartão para faturamento.
     */
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

    /**
     * Construtor de conveniência que assume a data atual como data do agendamento.
     *
     * @param id O identificador único do agendamento.
     * @param cliente O cliente que está realizando o agendamento.
     * @param modalidade A modalidade escolhida (já associada ao seu Plano).
     * @param dataAula A data da realização da aula.
     * @param instrutor O nome do instrutor.
     * @param numeroCartao O número do cartão para faturamento.
     */
    public Agendamento(Integer id, Client cliente, Modalidade modalidade, LocalDate dataAula,
                       String instrutor, String numeroCartao) {
        this(id, cliente, modalidade, LocalDate.now(), dataAula, instrutor, numeroCartao);
    }

    /**
     * Confirma o agendamento e realiza a cobrança no cartão.
     * Se o cliente não confirmar até 5 dias antes da data da aula, o agendamento é cancelado.
     *
     * @param dataConfirmacao A data em que a confirmação está sendo feita.
     * @return {@code true} se foi confirmado dentro do prazo, {@code false} se foi cancelado por expiração.
     */
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

        long diasAteAula = ChronoUnit.DAYS.between(dataConfirmacao, this.dataAula);

        if (diasAteAula < 5) {
            this.fase = FaseAgendamento.CANCELADO;
            System.out.printf("[Regra de Negócio] Agendamento #%d cancelado: prazo de confirmação expirado (faltam %d dias para a aula; exigido: mínimo de 5 dias).%n",
                    this.id, diasAteAula);
            return false;
        }

        this.fase = FaseAgendamento.DEFINITIVO;
        this.pagamento.processarPagamento();
        System.out.printf("[Regra de Negócio] Agendamento #%d confirmado como definitivo com %d dias de antecedência.%n",
                this.id, diasAteAula);
        return true;
    }

    /**
     * Confirma o agendamento utilizando a data atual do sistema.
     *
     * @return {@code true} se confirmado com sucesso, {@code false} se cancelado por expiração.
     */
    public boolean confirmarAgendamento() {
        return confirmarAgendamento(LocalDate.now());
    }

    /**
     * Cancela o agendamento por desistência do cliente e calcula o reembolso.
     * Se o agendamento estiver confirmado (com cartão debitado) e o cliente desistir com
     * até 3 dias antes da data (3 dias ou mais de antecedência), devolve 50% do valor retendo 50%.
     * Caso a desistência ocorra com menos de 3 dias de antecedência, não há devolução (retenção de 100%).
     *
     * @param dataDesistencia A data em que o cliente solicitou a desistência.
     * @return O valor em reais devolvido ao cliente.
     */
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

    /**
     * Cancela o agendamento com reembolso utilizando a data atual do sistema.
     *
     * @return O valor devolvido ao cliente.
     */
    public double cancelarComReembolso() {
        return cancelarComReembolso(LocalDate.now());
    }

    /**
     * Verifica e cancela preventivamente um agendamento preliminar que ultrapassou o limite de 5 dias.
     *
     * @param dataReferencia A data base para a verificação.
     * @return {@code true} se foi cancelado por prazo expirado, {@code false} caso contrário.
     */
    public boolean verificarExpiracaoPrazo(LocalDate dataReferencia) {
        if (this.fase == FaseAgendamento.PRELIMINAR) {
            long diasAteAula = ChronoUnit.DAYS.between(dataReferencia, this.dataAula);
            if (diasAteAula < 5) {
                this.fase = FaseAgendamento.CANCELADO;
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna o identificador do agendamento.
     *
     * @return O id do agendamento.
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * Retorna o cliente vinculado ao agendamento.
     *
     * @return O cliente.
     */
    public Client getCliente() {
        return this.cliente;
    }

    /**
     * Atualiza o cliente vinculado ao agendamento.
     *
     * @param cliente O novo cliente.
     */
    public void setCliente(Client cliente) {
        validarCliente(cliente);
        this.cliente = cliente;
    }

    /**
     * Retorna a modalidade da aula.
     *
     * @return A modalidade da aula.
     */
    public Modalidade getModalidade() {
        return this.modalidade;
    }

    /**
     * Atualiza a modalidade da aula e recalcula o valor a pagar caso o agendamento esteja preliminar.
     *
     * @param modalidade A nova modalidade.
     */
    public void setModalidade(Modalidade modalidade) {
        validarModalidade(modalidade);
        this.modalidade = modalidade;
        if (this.fase == FaseAgendamento.PRELIMINAR && !this.pagamento.isPago()) {
            this.pagamento.setValorPago(modalidade.calcularValorFinal());
        }
    }

    /**
     * Retorna a data de criação do agendamento.
     *
     * @return A data do agendamento.
     */
    public LocalDate getDataAgendamento() {
        return this.dataAgendamento;
    }

    /**
     * Retorna a data da realização da aula.
     *
     * @return A data da aula.
     */
    public LocalDate getDataAula() {
        return this.dataAula;
    }

    /**
     * Atualiza a data da realização da aula.
     *
     * @param dataAula A nova data da aula.
     */
    public void setDataAula(LocalDate dataAula) {
        validarDatas(this.dataAgendamento, dataAula);
        this.dataAula = dataAula;
    }

    /**
     * Retorna o nome do instrutor da aula.
     *
     * @return O nome do instrutor.
     */
    public String getInstrutor() {
        return this.instrutor;
    }

    /**
     * Atualiza o nome do instrutor da aula.
     *
     * @param instrutor O novo nome do instrutor.
     */
    public void setInstrutor(String instrutor) {
        validarInstrutor(instrutor);
        this.instrutor = instrutor.trim();
    }

    /**
     * Retorna o objeto de pagamento associado ao agendamento.
     *
     * @return O pagamento do agendamento.
     */
    public Pagamento getPagamento() {
        return this.pagamento;
    }

    /**
     * Retorna o preço calculado da aula.
     *
     * @return O valor da aula.
     */
    public double getPreco() {
        return this.pagamento.getValorPago();
    }

    /**
     * Retorna o número do cartão cadastrado.
     *
     * @return O número do cartão.
     */
    public String getNumeroCartao() {
        return this.pagamento.getNumeroCartao();
    }

    /**
     * Retorna o número do cartão ofuscado para preservar os dados do cliente.
     *
     * @return O número do cartão mascarado.
     */
    public String getNumeroCartaoMascarado() {
        return this.pagamento.getNumeroCartaoMascarado();
    }

    /**
     * Atualiza o número do cartão com validação.
     *
     * @param numeroCartao O novo número do cartão.
     */
    public void setNumeroCartao(String numeroCartao) {
        this.pagamento.setNumeroCartao(numeroCartao);
    }

    /**
     * Retorna a fase atual do agendamento.
     *
     * @return A fase do agendamento.
     */
    public FaseAgendamento getFase() {
        return this.fase;
    }

    /**
     * Retorna o valor reembolsado em caso de desistência.
     *
     * @return O valor do reembolso.
     */
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

    /**
     * Compara dois agendamentos pelo identificador id.
     *
     * @param obj O objeto a ser comparado.
     * @return {@code true} se tiverem o mesmo id, {@code false} caso contrário.
     */
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

    /**
     * Retorna o código hash baseado no identificador do agendamento.
     *
     * @return O valor hash do id.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    /**
     * Retorna uma representação em formato de texto com os dados do agendamento.
     *
     * @return Uma String contendo os detalhes do agendamento.
     */
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
