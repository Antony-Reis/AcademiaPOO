package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

/**
 * Classe abstrata base (Decorator) para adicionar camadas dinâmicas de segurança
 * e auditoria sobre um {@link Autenticador}.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public abstract class AutenticadorDecorator implements Autenticador {

    /** Instância de autenticador envolvida por este decorador. */
    private Autenticador autenticadorDecorado;

    /**
     * Construtor do decorador recebendo o autenticador a ser envolvido.
     *
     * @param autenticadorDecorado O componente autenticador base ou outro decorador.
     */
    public AutenticadorDecorator(Autenticador autenticadorDecorado) {
        super();
        if (autenticadorDecorado == null) {
            throw new IllegalArgumentException("O autenticador decorado não pode ser nulo.");
        }
        this.autenticadorDecorado = autenticadorDecorado;
    }

    /**
     * Retorna o autenticador envolvido por este decorador.
     *
     * @return O autenticador decorado.
     */
    protected Autenticador getAutenticadorDecorado() {
        return this.autenticadorDecorado;
    }

    /**
     * Delega a autenticação para o autenticador decorado.
     *
     * @param login O login informado.
     * @param senha A senha informada.
     * @return O resultado da autenticação do componente envolvido.
     */
    @Override
    public Usuario autenticar(String login, String senha) {
        return this.autenticadorDecorado.autenticar(login, senha);
    }

    /**
     * Retorna uma representação textual do decorador e do componente envolvido.
     *
     * @return String descrevendo a cadeia de decoradores.
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + this.autenticadorDecorado.toString() + ")";
    }
}
