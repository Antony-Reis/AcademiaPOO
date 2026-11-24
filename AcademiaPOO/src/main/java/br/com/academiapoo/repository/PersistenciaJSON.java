package br.com.academiapoo.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import br.com.academiapoo.model.Administrador;
import br.com.academiapoo.model.Agendamento;
import br.com.academiapoo.model.Client;
import br.com.academiapoo.model.Colaborador;
import br.com.academiapoo.model.Estoque;
import br.com.academiapoo.service.Extrato;
import br.com.academiapoo.model.FitDance;
import br.com.academiapoo.model.Modalidade;
import br.com.academiapoo.model.Musculacao;
import br.com.academiapoo.model.Pilates;
import br.com.academiapoo.model.Plano;
import br.com.academiapoo.model.PlanoAnual;
import br.com.academiapoo.model.PlanoDiaria;
import br.com.academiapoo.model.PlanoMensal;
import br.com.academiapoo.model.Produto;
import br.com.academiapoo.model.RegistroAcesso;
import br.com.academiapoo.model.Spinning;
import br.com.academiapoo.model.Usuario;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe utilitária responsável pela persistência de dados da academia em arquivos JSON
 * utilizando a biblioteca Gson.
 *
 * Gerencia leitura e escrita segura com {@code try-with-resources} em UTF-8 para
 * Clientes, Agendamentos (com Modalidade e Plano), Registros de Acesso da Catraca,
 * Produtos, Relatórios de Vendas (Extrato), Usuários (Colaboradores/Administradores)
 * e Estoque.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class PersistenciaJSON {

    /** Instância configurada do Gson com adaptadores para datas e hierarquias polimórficas. */
    private static final Gson GSON = criarGson();

    /**
     * Construtor padrão da classe utilitária.
     */
    public PersistenciaJSON() {
        super();
    }

    /**
     * Configura o {@link Gson} com Pretty Printing e adaptadores para {@link LocalDate},
     * {@link LocalDateTime}, {@link YearMonth}, {@link Plano}, {@link Modalidade} e {@link Usuario}.
     */
    private static Gson criarGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(YearMonth.class, new YearMonthAdapter())
                .registerTypeHierarchyAdapter(Plano.class, new PlanoAdapter())
                .registerTypeHierarchyAdapter(Modalidade.class, new ModalidadeAdapter())
                .registerTypeHierarchyAdapter(Usuario.class, new UsuarioAdapter())
                .create();
    }

    /**
     * Salva qualquer objeto ou coleção em um arquivo JSON utilizando {@code try-with-resources}.
     *
     * @param caminhoArquivo Caminho do arquivo de destino.
     * @param dados Objeto ou lista a ser serializada.
     * @param <T> Tipo dos dados.
     */
    public static <T> void salvar(String caminhoArquivo, T dados) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo JSON é obrigatório.");
        }
        File arquivo = new File(caminhoArquivo);
        File diretorioPai = arquivo.getParentFile();
        if (diretorioPai != null && !diretorioPai.exists()) {
            diretorioPai.mkdirs();
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.UTF_8))) {
            GSON.toJson(dados, writer);
        } catch (IOException e) {
            System.err.println("[PersistenciaJSON] Erro ao salvar '" + caminhoArquivo + "': " + e.getMessage());
        }
    }

    /**
     * Carrega um objeto ou coleção de um arquivo JSON utilizando {@code try-with-resources}.
     *
     * @param caminhoArquivo Caminho do arquivo JSON.
     * @param tipo Tipo alvo para desserialização (ex: {@link TypeToken}).
     * @param <T> Tipo de retorno.
     * @return O objeto desserializado ou {@code null} se o arquivo não existir ou falhar.
     */
    public static <T> T carregar(String caminhoArquivo, Type tipo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo JSON é obrigatório.");
        }
        File arquivo = new File(caminhoArquivo);
        if (!arquivo.exists()) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(arquivo), StandardCharsets.UTF_8))) {
            return GSON.fromJson(reader, tipo);
        } catch (IOException e) {
            System.err.println("[PersistenciaJSON] Erro ao carregar '" + caminhoArquivo + "': " + e.getMessage());
            return null;
        }
    }

    private static <T> List<T> carregarLista(String caminhoArquivo, Type tipoLista) {
        List<T> resultado = carregar(caminhoArquivo, tipoLista);
        return (resultado != null) ? resultado : new ArrayList<>();
    }

    // =========================================================================
    // MÉTODOS ESPECÍFICOS POR ENTIDADE
    // =========================================================================

    public static void salvarClientes(List<Client> clientes, String caminhoArquivo) {
        salvar(caminhoArquivo, clientes);
    }

    public static List<Client> carregarClientes(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Client>>() {}.getType());
    }

    public static void salvarAgendamentos(List<Agendamento> agendamentos, String caminhoArquivo) {
        salvar(caminhoArquivo, agendamentos);
    }

    public static List<Agendamento> carregarAgendamentos(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Agendamento>>() {}.getType());
    }

    public static void salvarRegistrosAcesso(List<RegistroAcesso> registros, String caminhoArquivo) {
        salvar(caminhoArquivo, registros);
    }

    public static List<RegistroAcesso> carregarRegistrosAcesso(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<RegistroAcesso>>() {}.getType());
    }

    public static void salvarProdutos(List<Produto> produtos, String caminhoArquivo) {
        salvar(caminhoArquivo, produtos);
    }

    public static List<Produto> carregarProdutos(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Produto>>() {}.getType());
    }

    public static void salvarExtrato(Extrato extrato, String caminhoArquivo) {
        salvar(caminhoArquivo, extrato);
    }

    public static Extrato carregarExtrato(String caminhoArquivo) {
        Extrato extrato = carregar(caminhoArquivo, Extrato.class);
        return (extrato != null) ? extrato : new Extrato();
    }

    public static void salvarRelatorios(List<String> relatorios, String caminhoArquivo) {
        salvar(caminhoArquivo, relatorios);
    }

    public static List<String> carregarRelatorios(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<String>>() {}.getType());
    }

    public static void salvarUsuarios(List<Usuario> usuarios, String caminhoArquivo) {
        salvar(caminhoArquivo, usuarios);
    }

    public static List<Usuario> carregarUsuarios(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Usuario>>() {}.getType());
    }

    public static void salvarEstoque(Estoque estoque, String caminhoArquivo) {
        salvar(caminhoArquivo, estoque);
    }

    public static Estoque carregarEstoque(String caminhoArquivo) {
        Estoque estoque = carregar(caminhoArquivo, Estoque.class);
        return (estoque != null) ? estoque : new Estoque();
    }

    // =========================================================================
    // ADAPTADORES GSON (DATAS E POLIMORFISMO)
    // =========================================================================

    private static class LocalDateAdapter implements JsonSerializer<LocalDate>, JsonDeserializer<LocalDate> {
        @Override
        public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE));
        }

        @Override
        public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDate.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE);
        }
    }

    private static class LocalDateTimeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }

        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return LocalDateTime.parse(json.getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    private static class YearMonthAdapter implements JsonSerializer<YearMonth>, JsonDeserializer<YearMonth> {
        @Override
        public JsonElement serialize(YearMonth src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.toString());
        }

        @Override
        public YearMonth deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            return YearMonth.parse(json.getAsString());
        }
    }

    private static class PlanoAdapter implements JsonSerializer<Plano>, JsonDeserializer<Plano> {
        @Override
        public JsonElement serialize(Plano src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("tipo", src.getClass().getSimpleName());
            obj.addProperty("descricao", src.getTipoPlano());
            return obj;
        }

        @Override
        public Plano deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String tipo = obj.get("tipo").getAsString();
            switch (tipo) {
                case "PlanoDiaria":
                    return new PlanoDiaria();
                case "PlanoMensal":
                    return new PlanoMensal();
                case "PlanoAnual":
                    return new PlanoAnual();
                default:
                    throw new JsonParseException("Tipo de Plano desconhecido: " + tipo);
            }
        }
    }

    private static class ModalidadeAdapter implements JsonSerializer<Modalidade>, JsonDeserializer<Modalidade> {
        @Override
        public JsonElement serialize(Modalidade src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("tipo", src.getClass().getSimpleName());
            obj.addProperty("nome", src.getNome());
            obj.addProperty("valorBase", src.getValorBase());
            obj.add("plano", context.serialize(src.getPlano(), Plano.class));
            return obj;
        }

        @Override
        public Modalidade deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String tipo = obj.get("tipo").getAsString();
            double valorBase = obj.get("valorBase").getAsDouble();
            Plano plano = context.deserialize(obj.get("plano"), Plano.class);

            switch (tipo) {
                case "Spinning":
                    return new Spinning(valorBase, plano);
                case "Musculacao":
                    return new Musculacao(valorBase, plano);
                case "FitDance":
                    return new FitDance(valorBase, plano);
                case "Pilates":
                    return new Pilates(valorBase, plano);
                default:
                    throw new JsonParseException("Tipo de Modalidade desconhecido: " + tipo);
            }
        }
    }

    private static class UsuarioAdapter implements JsonSerializer<Usuario>, JsonDeserializer<Usuario> {
        @Override
        public JsonElement serialize(Usuario src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject obj = new JsonObject();
            obj.addProperty("tipo", src.getClass().getSimpleName());
            obj.addProperty("nome", src.getNome());
            obj.addProperty("login", src.getLogin());
            obj.addProperty("senha", src.getSenha());
            return obj;
        }

        @Override
        public Usuario deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject obj = json.getAsJsonObject();
            String tipo = obj.get("tipo").getAsString();
            String nome = obj.get("nome").getAsString();
            String login = obj.get("login").getAsString();
            String senha = obj.get("senha").getAsString();

            switch (tipo) {
                case "Colaborador":
                    return new Colaborador(nome, login, senha);
                case "Administrador":
                    return new Administrador(nome, login, senha);
                default:
                    return new Usuario(nome, login, senha);
            }
        }
    }

    @Override
    public String toString() {
        return "PersistenciaJSON [Formato: JSON (Gson) | Encoding: UTF-8]";
    }
}
