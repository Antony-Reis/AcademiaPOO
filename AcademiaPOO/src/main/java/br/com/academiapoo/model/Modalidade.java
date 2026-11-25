package br.com.academiapoo.model;

// ponte pro Plano (padrao Bridge)
/**
 * Classe abstrata das modalidades de aula ligadas a um Plano (padrao Bridge).
 */
public abstract class Modalidade {

    /** Plano de cobranca associado. */
    protected Plano plano;

    /** Nome da modalidade. */
    private String nome;

    /** Valor base da aula. */
    private double valorBase;

    /** Construtor da modalidade com nome, valor base e plano. */
    public Modalidade(String nome, double valorBase, Plano plano) {
        super();
        validarNome(nome);
        validarValorBase(valorBase);
        validarPlano(plano);

        this.nome = nome.trim();
        this.valorBase = valorBase;
        this.plano = plano;
    }

    /** Calcula o valor final delegando para o plano. */
    public double calcularValorFinal() {
        return this.plano.calcularValor(this.valorBase);
    }

    /** Retorna o nome da modalidade. */
    public String getNome() {
        return this.nome;
    }

    /** Atualiza o nome da modalidade. */
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    /** Retorna o valor base. */
    public double getValorBase() {
        return this.valorBase;
    }

    /** Atualiza o valor base. */
    public void setValorBase(double valorBase) {
        validarValorBase(valorBase);
        this.valorBase = valorBase;
    }

    /** Retorna o plano vinculado. */
    public Plano getPlano() {
        return this.plano;
    }

    /** Troca o plano vinculado. */
    public void setPlano(Plano plano) {
        validarPlano(plano);
        this.plano = plano;
    }

    private void validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da modalidade não pode ser vazio.");
        }
    }

    private void validarValorBase(double valorBase) {
        if (Double.isNaN(valorBase) || Double.isInfinite(valorBase) || valorBase < 0.0) {
            throw new IllegalArgumentException("O valor base da modalidade deve ser maior ou igual a zero.");
        }
    }

    private void validarPlano(Plano plano) {
        if (plano == null) {
            throw new IllegalArgumentException("O plano da modalidade deve ser informado.");
        }
    }

    @Override
    public String toString() {
        return String.format(
                "%s [Plano: %s | Valor Base: R$ %.2f | Valor Final: R$ %.2f]",
                this.nome,
                this.plano.getTipoPlano(),
                this.valorBase,
                calcularValorFinal()
        );
    }
}
