package br.com.academiapoo.model;

/**
 * Representa um colaborador comum da academia (sem acesso a despesas e balanco).
 */
public class Colaborador extends Usuario {

    /** Construtor do colaborador. */
    public Colaborador(String nome, String login, String senha) {
        super(nome, login, senha);
    }

    @Override
    public String toString() {
        return "Colaborador -> " + super.toString();
    }
}
