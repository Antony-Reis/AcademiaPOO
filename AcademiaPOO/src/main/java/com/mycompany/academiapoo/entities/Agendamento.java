package com.mycompany.academiapoo.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * Representa um Agendamento de aula em uma academia.
 * Esta classe armazena as informações da aula escolhida (Spinning, Musculação, Fit Dance ou Pilates),
 * datas, instrutor, preço cobrado e o número do cartão para faturamento.
 * Também controla o fluxo entre as fases preliminar, definitivo e cancelado, aplicando as regras
 * de confirmação com 5 dias de antecedência e retenção de 50% em caso de desistência com até 3 dias.
 * 
 * @author Antony/Samuel
 * @version 1.0
 */
public class Agendamento {

    /** Formatador padrão para exibição das datas no padrão brasileiro. */
    private static final DateTimeFormatter FORMATADOR_DATA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /** O identificador único do agendamento no sistema. */
    private Integer id;

    /** A modalidade ou sala da aula (Spinning, Musculação, Fit Dance ou Pilates). */
    private GymRoom modalidade;

    /** A data de criação do agendamento. */
    private LocalDate dataAgendamento;

    /** A data em que a aula será realizada. */
    private LocalDate dataAula;

    /** O nome do instrutor responsável pela aula. */
    private String instrutor;

    /** O preço cobrado pela aula agendada. */
    private double preco;

    /** O número do cartão de crédito cadastrado para cobrança. */
    private String numeroCartao;

    /** A fase atual do agendamento (Preliminar, Definitivo ou Cancelado). */
    private FaseAgendamento fase;

    /** O valor devolvido ao cliente em caso de desistência com direito a reembolso. */
    private double valorReembolsado;

    /**
     * Construtor completo para inicializar um novo agendamento.
     * Utiliza super() para inicializar a classe base. O agendamento sempre inicia na fase Preliminar.
     *
     * @param id O identificador único do agendamento.
     * @param modalidade A modalidade da aula escolhida.
     * @param dataAgendamento A data em que o agendamento foi feito.
     * @param dataAula A data da realização da aula.
     * @param instrutor O nome do instrutor.
     * @param preco O valor da aula.
     * @param numeroCartao O número do cartão para faturamento.
     */
    public Agendamento(Integer id, GymRoom modalidade, LocalDate dataAgendamento, LocalDate dataAula,
                       String instrutor, double preco, String numeroCartao) {
        super();
        validarId(id);
        validarModalidade(modalidade);
        validarDatas(dataAgendamento, dataAula);
        validarInstrutor(instrutor);
        validarPreco(preco);
        validarNumeroCartao(numeroCartao);

        this.id = id;
        this.modalidade = modalidade;
        this.dataAgendamento = dataAgendamento;
        this.dataAula = dataAula;
        this.instrutor = instrutor.trim();
        this.preco = preco;
        this.numeroCartao = normalizarCartao(numeroCartao);
        this.fase = FaseAgendamento.PRELIMINAR;
        this.valorReembolsado = 0.0;
    }

    /**
     * Confirma o agendamento e realiza a cobrança no cartão.
     * Se o cliente não confirmar até 5 dias antes da data da aula, o agendamento é cancelado.
     *
     * @param dataConfirmacao A data em que a confirmação está sendo feita.
     * @return true se foi confirmado dentro do prazo, false se foi cancelado por expiração.
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
        debitarCartao();
        System.out.printf("[Regra de Negócio] Agendamento #%d confirmado como definitivo com %d dias de antecedência.%n",
                this.id, diasAteAula);
        return true;
    }

    /**
     * Confirma o agendamento utilizando a data atual do sistema.
     *
     * @return true se confirmado com sucesso, false se cancelado por expiração.
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

        // Se o agendamento ainda estava preliminar, cancela sem cobranças
        if (this.fase == FaseAgendamento.PRELIMINAR) {
            this.fase = FaseAgendamento.CANCELADO;
            this.valorReembolsado = 0.0;
            System.out.printf("[Regra de Negócio] Agendamento preliminar #%d cancelado sem cobranças.%n", this.id);
            return 0.0;
        }

        long diasAteAula = ChronoUnit.DAYS.between(dataDesistencia, this.dataAula);
        this.fase = FaseAgendamento.CANCELADO;

        if (diasAteAula >= 3) {
            // Desistência com pelo menos 3 dias de antecedência: devolve 50% e retém 50%
            this.valorReembolsado = this.preco * 0.50;
            System.out.printf("[Regra de Negócio] Desistência aceita com %d dias de antecedência. Devolução de 50%% (R$ %.2f). Retenção de 50%% (R$ %.2f).%n",
                    diasAteAula, this.valorReembolsado, (this.preco - this.valorReembolsado));
        } else {
            // Menos de 3 dias de antecedência: sem reembolso (retenção total)
            this.valorReembolsado = 0.0;
            System.out.printf("[Regra de Negócio] Desistência fora do prazo (faltam apenas %d dias para a aula; exigido: mínimo de 3 dias). Retenção total. Devolução: R$ 0,00.%n",
                    diasAteAula);
        }

        return this.valorReembolsado;
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
     * @return true se foi cancelado por prazo expirado, false caso contrário.
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
     * Simula o débito no cartão de crédito do cliente.
     */
    private void debitarCartao() {
        System.out.printf("[Financeiro] Débito de R$ %.2f realizado no cartão %s.%n",
                this.preco, getNumeroCartaoMascarado());
    }

    /**
     * Retorna o identificador do agendamento.
     *
     * @return O id do agendamento.
     */
    public Integer getId() {
        return this.id;
    }

    /*
     * Justificativa da ausência do método setId:
     * O id representa a identidade única do agendamento e não deve ser
     * alterado após a criação para garantir a rastreabilidade no sistema.
     */

