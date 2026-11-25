package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Autenticador simples que confere login e senha na lista de usuarios.
 */
public class AutenticadorBasico implements Autenticador {

    /** Lista interna de usuarios cadastrados. */
    // nao tem setUsuarios pra lista so ser alterada pelo metodo adicionarUsuario
    private List<Usuario> usuarios;

    /** Construtor padrao. */
    public AutenticadorBasico() {
        super();
        this.usuarios = new ArrayList<>();
    }

    /** Construtor recebendo uma lista inicial de usuarios. */
    public AutenticadorBasico(List<Usuario> usuarios) {
        super();
        if (usuarios != null) {
            this.usuarios = new ArrayList<>(usuarios);
        } else {
            this.usuarios = new ArrayList<>();
        }
    }

    /** Adiciona um usuario na lista de login. */
    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null) {
            this.usuarios.add(usuario);
        }
    }

    /** Retorna a lista de usuarios (somente leitura). */
    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return null;
        }
        // percorre a lista na mao mesmo pra achar o usuario
        for (int i = 0; i < this.usuarios.size(); i++) {
            Usuario temp = this.usuarios.get(i);
            if (temp.getLogin().equals(login) && temp.verificarSenha(senha)) {
                return temp;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("AutenticadorBasico [Usuários cadastrados: %d]", this.usuarios.size());
    }
}
