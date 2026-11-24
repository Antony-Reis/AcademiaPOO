package br.com.academiapoo.model;

/**
 * Subclasse de {@link Usuario} que representa um administrador da academia.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Administrador extends Usuario {

    /**
     * Construtor do administrador.
     *
     * @param nome Nome do administrador.
     * @param login Login de acesso.
     * @param senha Senha de acesso.
     */
    public Administrador(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    /**
     * Retorna uma representação textual do administrador.
     *
     * @return String descrevendo o administrador.
     */
    @Override
    public String toString() {
        return "Administrador -> " + super.toString();
    }
}
