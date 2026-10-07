package entities;

/**
 * Representa um Administrador do sistema da academia.
 * Esta classe estende a classe {@link Employee} e possui permissões totais de acesso,
 * incluindo o gerenciamento de despesas e a visualização do balanço mensal.
 * 
 * @author Samuel/Antony
 * @version 1.0
 * @see Employee
 */
public class Administrator extends Employee {
    
    /**
     * Construtor para inicializar um novo Administrador.
     * Utiliza a palavra-chave super para repassar os dados de identificação,
     * autenticação e definir o cargo fixo como "Administrador".
     *
     * @param id O identificador único do administrador.
     * @param username O nome de usuário para login no sistema.
     * @param password A senha de acesso do administrador.
     */
    public Administrator(int id, String username, String password) {
        super(id, username, password, "Administrador");
    }

    /**
     * Verifica se o usuário possui permissão para gerenciar as despesas da academia.
     * Sobrescreve o método da classe base para conceder acesso total ao administrador.
     *
     * @return {@code true}, pois administradores sempre têm permissão para gerenciar despesas.
     */
    @Override
    public boolean canManegeExpense() {
        return true; 
    }

    /**
     * Retorna uma representação em formato de texto (String) com os dados do administrador.
     * Combina as informações básicas da classe base (Employee) através do método super.toString().
     *
     * @return Uma String contendo os detalhes do administrador.
     */
    @Override
    public String toString() {
        return "Administrator{" + super.toString() + '}';
    }
}
