package br.com.academiapoo.model;

/**
 * Superclasse dos usuarios que fazem login no sistema (Colaborador e Administrador).
 */
public class Usuario {

    /** Nome completo do usuario. */
    private String nome;

    /** Login de acesso. */
    private String login;

    /** Senha de acesso. */
    private String senha;

    /** Construtor completo do usuario. */
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

    /** Retorna o nome do usuario. */
    public String getNome() {
        return this.nome;
    }

    /** Atualiza o nome do usuario. */
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do usuário não pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    /** Retorna o login do usuario. */
    public String getLogin() {
        return this.login;
    }

    /** Atualiza o login do usuario. */
    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("O login do usuário não pode ser vazio.");
        }
        this.login = login.trim();
    }

    /** Retorna a senha do usuario. */
    public String getSenha() {
        return this.senha;
    }

    /** Atualiza a senha do usuario. */
    public void setSenha(String senha) {
        if (senha == null || senha.isEmpty()) {
            throw new IllegalArgumentException("A senha do usuário não pode ser vazia.");
        }
        this.senha = senha;
    }

    /** Confere se a senha informada bate com a cadastrada. */
    public boolean verificarSenha(String senhaInformada) {
        return this.senha.equals(senhaInformada);
    }

    /** Altera a senha se a senha atual estiver correta. */
    public boolean alterarSenha(String senhaAtual, String novaSenha) {
        if (novaSenha == null || novaSenha.isEmpty()) {
            throw new IllegalArgumentException("A nova senha não pode ser vazia.");
        }
        if (!verificarSenha(senhaAtual)) {
            return false;
        }
        this.senha = novaSenha;
        return true;
    }

    @Override
    public String toString() {
        return String.format("Usuario [Nome: %s | Login: %s]", this.nome, this.login);
    }
}
