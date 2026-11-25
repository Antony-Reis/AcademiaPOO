package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// bloqueia se errar 3 vezes, ai nao da pra tentar denovo
/**
 * Decorator que bloqueia o login apos 3 tentativas invalidas seguidas.
 */
public class BloqueioTentativasDecorator extends AutenticadorDecorator {

    /** Limite de tentativas erradas antes de bloquear. */
    private static final int MAX_TENTATIVAS = 3;

    // mapas internos de controle, por isso nao tem setter
    private Map<String, Integer> tentativasInavlidas;
    private Set<String> loginsBloqueados;

    /** Construtor do decorator de bloqueio. */
    public BloqueioTentativasDecorator(Autenticador autenticadorDecorado) {
        super(autenticadorDecorado);
        this.tentativasInavlidas = new HashMap<>();
        this.loginsBloqueados = new HashSet<>();
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        if (login != null && this.loginsBloqueados.contains(login)) {
            System.out.printf("[Segurança] Acesso bloqueado para '%s': limite de %d tentativas inválidas atingido.%n",
                    login, MAX_TENTATIVAS);
            return null;
        }

        Usuario aux = super.autenticar(login, senha);

        if (aux != null) {
            this.tentativasInavlidas.remove(login);
            return aux;
        }

        if (login != null) {
            int erros = 0;
            if (this.tentativasInavlidas.containsKey(login)) {
                erros = this.tentativasInavlidas.get(login);
            }
            erros = erros + 1;
            this.tentativasInavlidas.put(login, erros);
            // System.out.println("debug: erros do login " + login + " = " + erros);
            if (erros >= MAX_TENTATIVAS) {
                this.loginsBloqueados.add(login);
                System.out.printf("[Segurança] Login '%s' bloqueado após %d tentativas inválidas consecutivas.%n",
                        login, erros);
            }
        }
        return null;
    }

    /** Verifica se o login esta bloqueado. */
    public boolean isBloqueado(String login) {
        return this.loginsBloqueados.contains(login);
    }

    /** Desbloqueia um login e zera os erros dele. */
    public void desbloquear(String login) {
        this.loginsBloqueados.remove(login);
        this.tentativasInavlidas.remove(login);
    }

    @Override
    public String toString() {
        return String.format("BloqueioTentativasDecorator [Bloqueados: %d] -> %s",
                this.loginsBloqueados.size(), super.toString());
    }
}
