package br.com.academiapoo.model;

/**
 * Classe abstrata que representa a Abstração (Abstraction) do padrão Bridge
 * para as modalidades de aula da academia.
 *
 * Mantém uma referência protegida para um objeto {@link Plano} via composição
 * e delega a ele o cálculo do valor final da aula.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public abstract class Modalidade {

    /** Plano de cobrança associado à modalidade (ponte/Bridge). */
    protected Plano plano;

    /** Nome identificador da modalidade. */
    private String nome;

    /** Valor base da aula antes da aplicação das regras do plano. */
    private double valorBase;

    /**
     * Construtor da modalidade recebendo nome, valor base e o plano vinculado.
     *
     * @param nome O nome da modalidade.
     * @param valorBase O valor base da modalidade.
     * @param plano O plano de cobrança aplicado.
     */
    public Modalidade(String nome, double valorBase, Plano plano) {
        super();
        validarNome(nome);
        validarValorBase(valorBase);
        validarPlano(plano);

        this.nome = nome.trim();
        this.valorBase = valorBase;
        this.plano = plano;
    }

    /**
     * Delega ao {@link Plano} o cálculo do valor final da modalidade.
     *
     * @return O valor final calculado pelo plano.
     */
    public double calcularValorFinal() {
        return this.plano.calcularValor(this.valorBase);
    }

    /**
     * Retorna o nome da modalidade.
     *
     * @return O nome da modalidade.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Atualiza o nome da modalidade.
     *
     * @param nome O novo nome da modalidade.
     */
    public void setNome(String nome) {
        validarNome(nome);
        this.nome = nome.trim();
    }

    /**
     * Retorna o valor base da modalidade.
     *
     * @return O valor base.
     */
    public double getValorBase() {
        return this.valorBase;
    }

    /**
     * Atualiza o valor base da modalidade.
     *
     * @param valorBase O novo valor base.
     */
    public void setValorBase(double valorBase) {
        validarValorBase(valorBase);
        this.valorBase = valorBase;
    }

    /**
     * Retorna o plano vinculado à modalidade.
     *
     * @return O objeto {@link Plano}.
     */
    public Plano getPlano() {
        return this.plano;
    }

    /**
     * Altera o plano vinculado à modalidade.
     *
     * @param plano O novo plano de cobrança.
     */
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

    /**
     * Retorna uma representação textual da modalidade, incluindo seu plano e valor final.
     *
     * @return String com os dados da modalidade.
     */
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
