package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

/**
 * Elo concreto do padrão Chain of Responsibility que verifica se o cliente
 * possui cadastro ativo no sistema da academia.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class ValidadorCadastroAtivo extends ValidadorAcesso {

    /**
     * Construtor padrão do validador de cadastro ativo.
     */
    public ValidadorCadastroAtivo() {
        super();
    }

    /**
     * Valida se o cliente não é nulo e está com a situação ativa.
     *
     * @param cliente O cliente que solicita acesso.
     * @return {@code true} se o cadastro estiver ativo e os próximos elos aprovarem.
     */
    @Override
    public boolean validar(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca - Cadastro] Acesso negado: cliente não informado.");
            return false;
        }
        if (!cliente.getSituation()) {
            System.out.printf("[Catraca - Cadastro] Acesso negado para %s: cadastro inativo.%n",
                    cliente.getName());
            return false;
        }
        return validarProximo(cliente);
    }

    /**
     * Retorna uma representação textual do validador de cadastro ativo.
     *
     * @return String descrevendo o validador.
     */
    @Override
    public String toString() {
        return "ValidadorCadastroAtivo -> " + super.toString();
    }
}
