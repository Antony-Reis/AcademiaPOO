package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

// adaptado do padrao chain of responsibility
/**
 * Classe abstrata base da cadeia de validadores da catraca.
 */
public abstract class ValidadorAcesso {

    /** Proximo validador na cadeia. */
    protected ValidadorAcesso proximo;

    /** Construtor padrao. */
    public ValidadorAcesso() {
        super();
    }

    /** Define o proximo validador da cadeia e retorna ele mesmo. */
    public ValidadorAcesso setProximo(ValidadorAcesso proximo) {
        this.proximo = proximo;
        return proximo;
    }

    /** Retorna o proximo validador da cadeia. */
    public ValidadorAcesso getProximo() {
        return this.proximo;
    }

    /** Executa a validacao desse elo para o cliente. */
    public abstract boolean validar(Client cliente);

    /** Chama o proximo validador da cadeia se existir. */
    protected boolean validarProximo(Client cliente) {
        if (this.proximo == null) {
            return true;
        }
        boolean aux = this.proximo.validar(cliente);
        return aux;
    }

    @Override
    public String toString() {
        String proxNome;
        if (this.proximo != null) {
            proxNome = this.proximo.getClass().getSimpleName();
        } else {
            proxNome = "Fim da cadeia";
        }
        return getClass().getSimpleName() + " [Próximo: " + proxNome + "]";
    }
}
