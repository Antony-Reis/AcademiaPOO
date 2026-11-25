package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

/**
 * Interface base do padrao Decorator para autenticacao de usuarios.
 */
public interface Autenticador {

    /** Valida login e senha e devolve o usuario ou null se falhar. */
    Usuario autenticar(String login, String senha);
}
