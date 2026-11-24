package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementação concreta base (ConcreteComponent) de {@link Autenticador}.
 * Realiza exclusivamente a validação simples de login e senha na lista de usuários cadastrados.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class AutenticadorBasico implements Autenticador {

    /** Lista de usuários cadastrados para autenticação. */
    private List<Usuario> usuarios;

    /**
     * Construtor padrão sem usuários iniciais.
     */
    public AutenticadorBasico() {
        super();
        this.usuarios = new ArrayList<>();
    }

    /**
     * Construtor recebendo a lista de usuários cadastrados.
     *
     * @param usuarios Lista de usuários.
     */
    public AutenticadorBasico(List<Usuario> usuarios) {
        super();
        this.usuarios = (usuarios != null) ? new ArrayList<>(usuarios) : new ArrayList<>();
    }

    /**
     * Adiciona um usuário à lista de credenciais válidas.
     *
     * @param usuario O usuário a ser cadastrado.
     */
    public void adicionarUsuario(Usuario usuario) {
        if (usuario != null) {
            this.usuarios.add(usuario);
        }
    }

    /**
     * Retorna a lista imutável de usuários cadastrados.
     *
     * @return Lista de usuários.
     */
    public List<Usuario> getUsuarios() {
        return Collections.unmodifiableList(this.usuarios);
    }

    /**
     * Valida login e senha percorrendo os usuários cadastrados.
     *
     * @param login O login informado.
     * @param senha A senha informada.
     * @return O {@link Usuario} correspondente ou {@code null} caso inválido.
     */
    @Override
    public Usuario autenticar(String login, String senha) {
        if (login == null || senha == null) {
            return null;
        }
        for (Usuario usuario : this.usuarios) {
            if (usuario.getLogin().equals(login) && usuario.verificarSenha(senha)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     * Retorna uma representação textual do autenticador básico.
     *
     * @return String com a quantidade de usuários cadastrados.
     */
    @Override
    public String toString() {
        return String.format("AutenticadorBasico [Usuários cadastrados: %d]", this.usuarios.size());
    }
}
