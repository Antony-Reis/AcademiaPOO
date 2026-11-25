package br.com.academiapoo.model;

import br.com.academiapoo.config.Sistema;

/**
 * Representa um cliente da academia com dados de contato e CPF pseudo-anonimizado.
 */
public class Client {

    /** Identificador do cliente. */
    // nao tem setter pra nao trocar o id do cliente depois de cadastrado
    private Integer id;

    /** Nome do cliente. */
    private String name;

    /** Endereco residencial ou de hospedagem. */
    private String address;

    /** Telefone de contato. */
    private String fone;

    /** E-mail do cliente. */
    private String email;

    /** CPF mascarado do cliente. */
    // nao tem setter porque o CPF ja entra pseudo-anonimizado no construtor
    private String cpf;

    /** Situacao do cadastro (ativo ou inativo). */
    private boolean situation;

    /** Construtor completo do cliente. */
    public Client(Integer id, String name, String address, String fone,
            String email, String cpf, boolean situation) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fone = fone;
        this.email = email;
        this.cpf = pseudoAnonymizeCpf(cpf);
        this.situation = situation;
        Sistema.registrarNovoCliente();
    }

    /** Retorna o id do cliente. */
    public Integer getId() {
        return id;
    }

    /** Retorna o nome do cliente. */
    public String getName() {
        return name;
    }

    /** Retorna o endereco do cliente. */
    public String getAddress() {
        return address;
    }

    /** Retorna o telefone do cliente. */
    public String getFone() {
        return fone;
    }

    /** Retorna o e-mail do cliente. */
    public String getEmail() {
        return email;
    }

    /** Retorna o CPF mascarado. */
    public String getCpf() {
        return cpf;
    }

    /** Retorna true se o cliente estiver ativo. */
    public boolean getSituation() {
        return situation;
    }

    /** Atualiza o nome do cliente. */
    public void setName(String name) {
        this.name = name;
    }

    /** Atualiza o endereco do cliente. */
    public void setAddress(String address) {
        this.address = address;
    }

    /** Atualiza o telefone do cliente. */
    public void setFone(String fone) {
        this.fone = fone;
    }

    /** Atualiza o e-mail do cliente. */
    public void setEmail(String email) {
        this.email = email;
    }

    /** Atualiza a situacao do cliente. */
    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    // mascara o comeco e o fim do cpf
    private String pseudoAnonymizeCpf(String rawCpf) {
        if (rawCpf == null || rawCpf.length() < 11) {
            return "###.###.###-##";
        }
        String cleanCpf = rawCpf.replaceAll("[^0-9]", "");
        if (cleanCpf.length() == 11) {
            return "***." + cleanCpf.substring(3, 6) + "." + cleanCpf.substring(6, 9) + "-**";
        }
        return rawCpf;
    }

    @Override
    public String toString() {
        return "Client{" + "id = " + id +
                ", name = " + name +
                ", address = " + address +
                ", fone = " + fone +
                ", email = " + email +
                ", cpf = " + cpf +
                ", situation = " + situation + '}';
    }
}
