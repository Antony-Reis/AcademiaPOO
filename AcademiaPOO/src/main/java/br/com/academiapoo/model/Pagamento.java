package br.com.academiapoo.model;

/**
 * Representa o pagamento vinculado a um agendamento de aula na academia.
 * Responsável pelo registro do cartão de crédito, processamento do débito
 * e cálculo de reembolso em caso de cancelamento.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Pagamento {

    /** Número do cartão de crédito normalizado (apenas dígitos). */
    private String numeroCartao;

    /** Valor total cobrado pela aula. */
    private double valorPago;

    /** Valor reembolsado ao cliente em caso de desistência. */
    private double valorDevolvido;

    /** Indica se o débito já foi efetivado no cartão. */
    private boolean pago;

    /**
     * Construtor do pagamento com validação de cartão e valor.
     *
     * @param numeroCartao Número do cartão de crédito.
     * @param valorPago Valor a ser cobrado.
     */
    public Pagamento(String numeroCartao, double valorPago) {
        super();
        validarNumeroCartao(numeroCartao);
        validarValor(valorPago);

        this.numeroCartao = normalizarCartao(numeroCartao);
        this.valorPago = valorPago;
        this.valorDevolvido = 0.0;
        this.pago = false;
    }

    /**
     * Efetiva a cobrança no cartão de crédito do cliente.
     */
    public void processarPagamento() {
        this.pago = true;
        System.out.printf("[Financeiro] Débito de R$ %.2f realizado no cartão %s.%n",
                this.valorPago, getNumeroCartaoMascarado());
    }

    /**
     * Calcula e registra o reembolso de acordo com o percentual informado.
     *
     * @param percentual Fração do valor pago a ser devolvida (ex: 0.50 para 50%).
     * @return O valor em reais devolvido ao cliente.
     */
    public double processarReembolso(double percentual) {
        if (!this.pago || percentual <= 0.0) {
            this.valorDevolvido = 0.0;
            return 0.0;
        }
        this.valorDevolvido = this.valorPago * percentual;
        return this.valorDevolvido;
    }

    /**
     * Retorna o número do cartão armazenado.
     *
     * @return O número do cartão.
     */
    public String getNumeroCartao() {
        return this.numeroCartao;
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
     * Retorna o número do cartão mascarado exibindo apenas os últimos 4 dígitos.
     *
     * @return O número do cartão mascarado.
     */
    public String getNumeroCartaoMascarado() {
        if (this.numeroCartao == null || this.numeroCartao.length() < 4) {
            return "•••• •••• •••• ••••";
        }
        return "•••• •••• •••• " + this.numeroCartao.substring(this.numeroCartao.length() - 4);
    }

    /**
     * Retorna o valor do pagamento.
     *
     * @return O valor pago.
     */
    public double getValorPago() {
        return this.valorPago;
    }

    /**
     * Atualiza o valor do pagamento caso ainda não tenha sido efetivado.
     *
     * @param valorPago O novo valor a pagar.
     */
    public void setValorPago(double valorPago) {
        if (this.pago) {
            throw new IllegalStateException("Não é possível alterar o valor de um pagamento já efetivado.");
        }
        validarValor(valorPago);
        this.valorPago = valorPago;
    }

    /**
     * Retorna o valor devolvido em caso de cancelamento.
     *
     * @return O valor reembolsado.
     */
    public double getValorDevolvido() {
        return this.valorDevolvido;
    }

    /**
     * Indica se o pagamento foi efetivado.
     *
     * @return {@code true} se pago, {@code false} caso contrário.
     */
    public boolean isPago() {
        return this.pago;
    }

    private void validarNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.trim().isEmpty()) {
            throw new IllegalArgumentException("O número do cartão é obrigatório.");
        }
        String digitos = numeroCartao.replaceAll("\\D", "");
        if (digitos.length() < 13 || digitos.length() > 19) {
            throw new IllegalArgumentException("Número de cartão de crédito inválido.");
        }
    }

    private void validarValor(double valor) {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0.0) {
            throw new IllegalArgumentException("Valor de pagamento inválido.");
        }
    }

    private String normalizarCartao(String numeroCartao) {
        return numeroCartao.replaceAll("\\D", "");
    }

    /**
     * Retorna uma representação textual dos dados financeiros do pagamento.
     *
     * @return String contendo cartão mascarado, status, valor pago e reembolso.
     */
    @Override
    public String toString() {
        return String.format(
                "Pagamento [Cartão: %s | Pago: %s | Valor: R$ %.2f | Devolvido: R$ %.2f]",
                getNumeroCartaoMascarado(),
                this.pago ? "Sim" : "Não",
                this.valorPago,
                this.valorDevolvido
        );
    }
}
