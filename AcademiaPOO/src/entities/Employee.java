package entities;

/**
 * Representa um funcionário colaborador genérico do sistema da academia em Milho Verde.
 * Esta classe serve como classe base para o controle de autenticação, identificação 
 * e atribuição de papéis (roles) dentro do sistema, definindo o nível básico de permissões.
 * 
 * @author Samuel/Antony
 * @version 1.0
 */
public class Employee {
    
    /** O identificador único do funcionário. */
    private int id;
    
    /** O nome de usuário (username) utilizado para realizar o login no sistema. */
    private String username;
    
    /** A senha de acesso criptografada ou em texto simples associada ao usuário. */
    private String password;
    
    /** O cargo ou papel desempenhado pelo funcionário na academia (ex: Colaborador, Administrador). */
    private String role;
    
    /**
     * Construtor completo para inicializar um novo Funcionário.
     *
     * @param id O identificador único do funcionário.
     * @param username O nome de usuário para credenciamento de login.
     * @param password A senha de acesso ao sistema.
     * @param role O cargo ou função que o funcionário exercerá.
     */
    public Employee(int id, String username, String password, String role){
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Retorna o identificador único do funcionário.
     *
     * @return O id do funcionário.
     */
    public int getId() {
        return id;
    }

    /**
     * Retorna o nome de usuário cadastrado para o login do funcionário.
     *
     * @return O nome de usuário (username).
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retorna a credencial de senha atualmente armazenada para este funcionário.
     *
     * @return A senha de acesso.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Retorna o papel ou cargo atribuído a este funcionário no sistema.
     *
     * @return O cargo (role) do funcionário.
     */
    public String getRole() {
        return role;
    }

    /**
     * Altera o nome de usuário utilizado para a autenticação do funcionário.
     *
     * @param username O novo nome de usuário.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Altera a senha de acesso do funcionário.
     *
     * @param password A nova senha de acesso.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Altera o cargo ou papel do funcionário no sistema, modificando seu nível de escopo.
     *
     * @param role O novo cargo a ser atribuído.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retorna uma representação em formato de texto (String) com os dados básicos do funcionário.
     * Por razões estritas de segurança e privacidade de dados, o campo de senha (password) é omitido.
     *
     * @return Uma String contendo o id, username e papel (role) do funcionário.
     */
    @Override
    public String toString() {
        return "Employee{" + " id: " + id + ", username: " + username + ", role: " + role + '}';
    }
    
    /**
     * Realiza a validação das credenciais de acesso comparando a senha informada com a senha gravada.
     *
     * @param passwordInformed A senha digitada pelo usuário durante a tentativa de login.
     * @return {@code true} se a senha informada for idêntica à senha do cadastro; {@code false} caso contrário.
     */
    public boolean authentication(String passwordInformed){
        return this.password.equals(passwordInformed);
    }
    
    /**
     * Define a política padrão de controle de acesso para o gerenciamento de despesas financeiras da academia.
     * Funcionários comuns (Colaboradores) não possuem privilégios administrativos. Este método deve ser 
     * sobrescrito por subclasses que exijam níveis elevados de permissão.
     *
     * @return {@code false} por padrão para funcionários comuns, indicando que não possuem permissão de gerenciamento.
     */
    public boolean canManegeExpense(){
        return false;
    }
}
