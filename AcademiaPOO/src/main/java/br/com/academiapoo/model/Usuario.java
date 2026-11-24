package br.com.academiapoo.model;

/**
 * Superclasse que representa um usuário autenticável no sistema da academia
 * (como {@link Colaborador} e {@link Administrador}).
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Usuario {

    /** Nome completo do usuário. */
    private String nome;

    /** Identificador de login do usuário. */
    private String login;

    /** Senha de acesso do usuário. */
    private String senha;

    /**
     * Construtor completo do usuário.
     *
     * @param nome O nome do usuário.
     * @param login O login de acesso.
     * @param senha A senha de acesso.
     */
    public Usuario(String nome, String login, String senha) {
        super();
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário é obrigatório.");
        }
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("O login do usuário é obrigatório.");
        }
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário é obrigatória.");
        }
        this.nome = nome.trim();
        this.login = login.trim();
        this.senha = senha;
    }

    /**
     * Retorna o nome do usuário.
     *
     * @return O nome do usuário.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Atualiza o nome do usuário.
     *
     * @param nome O novo nome.
     */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /**
     * Retorna o login do usuário.
     *
     * @return O login.
     */
    public String getLogin() {
        return this.login;
    }

    /**
     * Atualiza o login do usuário.
     *
     * @param login O novo login.
     */
    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("O login do usuário não pode ser vazio.");
        }
        this.login = login.trim();
    }

    /**
     * Retorna a senha do usuário.
     *
     * @return A senha armazenada.
     */
    public String getSenha() {
        return this.senha;
    }

    /**
     * Atualiza a senha do usuário.
     *
     * @param senha A nova senha.
     */
    public void setSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário não pode ser vazia.");
        }
        this.senha = senha;
    }

    /**
     * Verifica se a senha informada corresponde à senha cadastrada.
     *
     * @param senhaInformada A senha digitada na tentativa de login.
     * @return {@code true} se coincidir, {@code false} caso contrário.
     */
    public boolean verificarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    /**
     * Retorna uma representação textual do usuário (omitindo a senha por segurança).
     *
     * @return String com nome e login do usuário.
     */
    @Override
    public String toString() {
        return String.format("Usuario [Nome: %s | Login: %s]", this.nome, this.login);
    }
}
