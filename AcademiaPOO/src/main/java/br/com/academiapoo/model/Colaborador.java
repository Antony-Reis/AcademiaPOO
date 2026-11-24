package br.com.academiapoo.model;

/**
 * Subclasse de {@link Usuario} que representa um colaborador da academia.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Colaborador extends Usuario {

    /**
     * Construtor do colaborador.
     *
     * @param nome Nome do colaborador.
     * @param login Login de acesso.
     * @param senha Senha de acesso.
     */
    public Colaborador(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    /**
     * Retorna uma representação textual do colaborador.
     *
     * @return String descrevendo o colaborador.
     */
    @Override
    public String toString() {
        return "Colaborador -> " + super.toString();
    }
}
