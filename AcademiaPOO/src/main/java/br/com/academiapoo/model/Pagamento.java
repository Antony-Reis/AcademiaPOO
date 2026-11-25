package br.com.academiapoo.model;

/**
 * Registra o pagamento no cartao e o reembolso de um agendamento.
 */
public class Pagamento {

    /** Numero do cartao (somente digitos). */
    private String numeroCartao;

    /** Valor cobrado pela aula. */
    private double valorPago;

    /** Valor devolvido em caso de cancelamento. */
    // nao tem setter porque so e calculado no processarReembolso
    private double valorDevolvido;

    /** Indica se o pagamento ja foi debitado. */
    // nao tem setter porque o status so muda quando chama processarPagamento
    private boolean pago;

    /** Construtor do pagamento com cartao e valor. */
    public Pagamento(String numeroCartao, double valorPago) {
        super();
        validarNumeroCartao(numeroCartao);
        validarValor(valorPago);

        this.numeroCartao = normalizarCartao(numeroCartao);
        this.valorPago = valorPago;
        this.valorDevolvido = 0.0;
        this.pago = false;
    }

    /** Marca o pagamento como realizado no cartao. */
    public void processarPagamento() {
        this.pago = true;
        System.out.printf("[Financeiro] Débito de R$ %.2f realizado no cartão %s.%n",
                this.valorPago, getNumeroCartaoMascarado());
    }

    /** Calcula e registra o valor devolvido conforme o percentual. */
    public double processarReembolso(double percentual) {
        if (!this.pago || percentual <= 0.0) {
            this.valorDevolvido = 0.0;
            return 0.0;
        }
        this.valorDevolvido = this.valorPago * percentual;
        return this.valorDevolvido;
    }

    /** Retorna o numero do cartao. */
    public String getNumeroCartao() {
        return this.numeroCartao;
    }

    /** Atualiza o numero do cartao. */
    public void setNumeroCartao(String numeroCartao) {
        validarNumeroCartao(numeroCartao);
        this.numeroCartao = normalizarCartao(numeroCartao);
    }

    /** Retorna o cartao mascarado exibindo so os 4 ultimos digitos. */
    public String getNumeroCartaoMascarado() {
        if (this.numeroCartao == null || this.numeroCartao.length() < 4) {
            return "•••• •••• •••• ••••";
        }
        return "•••• •••• •••• " + this.numeroCartao.substring(this.numeroCartao.length() - 4);
    }

    /** Retorna o valor do pagamento. */
    public double getValorPago() {
        return this.valorPago;
    }

    /** Atualiza o valor enquanto ainda nao foi pago. */
    public void setValorPago(double valorPago) {
        if (this.pago) {
            throw new IllegalStateException("Não é possível alterar o valor de um pagamento já efetivado.");
        }
        validarValor(valorPago);
        this.valorPago = valorPago;
    }

    /** Retorna o valor reembolsado. */
    public double getValorDevolvido() {
        return this.valorDevolvido;
    }

    /** Retorna true se o pagamento ja foi feito. */
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

    @Override
    public String toString() {
        String statusPago;
        if (this.pago) {
            statusPago = "Sim";
        } else {
            statusPago = "Não";
        }
        return String.format(
                "Pagamento [Cartão: %s | Pago: %s | Valor: R$ %.2f | Devolvido: R$ %.2f]",
                getNumeroCartaoMascarado(),
                statusPago,
                this.valorPago,
                this.valorDevolvido
        );
    }
}
