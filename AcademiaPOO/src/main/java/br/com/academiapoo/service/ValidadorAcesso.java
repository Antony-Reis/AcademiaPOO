package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

/**
 * Classe abstrata base (Handler) do padrão Chain of Responsibility
 * para validação sequencial de acesso na catraca da academia.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public abstract class ValidadorAcesso {

    /** Próximo elo na cadeia de validadores. */
    protected ValidadorAcesso proximo;

    /**
     * Construtor padrão do validador de acesso.
     */
    public ValidadorAcesso() {
        super();
    }

    /**
     * Define o próximo validador na cadeia de responsabilidade.
     *
     * @param proximo O próximo validador a ser executado.
     * @return O próprio validador passado como parâmetro, permitindo encadeamento fluente.
     */
    public ValidadorAcesso setProximo(ValidadorAcesso proximo) {
        this.proximo = proximo;
        return proximo;
    }

    /**
     * Retorna o próximo validador configurado na cadeia.
     *
     * @return O próximo validador ou {@code null} se for o último elo.
     */
    public ValidadorAcesso getProximo() {
        return this.proximo;
    }

    /**
     * Executa a regra de validação específica deste elo para o cliente informado.
     *
     * @param cliente O cliente que está tentando passar pela catraca.
     * @return {@code true} se este elo e todos os próximos aprovarem; {@code false} caso contrário.
     */
    public abstract boolean validar(Client cliente);

    /**
     * Encaminha a validação para o próximo elo da cadeia, caso exista.
     *
     * @param cliente O cliente em validação.
     * @return {@code true} se não houver próximo elo ou se o próximo aprovar.
     */
    protected boolean validarProximo(Client cliente) {
        if (this.proximo == null) {
            return true;
        }
        return this.proximo.validar(cliente);
    }

    /**
     * Retorna uma representação textual do validador.
     *
     * @return Nome da classe do validador.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Próximo: "
                + (this.proximo != null ? this.proximo.getClass().getSimpleName() : "Fim da cadeia") + "]";
    }
}
