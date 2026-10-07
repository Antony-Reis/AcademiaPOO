/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.academiapoo;

import entities.Administrator;
import entities.Client;
import entities.Employee;
import entities.GymClass;

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
    
    GymClass modality1 = new GymClass(6, "Sala 7", "Tommy","Spinning", 15);
    System.out.println(modality1);
    GymClass modality2 = new GymClass(7, "Sala 8", "Ana","Musculacao", 10);
    System.out.println(modality2);
    GymClass modality3 = new GymClass(8, "Sala 9", "Bob","Fit Dance", 12);
    System.out.println(modality3);
    GymClass modality4 = new GymClass(9, "Sala 10", "Antony","Pilates", 5);
    System.out.println(modality4);
    
    modality4.setInstructor("Ana");
    modality4.setCapacity(14);
    modality2.setInstructor("Antony");
    modality2.setCapacity(6);
    System.out.print("Alteracoes " + modality4.getInstructor() + " ," + modality2.getInstructor() + " " +
            " e " + modality2.getCapacity() + " ," + modality4.getCapacity() + " ");
    
    
   } 
    
}

