package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Decorador concreto que bloqueia o acesso de um login após 3 tentativas
 * consecutivas inválidas de autenticação.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class BloqueioTentativasDecorator extends AutenticadorDecorator {

    /** Quantidade máxima de erros consecutivos antes do bloqueio. */
    private static final int MAX_TENTATIVAS = 3;

    /** Contagem de tentativas inválidas por login. */
    private Map<String, Integer> tentativasInvalidas;

    /** Conjunto de logins bloqueados por excesso de tentativas. */
    private Set<String> loginsBloqueados;

    /**
     * Construtor do decorador de bloqueio por tentativas inválidas.
     *
     * @param autenticadorDecorado O autenticador a ser decorado.
     */
    public BloqueioTentativasDecorator(Autenticador autenticadorDecorado) {
        super(autenticadorDecorado);
        this.tentativasInvalidas = new HashMap<>();
        this.loginsBloqueados = new HashSet<>();
    }

    /**
     * Verifica se o login está bloqueado antes de delegar a autenticação;
     * contabiliza erros consecutivos e bloqueia ao atingir 3 falhas.
     *
     * @param login O login informado.
     * @param senha A senha informada.
     * @return O {@link Usuario} autenticado ou {@code null} em caso de falha ou bloqueio.
     */
    @Override
    public Usuario autenticar(String login, String senha) {
        if (login != null && this.loginsBloqueados.contains(login)) {
            System.out.printf("[Segurança] Acesso bloqueado para '%s': limite de %d tentativas inválidas atingido.%n",
                    login, MAX_TENTATIVAS);
            return null;
        }

        Usuario resultado = super.autenticar(login, senha);

        if (resultado != null) {
            this.tentativasInvalidas.remove(login);
            return resultado;
        }

        if (login != null) {
            int erros = this.tentativasInvalidas.getOrDefault(login, 0) + 1;
            this.tentativasInvalidas.put(login, erros);
            if (erros >= MAX_TENTATIVAS) {
                this.loginsBloqueados.add(login);
                System.out.printf("[Segurança] Login '%s' bloqueado após %d tentativas inválidas consecutivas.%n",
                        login, erros);
            }
        }
        return null;
    }

    /**
     * Verifica se um determinado login está bloqueado.
     *
     * @param login O login a consultar.
     * @return {@code true} se estiver bloqueado, {@code false} caso contrário.
     */
    public boolean isBloqueado(String login) {
        return this.loginsBloqueados.contains(login);
    }

    /**
     * Desbloqueia um login e zera seu contador de tentativas inválidas.
     *
     * @param login O login a desbloquear.
     */
    public void desbloquear(String login) {
        this.loginsBloqueados.remove(login);
        this.tentativasInvalidas.remove(login);
    }

    /**
     * Retorna uma representação textual do decorador de bloqueio.
     *
     * @return String descrevendo logins bloqueados e o componente envolvido.
     */
    @Override
    public String toString() {
        return String.format("BloqueioTentativasDecorator [Bloqueados: %d] -> %s",
                this.loginsBloqueados.size(), super.toString());
    }
}