    /**
     * Retorna a modalidade da aula.
     *
     * @return A modalidade da aula.
     */
    public GymRoom getModalidade() {
        return this.modalidade;
    }

    /**
     * Atualiza a modalidade da aula.
     *
     * @param modalidade A nova modalidade.
     */
    public void setModalidade(GymRoom modalidade) {
        validarModalidade(modalidade);
        this.modalidade = modalidade;
    }

    /**
     * Retorna a data de criação do agendamento.
     *
     * @return A data do agendamento.
     */
    public LocalDate getDataAgendamento() {
        return this.dataAgendamento;
    }

    /*
     * Justificativa da ausência do método setDataAgendamento:
     * A data em que o agendamento foi registrado é um registro histórico imutável.
     */

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
     * Retorna o preço cobrado pela aula.
     *
     * @return O preço da aula.
     */
    public double getPreco() {
        return this.preco;
    }

    /**
     * Atualiza o preço cobrado pela aula.
     *
     * @param preco O novo preço da aula.
     */
    public void setPreco(double preco) {
        validarPreco(preco);
        this.preco = preco;
    }

    /**
     * Retorna o número do cartão cadastrado.
     *
     * @return O número do cartão.
     */
    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    /**
     * Retorna o número do cartão ofuscado para preservar os dados do cliente.
     *
     * @return O número do cartão mascarado mostrando apenas os 4 últimos dígitos.
     */
    public String getNumeroCartaoMascarado() {
        if (this.numeroCartao == null || this.numeroCartao.length() < 4) {
            return "•••• •••• •••• ••••";
        }
        return "•••• •••• •••• " + this.numeroCartao.substring(this.numeroCartao.length() - 4);
    }

    /**
     * Atualiza o número do cartão com validação.
     *
     * @param numeroCartao O novo número do cartão.
     */
    public void setNumeroCartao(String numeroCartao) {
        validarNumeroCartao(numeroCartao);
        this.numeroCartao = normalizarCartao(numeroCartao);
    }

    /**
     * Retorna a fase atual do agendamento.
     *
     * @return A fase do agendamento.
     */
    public FaseAgendamento getFase() {
        return this.fase;
    }

    /*
     * Justificativa da ausência do método setFase:
     * A fase do agendamento não pode ser modificada livremente. A transição
     * entre Preliminar, Definitivo e Cancelado é controlada de forma estrita
     * pelos métodos de negócio confirmarAgendamento e cancelarComReembolso.
     */

    /**
     * Retorna o valor reembolsado em caso de desistência.
     *
     * @return O valor do reembolso.
     */
    public double getValorReembolsado() {
        return this.valorReembolsado;
    }

    /**
     * Valida o identificador do agendamento.
     *
     * @param id O id a ser validado.
     */
    private void validarId(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("O ID do agendamento deve ser maior que zero.");
        }
    }

    /**
     * Valida a modalidade informada.
     *
     * @param modalidade A modalidade da aula.
     */
    private void validarModalidade(GymRoom modalidade) {
        if (modalidade == null) {
            throw new IllegalArgumentException("A modalidade da aula deve ser informada (Spinning, Musculação, Fit Dance ou Pilates).");
        }
    }

    /**
     * Valida as datas de agendamento e da aula.
     *
     * @param dataAgendamento A data do agendamento.
     * @param dataAula A data da aula.
     */
    private void validarDatas(LocalDate dataAgendamento, LocalDate dataAula) {
        if (dataAgendamento == null || dataAula == null) {
            throw new IllegalArgumentException("As datas de agendamento e da aula são obrigatórias.");
        }
        if (dataAula.isBefore(dataAgendamento)) {
            throw new IllegalArgumentException("A data da aula não pode ser anterior à data do agendamento.");
        }
    }

    /**
     * Valida o nome do instrutor.
     *
     * @param instrutor O nome do instrutor.
     */
    private void validarInstrutor(String instrutor) {
        if (instrutor == null || instrutor.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do instrutor não pode ser vazio.");
        }
    }

    /**
     * Valida o preço informado.
     *
     * @param preco O preço a ser validado.
     */
    private void validarPreco(double preco) {
        if (Double.isNaN(preco) || Double.isInfinite(preco) || preco < 0.0) {
            throw new IllegalArgumentException("Preço inválido para o agendamento.");
        }
    }

    /**
     * Valida o número do cartão de crédito.
     *
     * @param numeroCartao O número do cartão.
     */
    private void validarNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.trim().isEmpty()) {
            throw new IllegalArgumentException("O número do cartão é obrigatório.");
        }
        String digitos = numeroCartao.replaceAll("\\D", "");
        if (digitos.length() < 13 || digitos.length() > 19) {
            throw new IllegalArgumentException("Número de cartão de crédito inválido.");
        }
    }

    /**
     * Remove caracteres não numéricos do cartão.
     *
     * @param numeroCartao O número original do cartão.
     * @return O número contendo apenas dígitos.
     */
    private String normalizarCartao(String numeroCartao) {
        return numeroCartao.replaceAll("\\D", "");
    }

    /**
     * Compara dois agendamentos pelo identificador id.
     *
     * @param obj O objeto a ser comparado.
     * @return true se tiverem o mesmo id, false caso contrário.
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
                "Agendamento [ID: %d | Fase: %s | Aula: %s (%s) | Instrutor: %s | Data da Aula: %s | Preço: R$ %.2f | Cartão: %s | Reembolso: R$ %.2f]",
                this.id,
                this.fase.getDescricao(),
                this.modalidade.name(),
                this.modalidade.getNome(),
                this.instrutor,
                this.dataAula.format(FORMATADOR_DATA),
                this.preco,
                getNumeroCartaoMascarado(),
                this.valorReembolsado
        );
    }
}
