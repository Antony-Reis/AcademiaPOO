package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

// base do decorator de login (adaptado do exemplo da aula)
/**
 * Classe base abstrata dos decorators de autenticacao.
 */
public abstract class AutenticadorDecorator implements Autenticador {

    /** Autenticador envolvido por este decorator. */
    // nao tem setter porque o decorator e montado no construtor
    private Autenticador autenticadorDecorado;

    /** Construtor recebendo o autenticador que sera decorado. */
    public AutenticadorDecorator(Autenticador autenticadorDecorado) {
        super();
        if (autenticadorDecorado == null) {
            throw new IllegalArgumentException("O autenticador decorado não pode ser nulo.");
        }
        this.autenticadorDecorado = autenticadorDecorado;
    }

    /** Retorna o autenticador interno. */
    protected Autenticador getAutenticadorDecorado() {
        return this.autenticadorDecorado;
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        return this.autenticadorDecorado.autenticar(login, senha);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + this.autenticadorDecorado.toString() + ")";
    }
}
