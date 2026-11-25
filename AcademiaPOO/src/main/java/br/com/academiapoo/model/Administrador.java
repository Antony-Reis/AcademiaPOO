package br.com.academiapoo.model;

/**
 * Representa um administrador da academia com acesso total ao sistema.
 */
public class Administrador extends Usuario {

    /** Construtor do administrador. */
    public Administrador(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    @Override
    public String toString() {
        return "Administrador -> " + super.toString();
    }
}
