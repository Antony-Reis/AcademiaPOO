package br.com.academiapoo.service;

import br.com.academiapoo.model.*;

/**
 * Valida na catraca se o cadastro do cliente esta ativo.
 */
public class ValidadorCadastroAtivo extends ValidadorAcesso {

    /** Construtor padrao. */
    public ValidadorCadastroAtivo() {
        super();
    }

    @Override
    public boolean validar(Client cliente) {
        if (cliente == null) {
            System.out.println("[Catraca - Cadastro] Acesso negado: cliente não informado.");
            return false;
        }
        // verfica se o cliente ta ativo
        if (!cliente.getSituation()) {
            System.out.printf("[Catraca - Cadastro] Acesso negado para %s: cadastro inativo.%n",
                    cliente.getName());
            return false;
        }
        return validarProximo(cliente);
    }

    @Override
    public String toString() {
        return "ValidadorCadastroAtivo -> " + super.toString();
    }
}
