package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

/**
 * Interface componente (Component) do padrão Decorator para autenticação de usuários.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public interface Autenticador {

    /**
     * Realiza a autenticação a partir de login e senha.
     *
     * @param login O login informado.
     * @param senha A senha informada.
     * @return O {@link Usuario} autenticado, ou {@code null} se as credenciais forem inválidas ou bloqueadas.
     */
    Usuario autenticar(String login, String senha);
}
