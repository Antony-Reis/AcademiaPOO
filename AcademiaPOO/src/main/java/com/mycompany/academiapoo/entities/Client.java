package entities;

/**
 * Representa um cliente da academia em Milho Verde.
 * Esta classe armazena os dados cadastrais, de contato e a situação de adimplência do cliente,
 * aplicando regras de pseudo-anonimização no CPF para proteção de dados pessoais.
 * 
 * @author Seu Nome
 * @version 1.0
 */
public class Client {
    
    /** O identificador único do cliente no sistema. */
    private Integer id;
    
    /** O nome completo do cliente. */
    private String name;
    
    /** O endereço residencial ou de hospedagem do cliente (importante para turistas). */
    private String address;
    
    /** O número de telefone ou celular para contato. */
    private String fone;
    
    /** O endereço de e-mail eletrônico do cliente. */
    private String email;
    
    /** O CPF pseudo-anonimizado do cliente para fins de segurança e conformidade de privacidade. */
    private String cpf;
    
    /** A situação atual do cliente na academia (true para ativo/adimplente, false para inativo/inadimplente). */
    private boolean situation;

    /**
     * Construtor completo para cadastrar um novo cliente.
     * O CPF fornecido passa automaticamente pelo processo de pseudo-anonimização antes de ser armazenado.
     *
     * @param id O identificador único do cliente.
     * @param name O nome completo do cliente.
     * @param address O endereço do cliente.
     * @param fone O telefone de contato.
     * @param email O e-mail do cliente.
     * @param cpf O CPF original do cliente (será ofuscado internamente).
     * @param situation A situação inicial do cadastro do cliente.
     */
    public Client(Integer id, String name, String address, String fone,
            String email, String cpf, boolean situation) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.fone = fone;
        this.email = email;
        this.cpf = pseudoAnonymizeCpf(cpf);
        this.situation = situation;
    }

    /**
     * Retorna o identificador único do cliente.
     *
     * @return O id do cliente.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Retorna o nome do cliente.
     *
     * @return O nome completo.
     */
    public String getName() {
        return name;
    }

    /**
     * Retorna o endereço cadastrado do cliente.
     *
     * @return O endereço residencial ou temporário.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Retorna o telefone de contato do cliente.
     *
     * @return O número do telefone.
     */
    public String getFone() {
        return fone;
    }

    /**
     * Retorna o e-mail do cliente.
     *
     * @return O endereço de e-mail.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retorna o CPF do cliente em formato pseudo-anonimizado (ex: ***.456.789-**).
     *
     * @return O CPF protegido por máscara.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Retorna a situação cadastral ou financeira do cliente.
     *
     * @return {@code true} se estiver regular/ativo, {@code false} caso contrário.
     */
    public boolean getSituation() {
        return situation;
    }

    /**
     * Altera o nome do cliente.
     *
     * @param name O novo nome completo.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Altera o endereço do cliente.
     *
     * @param address O novo endereço.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Altera o telefone de contato do cliente.
     *
     * @param fone O novo número de telefone.
     */
    public void setFone(String fone) {
        this.fone = fone;
    }

    /**
     * Altera o e-mail do cliente.
     *
     * @param email O novo endereço de e-mail.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Altera a situação ativa ou inativa do cliente no sistema.
     *
     * @param situation {@code true} para ativar, {@code false} para desativar.
     */
    public void setSituation(boolean situation) {
        this.situation = situation;
    }

    /**
     * Método auxiliar privado que mascara os primeiros e últimos dígitos do CPF 
     * para cumprir a exigência de pseudo-anonimização do problema.
     *
     * @param rawCpf O CPF bruto informado pelo cliente.
     * @return O CPF formatado com máscaras de proteção.
     */
    private String pseudoAnonymizeCpf(String rawCpf) {
        if (rawCpf == null || rawCpf.length() < 11) {
            return "###.###.###-##";
        }
        // Remove caracteres não numéricos caso existam
        String cleanCpf = rawCpf.replaceAll("[^0-9]", "");
        if (cleanCpf.length() == 11) {
            return "***." + cleanCpf.substring(3, 6) + "." + cleanCpf.substring(6, 9) + "-**";
        }
        return rawCpf;
    }

    /**
     * Retorna uma representação em formato de texto (String) com os dados do cliente.
     * Exibe o CPF com a máscara protetora aplicada de forma segura.
     *
     * @return Uma String contendo todos os atributos públicos e protegidos do cliente.
     */
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
