/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.academiapoo;

import com.mycompany.academiapoo.entities.GymRoom;
import com.mycompany.academiapoo.entities.Administrator;
import com.mycompany.academiapoo.entities.Client;
import com.mycompany.academiapoo.entities.Employee;
import com.mycompany.academiapoo.entities.GymClass;
import com.mycompany.academiapoo.entities.Produto;
import com.mycompany.academiapoo.entities.Estoque;
import com.mycompany.academiapoo.entities.Agendamento;
import com.mycompany.academiapoo.entities.FaseAgendamento;
import java.time.LocalDate;

/**
 * Classe principal responsável por iniciar o sistema da academia e executar os testes automatizados.
 * Serve como o ponto de entrada (entry point) do programa, validando a criação e a manipulação 
 * das entidades principais como Clientes, Funcionários, Administradores e Aulas.
 * 
 * @author Samuel/Antony
 * @version 1.0
 */
public class AcademiaPOO {

   
    public static void main(String[] args) {
       
        Client cliente1 = new Client (1,"Samuel", "Rua A", "12345678", "samuel@gmail.com", "1234567890", true);
        System.out.println(cliente1);
        cliente1.setName("Tom");
        cliente1.setSituation(false);
        cliente1.setFone("3182659742");
        
        System.out.println("Atualizacoes feitas: Nome: " + cliente1.getName() +
                ", Situacao: " + cliente1.getSituation() + ", Telefone: " + cliente1.getFone());
    
    Employee employee1 = new Employee(1, "samuel", "1234", "Recepcao");
    System.out.println(employee1);
    System.out.println(employee1.authentication("1234"));
    System.out.println(employee1.canManegeExpense());
    
    Employee adm1 = new Administrator(2, "samuel","4321" );
    System.out.println(adm1);
    System.out.println(adm1.authentication("4321"));
    System.out.println(adm1.canManegeExpense());
    
    GymClass modality1 = new GymClass(6,"Samuel",GymRoom.MUSCULACAO );
    System.out.println(modality1);
    GymClass modality2 = new GymClass(7,"Ana", GymRoom.SPINNING);
    System.out.println(modality2);
    GymClass modality3 = new GymClass(8, "Bob", GymRoom.FIT_DANCE);
    System.out.println(modality3);
    GymClass modality4 = new GymClass(9 , "Antony", GymRoom.PILATES);
    System.out.println(modality4);
    
    modality4.setInstructor("Ana");
    modality2.setInstructor("Antony");
    System.out.println("Alteracoes " + modality4.getInstructor() + " ," + modality2.getInstructor());
    
    // =========================================================================
    // TESTES DO SUBSISTEMA DE LOJINHA (PRODUTO & ESTOQUE)
    // =========================================================================
    System.out.println("\n==================================================");
    System.out.println("   DEMONSTRAÇÃO DO SUBSISTEMA DE LOJINHA DA ACADEMIA");
    System.out.println("==================================================");

    // 1. Instanciação de produtos
    Produto whey = new Produto(101, "Whey Protein Concentrado 900g", 149.90);
    Produto creatina = new Produto(102, "Creatina Monohidratada 300g", 89.90);
    Produto barraProteica = new Produto(103, "Barra de Proteína Cookies", 12.50);
    Produto coqueteleira = new Produto(104, "Coqueteleira 700ml Academia", 29.90);

    System.out.println("\n[Produtos Criados]");
    System.out.println(whey);
    System.out.println(creatina);
    System.out.println(barraProteica);
    System.out.println(coqueteleira);

    // 2. Manipulação de getters e setters (encapsulamento)
    barraProteica.setNome("Barra de Proteína Chocolate Belga");
    barraProteica.setPreco(14.00);
    System.out.println("\n[Após Atualização via Setters com Validação]");
    System.out.println("Nome atualizado: " + barraProteica.getNome() + " | Preço atualizado: R$ " + barraProteica.getPreco());

    // 3. Inicialização e adição ao estoque
    Estoque estoqueLojinha = new Estoque();
    estoqueLojinha.adicionarProduto(whey);
    estoqueLojinha.adicionarProduto(creatina);
    estoqueLojinha.adicionarProduto(barraProteica);
    estoqueLojinha.adicionarProduto(coqueteleira);

    System.out.println("\n" + estoqueLojinha);

    // 4. Verificação de produtos no estoque
    System.out.println("\n[Verificações no Estoque]");
    System.out.println("Creatina está no estoque? " + estoqueLojinha.verificarProduto(creatina));
    System.out.println("Produto ID 101 está no estoque? " + estoqueLojinha.verificarProdutoPorId(101));
    System.out.println("Produto ID 999 (inexistente) está no estoque? " + estoqueLojinha.verificarProdutoPorId(999));

    // 5. Remoção de produto do estoque
    System.out.println("\n[Remoção de Produto]");
    boolean removido = estoqueLojinha.removerProdutoPorId(103);
    System.out.println("Barra de Proteína (ID 103) removida com sucesso? " + removido);
    System.out.println("Barra de Proteína ainda está no estoque? " + estoqueLojinha.verificarProduto(barraProteica));

    // 6. Estado final do estoque
    System.out.println("\n" + estoqueLojinha);

    // =========================================================================
    // TESTES DO SUBSISTEMA DE AGENDAMENTO DE AULAS
    // =========================================================================
    System.out.println("\n==================================================");
    System.out.println("   DEMONSTRAÇÃO DO SUBSISTEMA DE AGENDAMENTO");
    System.out.println("==================================================");

    LocalDate hoje = LocalDate.of(2026, 11, 1);
    LocalDate dataAula1 = LocalDate.of(2026, 11, 10); // Daqui a 9 dias
    LocalDate dataAula2 = LocalDate.of(2026, 11, 10); // Daqui a 9 dias

    // Criação de Agendamentos preliminares
    Agendamento agendamento1 = new Agendamento(
            1, GymRoom.SPINNING, hoje, dataAula1, "Ana", 80.00, "1234567890123456"
    );
    Agendamento agendamento2 = new Agendamento(
            2, GymRoom.PILATES, hoje, dataAula2, "Antony", 100.00, "9876543210987654"
    );

    System.out.println("\n[Agendamentos Criados (Fase Inicial)]");
    System.out.println(agendamento1);
    System.out.println(agendamento2);

    // Regra 1 - Tentativa de confirmação a menos de 5 dias da aula (Ex: dia 06/11 para aula no dia 10/11 = 4 dias)
    System.out.println("\n--- Teste Regra 1: Confirmação Fora do Prazo (< 5 dias) ---");
    LocalDate tentativaTardia = LocalDate.of(2026, 11, 6);
    boolean confirmou1 = agendamento1.confirmarAgendamento(tentativaTardia);
    System.out.println("Confirmação foi aceita? " + confirmou1);
    System.out.println("Estado atual: " + agendamento1);

    // Regra 1 - Confirmação dentro do prazo (>= 5 dias antes, Ex: dia 03/11 para aula no dia 10/11 = 7 dias)
    System.out.println("\n--- Teste Regra 1: Confirmação Válida (>= 5 dias antes) ---");
    LocalDate confirmacaoValida = LocalDate.of(2026, 11, 3);
    boolean confirmou2 = agendamento2.confirmarAgendamento(confirmacaoValida);
    System.out.println("Confirmação foi aceita? " + confirmou2);
    System.out.println("Estado atual: " + agendamento2);

    // Regra 2 - Desistência até 3 dias antes da data (Ex: dia 07/11 para aula no dia 10/11 = 3 dias)
    // Devolve 50% e retém 50%
    System.out.println("\n--- Teste Regra 2: Desistência com Retenção de 50% (3 dias antes) ---");
    LocalDate dataDesistenciaNoPrazo = LocalDate.of(2026, 11, 7);
    double valorDevolvido = agendamento2.cancelarComReembolso(dataDesistenciaNoPrazo);
    System.out.println("Valor devolvido ao cliente: R$ " + valorDevolvido);
    System.out.println("Estado pós-desistência: " + agendamento2);

    // Regra 2 - Teste de desistência tardia (a menos de 3 dias, Ex: dia 09/11 para aula no dia 10/11 = 1 dia)
    System.out.println("\n--- Teste Regra 2: Desistência Tardia (< 3 dias antes - Retenção de 100%) ---");
    Agendamento agendamento3 = new Agendamento(
            3, GymRoom.FIT_DANCE, hoje, LocalDate.of(2026, 11, 10), "Bob", 60.00, "5555444433332222"
    );
    agendamento3.confirmarAgendamento(LocalDate.of(2026, 11, 2)); // confirmado a 8 dias
    double valorDevolvidoTardio = agendamento3.cancelarComReembolso(LocalDate.of(2026, 11, 9)); // 1 dia antes
    System.out.println("Valor devolvido ao cliente: R$ " + valorDevolvidoTardio);
    System.out.println("Estado pós-desistência: " + agendamento3);
   } 
    
}

