/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import entities.Client;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório responsável pelo gerenciamento e persistência dos dados de clientes.
 * Realiza operações de leitura e escrita armazenando as informações localmente 
 * em formato JSON.
 * 
 * @author User
 * @version 1.0
 */
public class ClientRepository {
    
    /** Caminho do arquivo JSON utilizado para persistir os dados dos clientes. */
    private final String CAMINHO_ARQUIVO = "client.json";
    
    /** Instância do Gson configurada para conversão de objetos e formatação do JSON. */
    private final Gson gson;
    
    /**
     * Construtor padrão da classe.
     * Inicializa a instância do Gson com a configuração de "Pretty Printing" 
     * para gerar arquivos JSON indentados e fáceis de ler.
     */
    public ClientRepository() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
    }
    
    /**
     * Recupera todos os clientes cadastrados no arquivo JSON.
     * Caso o arquivo não exista ou ocorra um erro de leitura, retorna uma lista vazia.
     * 
     * @return {@code List<Client>} contendo todos os clientes cadastrados.
     */
    public List<Client> buscarTodos(){
        File arquivo = new File(CAMINHO_ARQUIVO);

        if (!arquivo.exists()){
            return new ArrayList<>();
        }

        try (FileReader reader = new FileReader(arquivo)) {
            Type tipoLista = new TypeToken<List<Client>>(){}.getType();
            List<Client> lista = gson.fromJson(reader, tipoLista);

            return lista != null ? lista : new ArrayList<>();

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON");
            return new ArrayList<>();
        }
    }
    
    /**
     * Sobrescreve o arquivo JSON com a lista de clientes fornecida.
     * Este método é de uso estritamente interno da classe (private).
     * 
     * @param lista A lista completa de clientes que será persistida no arquivo.
     */
    private void salvarLista(List<Client> lista) {
        try (FileWriter writer = new FileWriter(CAMINHO_ARQUIVO)) {
            gson.toJson(lista, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar no arquivo JSON: " + e.getMessage());
        }
    }
    
    /**
     * Adiciona um novo cliente ao arquivo JSON sem apagar os registros existentes.
     * O método lê o histórico atual, adiciona o novo registro na memória e atualiza o arquivo.
     * 
     * @param novaPessoa O objeto {@link Client} que será cadastrado.
     */
    public void salvarPessoa(Client novaPessoa) {
        // Busca a lista atual existente no arquivo antes de adicionar
        List<Client> pessoasAtuais = buscarTodos();
        pessoasAtuais.add(novaPessoa);
        salvarLista(pessoasAtuais);
    }
    
}
