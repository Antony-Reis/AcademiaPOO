/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.academiapoo.entities;

/**
 *
 * @author User
 */
public enum GymRoom {
    SPINNING("Spinning", 20),
    MUSCULACAO("Musculação", 40),
    FIT_DANCE("Fit Dance", 20),
    PILATES("Pilates", 20);
    
    private final String nome;
    private final int capacidade;
    
    GymRoom(String nome, int capacidade) {
        this.nome = nome;
        this.capacidade = capacidade;
    }

    public String getNome() {
        return nome;
    }

    public int getCapacidade() {
        return capacidade;
    }
    
    
}
