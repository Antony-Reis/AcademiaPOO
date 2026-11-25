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
import br.com.academiapoo.model.RegistoAcesso;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

// fazendo a percistencia dos dados em json
/**
 * Classe utilitaria para salvar e carregar os dados do sistema em arquivos JSON usando Gson.
 */
public class PersistenciaJSON {

    /** Instancia do Gson com adaptadores de data e heranca. */
    private static final Gson GSON = criarGson();

    /** Construtor padrao. */
    public PersistenciaJSON() {
        super();
    }

    // adaptado pra funcionar com o gson (datas do java 8 e classes abstratas davam erro sem isso)
    private static Gson criarGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        builder.registerTypeAdapter(LocalDate.class, new LocalDateAdapter());
        builder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter());
        builder.registerTypeAdapter(YearMonth.class, new YearMonthAdapter());
        builder.registerTypeHierarchyAdapter(Plano.class, new PlanoAdapter());
        builder.registerTypeHierarchyAdapter(Modalidade.class, new ModalidadeAdapter());
        builder.registerTypeHierarchyAdapter(Usuario.class, new UsuarioAdapter());
        return builder.create();
    }

    /** Salva um objeto ou lista no caminho JSON informado usando try-with-resources. */
    public static <T> void salvar(String caminhoArquivo, T dados) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo JSON é obrigatório.");
        }
        File arq = new File(caminhoArquivo);
        File pasta = arq.getParentFile();
        if (pasta != null && !pasta.exists()) {
            pasta.mkdirs();
        }

        // System.out.println("debug: salvando em " + caminhoArquivo);
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(arq), StandardCharsets.UTF_8))) {
            GSON.toJson(dados, writer);
        } catch (IOException e) {
            System.err.println("[PersistenciaJSON] Erro ao salvar '" + caminhoArquivo + "': " + e.getMessage());
        }
    }

    /** Carrega um objeto ou lista de um arquivo JSON usando try-with-resources. */
    public static <T> T carregar(String caminhoArquivo, Type tipo) {
        if (caminhoArquivo == null || caminhoArquivo.trim().isEmpty()) {
            throw new IllegalArgumentException("O caminho do arquivo JSON é obrigatório.");
        }
        File arq = new File(caminhoArquivo);
        if (!arq.exists()) {
            return null;
        }

        T aux = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(arq), StandardCharsets.UTF_8))) {
            aux = GSON.fromJson(reader, tipo);
            return aux;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static <T> List<T> carregarLista(String caminhoArquivo, Type tipoLista) {
        List<T> temp = carregar(caminhoArquivo, tipoLista);
        if (temp != null) {
            return temp;
        } else {
            return new ArrayList<>();
        }
    }

    /** Salva a lista de clientes em JSON. */
    public static void salvarClientes(List<Client> clientes, String caminhoArquivo) {
        salvar(caminhoArquivo, clientes);
    }

    /** Carrega a lista de clientes de um arquivo JSON. */
    public static List<Client> carregarClientes(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Client>>() {}.getType());
    }

    /** Salva a lista de agendamentos em JSON. */
    public static void salvarAgendamentos(List<Agendamento> agendamentos, String caminhoArquivo) {
        salvar(caminhoArquivo, agendamentos);
    }

    /** Carrega a lista de agendamentos de um arquivo JSON. */
    public static List<Agendamento> carregarAgendamentos(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Agendamento>>() {}.getType());
    }

    /** Salva os registros de acesso da catraca em JSON. */
    public static void salvarRegistrosAcesso(List<RegistoAcesso> registros, String caminhoArquivo) {
        salvar(caminhoArquivo, registros);
    }

    /** Carrega os registros de acesso da catraca de um arquivo JSON. */
    public static List<RegistoAcesso> carregarRegistrosAcesso(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<RegistoAcesso>>() {}.getType());
    }

    /** Salva a lista de produtos em JSON. */
    public static void salvarProdutos(List<Produto> produtos, String caminhoArquivo) {
        salvar(caminhoArquivo, produtos);
    }

    /** Carrega a lista de produtos de um arquivo JSON. */
    public static List<Produto> carregarProdutos(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Produto>>() {}.getType());
    }

    /** Salva o extrato geral em JSON. */
    public static void salvarExtrato(Extrato extrato, String caminhoArquivo) {
        salvar(caminhoArquivo, extrato);
    }

    /** Carrega o extrato geral de um arquivo JSON. */
    public static Extrato carregarExtrato(String caminhoArquivo) {
        Extrato temp = carregar(caminhoArquivo, Extrato.class);
        if (temp != null) {
            return temp;
        }
        return new Extrato();
    }

    /** Salva a conta/extrato individual vinculada ao cliente em JSON. */
    public static void salvarExtratoCliente(Client cliente, Extrato extrato, String caminhoArquivo) {
        if (cliente == null || extrato == null) {
            throw new IllegalArgumentException("Cliente e extrato são obrigatórios.");
        }
        Map<String, Object> dadosConta = new LinkedHashMap<>();
        dadosConta.put("clienteId", cliente.getId());
        dadosConta.put("clienteNome", cliente.getName());
        dadosConta.put("extratoTexto", extrato.gerarExtratoCliente(cliente));
        salvar(caminhoArquivo, dadosConta);
    }

    /** Salva uma lista de relatorios de vendas em JSON. */
    public static void salvarRelatorios(List<String> relatorios, String caminhoArquivo) {
        salvar(caminhoArquivo, relatorios);
    }

    /** Carrega uma lista de relatorios de vendas de um arquivo JSON. */
    public static List<String> carregarRelatorios(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<String>>() {}.getType());
    }

    /** Salva a lista de usuarios (colaboradores e administradores) em JSON. */
    public static void salvarUsuarios(List<Usuario> usuarios, String caminhoArquivo) {
        salvar(caminhoArquivo, usuarios);
    }

    /** Carrega a lista de usuarios de um arquivo JSON. */
    public static List<Usuario> carregarUsuarios(String caminhoArquivo) {
        return carregarLista(caminhoArquivo, new TypeToken<List<Usuario>>() {}.getType());
    }

    /** Salva o estoque da lojinha em JSON. */
    public static void salvarEstoque(Estoque estoque, String caminhoArquivo) {
        salvar(caminhoArquivo, estoque);
    }

    /** Carrega o estoque da lojinha de um arquivo JSON. */
    public static Estoque carregarEstoque(String caminhoArquivo) {
        Estoque aux = carregar(caminhoArquivo, Estoque.class);
        if (aux != null) {
            return aux;
        }
        return new Estoque();
    }

    // adaptadores pro gson nao quebrar com LocalDate, LocalDateTime, YearMonth e heranca
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
            if (tipo.equals("PlanoDiaria")) {
                return new PlanoDiaria();
            } else if (tipo.equals("PlanoMensal")) {
                return new PlanoMensal();
            } else if (tipo.equals("PlanoAnual")) {
                return new PlanoAnual();
            } else {
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

            if (tipo.equals("Colaborador")) {
                return new Colaborador(nome, login, senha);
            } else if (tipo.equals("Administrador")) {
                return new Administrador(nome, login, senha);
            } else {
                return new Usuario(nome, login, senha);
            }
        }
    }

    @Override
    public String toString() {
        return "PersistenciaJSON [Formato: JSON (Gson) | Encoding: UTF-8]";
    }
}
