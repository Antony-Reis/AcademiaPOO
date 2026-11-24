package br.com.academiapoo;

import br.com.academiapoo.model.Administrador;
import br.com.academiapoo.model.Administrator;
import br.com.academiapoo.model.Agendamento;
import br.com.academiapoo.util.AgendamentoComparator;
import br.com.academiapoo.security.Autenticador;
import br.com.academiapoo.security.AutenticadorBasico;
import br.com.academiapoo.service.BalancoMensal;
import br.com.academiapoo.security.BloqueioTentativasDecorator;
import br.com.academiapoo.service.Catraca;
import br.com.academiapoo.model.Client;
import br.com.academiapoo.util.ClienteComparator;
import br.com.academiapoo.model.Colaborador;
import br.com.academiapoo.model.Employee;
import br.com.academiapoo.model.Estoque;
import br.com.academiapoo.service.Extrato;
import br.com.academiapoo.model.FitDance;
import br.com.academiapoo.model.GymClass;
import br.com.academiapoo.model.GymRoom;
import br.com.academiapoo.security.LogAuditoriaDecorator;
import br.com.academiapoo.model.Modalidade;
import br.com.academiapoo.model.Musculacao;
import br.com.academiapoo.model.Pilates;
import br.com.academiapoo.model.Plano;
import br.com.academiapoo.model.PlanoAnual;
import br.com.academiapoo.model.PlanoDiaria;
import br.com.academiapoo.model.PlanoMensal;
import br.com.academiapoo.model.Produto;
import br.com.academiapoo.model.RegistroAcesso;
import br.com.academiapoo.config.Sistema;
import br.com.academiapoo.model.Spinning;
import br.com.academiapoo.model.Usuario;
import br.com.academiapoo.service.ValidadorAcesso;
import br.com.academiapoo.service.ValidadorCadastroAtivo;
import br.com.academiapoo.service.ValidadorCapacidadeSala;
import br.com.academiapoo.service.ValidadorFinanceiro;
import br.com.academiapoo.repository.PersistenciaJSON;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Classe principal responsável por executar e validar todos os módulos do sistema
 * AcademiaPOO, organizada em blocos demarcados por questão do TPI, incluindo
 * coleções, iteradores, comparadores, busca e a simulação ponta a ponta de 10 clientes.
 *
 * @author Samuel/Antony
 * @version 2.0
 */
public class AcademiaPOO {

    /**
     * Método auxiliar de impressão utilizado nos percursos de coleção (Questão 18).
     *
     * @param objeto O objeto a ser exibido no console.
     */
    private static void imprimir(Object objeto) {
        System.out.println(objeto);
    }

    /**
     * Questão 20 — Localiza um cliente em uma coleção utilizando obrigatoriamente
     * {@link Iterator} e {@link Comparator}.
     *
     * @param clientes Lista de clientes onde será feita a busca.
     * @param alvo Cliente referência contendo o atributo de comparação preenchido.
     * @param comparator Comparador utilizado para definir igualdade pelo critério desejado.
     * @return O {@link Client} encontrado ou {@code null} caso não exista na lista.
     */
    public static Client encontrar(List<Client> clientes, Client alvo, Comparator<Client> comparator) {
        if (clientes == null || alvo == null || comparator == null) {
            return null;
        }
        Iterator<Client> iterator = clientes.iterator();
        while (iterator.hasNext()) {
            Client atual = iterator.next();
            if (comparator.compare(atual, alvo) == 0) {
                return atual;
            }
        }
        return null;
    }

    public static void main(String[] args) {

        // Questao 1 - Cadastro e manipulação básica de Clientes
        {
            System.out.println("=== QUESTÃO 1: CLIENTES ===");
            Client clienteBase = new Client(1, "Samuel", "Rua A", "12345678", "samuel@gmail.com", "12345678901", true);
            imprimir(clienteBase);
            clienteBase.setName("Tom");
            clienteBase.setSituation(false);
            clienteBase.setFone("3182659742");
            System.out.println("Atualizado: " + clienteBase.getName() + " | Ativo: " + clienteBase.getSituation());
        }

        // Questao 2 - Funcionários e Administradores (Herança e Polimorfismo)
        {
            System.out.println("\n=== QUESTÃO 2: FUNCIONÁRIOS E ADMINISTRADORES ===");
            Employee emp = new Employee(1, "samuel", "1234", "Recepcao");
            Employee adm = new Administrator(2, "samuel_adm", "4321");
            imprimir(emp + " | Auth: " + emp.authentication("1234") + " | Despesas: " + emp.canManegeExpense());
            imprimir(adm + " | Auth: " + adm.authentication("4321") + " | Despesas: " + adm.canManegeExpense());
        }

        // Questao 3 - Salas e Aulas (GymRoom e GymClass)
        {
            System.out.println("\n=== QUESTÃO 3: SALAS E AULAS ===");
            GymClass aula1 = new GymClass(6, "Samuel", GymRoom.MUSCULACAO);
            GymClass aula2 = new GymClass(7, "Ana", GymRoom.SPINNING);
            GymClass aula3 = new GymClass(8, "Bob", GymRoom.FIT_DANCE);
            GymClass aula4 = new GymClass(9, "Antony", GymRoom.PILATES);
            imprimir(aula1);
            imprimir(aula2);
            imprimir(aula3);
            imprimir(aula4);
        }

        // Questao 4 - Lojinha da Academia (Produto e Estoque)
        {
            System.out.println("\n=== QUESTÃO 4: LOJINHA E ESTOQUE ===");
            Produto whey = new Produto(101, "Whey Protein Concentrado 900g", 149.90);
            Produto barra = new Produto(103, "Barra de Proteína Cookies", 12.50);
            Estoque estoque = new Estoque();
            estoque.adicionarProduto(whey);
            estoque.adicionarProduto(barra);
            estoque.removerProdutoPorId(103);
            imprimir(estoque);
        }

        // Questao 5 - Classe Sistema e Atributos Estáticos (Salas e Contadores)
        {
            System.out.println("\n=== QUESTÃO 5: SISTEMA E ATRIBUTOS ESTÁTICOS ===");
            for (GymRoom sala : Sistema.getSalas()) {
                System.out.println(" - Sala: " + sala.getNome() + " (Capacidade: " + sala.getCapacidade() + ")");
            }
            System.out.println("Clientes (private static): " + Sistema.getTotalClientesPrivado());
            System.out.println("Clientes (protected static): " + Sistema.getTotalClientesProtegido());
            System.out.println("Produtos criados: " + Sistema.getTotalProdutosCriados());
        }

        // Questao 6 - Modalidades e Planos (Padrão Bridge)
        {
            System.out.println("\n=== QUESTÃO 6: MODALIDADES E PLANOS (BRIDGE) ===");
            Modalidade spinningDiaria = new Spinning(new PlanoDiaria());
            Modalidade pilatesAnual = new Pilates(new PlanoAnual());
            Modalidade fitDanceMensal = new FitDance(new PlanoMensal());
            Modalidade musculacaoAnual = new Musculacao(new PlanoAnual());
            imprimir(spinningDiaria);
            imprimir(pilatesAnual);
            imprimir(fitDanceMensal);
            imprimir(musculacaoAnual);
        }

        // Questao 7 - Agendamentos, Pagamento e Máquina de Estados
        {
            System.out.println("\n=== QUESTÃO 7: AGENDAMENTO E PAGAMENTO ===");
            Client c = new Client(10, "Lucas", "Rua das Flores", "31999991111", "lucas@email.com", "11122233344", true);
            LocalDate hoje = LocalDate.of(2026, 11, 1);
            LocalDate aula = LocalDate.of(2026, 11, 10);
            Agendamento ag = new Agendamento(10, c, new Pilates(new PlanoAnual()), hoje, aula, "Antony", "1234567890123456");
            ag.confirmarAgendamento(LocalDate.of(2026, 11, 3));
            double reembolso = ag.cancelarComReembolso(LocalDate.of(2026, 11, 7));
            System.out.println("Reembolso (50%): R$ " + reembolso + " | " + ag);
        }

        // Questao 8 - Catraca e Validação Sequencial (Chain of Responsibility)
        {
            System.out.println("\n=== QUESTÃO 8: CATRACA (CHAIN OF RESPONSIBILITY) ===");
            Client c = new Client(11, "Helena", "Av. Central", "31988882222", "helena@email.com", "22233344455", true);
            Agendamento ag = new Agendamento(11, c, new Spinning(new PlanoDiaria()), LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 10), "Ana", "4444555566667777");
            ag.confirmarAgendamento(LocalDate.of(2026, 11, 2));

            ValidadorAcesso cadeia = new ValidadorCadastroAtivo();
            cadeia.setProximo(new ValidadorFinanceiro(Arrays.asList(ag)))
                  .setProximo(new ValidadorCapacidadeSala(GymRoom.SPINNING, 10));

            Catraca catraca = new Catraca(cadeia);
            catraca.registrarEntrada(c);
            catraca.registrarSaida(c);
        }

        // Questao 9 - Login e Auditoria (Padrão Decorator)
        {
            System.out.println("\n=== QUESTÃO 9: LOGIN E AUDITORIA (DECORATOR) ===");
            Usuario adm = new Administrador("Ana Souza", "ana.admin", "admin456");
            Autenticador auth = new BloqueioTentativasDecorator(
                    new LogAuditoriaDecorator(new AutenticadorBasico(Arrays.asList(adm)))
            );
            imprimir("Autenticado: " + auth.autenticar("ana.admin", "admin456"));
        }

        // Questao 18 - Iterator e Laço foreach
        {
            System.out.println("\n=== QUESTÃO 18: ITERATOR E FOREACH ===");
            ArrayList<Client> clientesQ18 = new ArrayList<>();
            clientesQ18.add(new Client(181, "Bruno", "Rua 1", "31911110001", "bruno@email.com", "10020030001", true));
            clientesQ18.add(new Client(182, "Amanda", "Rua 2", "31911110002", "amanda@email.com", "10020030002", true));
            clientesQ18.add(new Client(183, "Carlos", "Rua 3", "31911110003", "carlos@email.com", "10020030003", false));

            /*
             * Explicação do funcionamento do Iterator e relação com o laço foreach:
             * - A coleção ArrayList implementa a interface Iterable<Client>, que fornece
             *   o método iterator() retornando um Iterator<Client> com um cursor interno.
             * - hasNext() verifica se ainda há elementos à frente do cursor sem avançá-lo;
             *   next() retorna o elemento atual e avança o cursor para a próxima posição.
             * - Em Java, o laço enhanced-for (foreach) é açúcar sintático: o compilador
             *   traduz "for (Client c : lista)" exatamente para um laço while usando
             *   Iterator (Iterable.iterator() + hasNext() + next()) por baixo dos panos.
             */
            System.out.println("[Percurso explícito com Iterator + while]");
            Iterator<Client> iterator = clientesQ18.iterator();
            while (iterator.hasNext()) {
                imprimir(iterator.next());
            }

            System.out.println("[Percurso equivalente com laço foreach]");
            for (Client c : clientesQ18) {
                imprimir(c);
            }
        }

        // Questao 19 - Testes de Comparator e Collections.sort() com dois critérios
        {
            System.out.println("\n=== QUESTÃO 19: COMPARATOR E COLLECTIONS.SORT ===");
            ArrayList<Client> clientesQ19 = new ArrayList<>();
            clientesQ19.add(new Client(30, "Zelia", "Rua Z", "31900000030", "zelia@email.com", "30030030030", true));
            clientesQ19.add(new Client(10, "Alberto", "Rua A", "31900000010", "alberto@email.com", "10010010010", true));
            clientesQ19.add(new Client(20, "Mariana", "Rua M", "31900000020", "mariana@email.com", "20020020020", false));

            System.out.println("[Antes da ordenação]");
            for (Client c : clientesQ19) {
                imprimir(" - ID: " + c.getId() + " | Nome: " + c.getName());
            }

            Collections.sort(clientesQ19, new ClienteComparator(ClienteComparator.Criterio.NOME));
            System.out.println("[Depois da ordenação 1: por NOME]");
            for (Client c : clientesQ19) {
                imprimir(" - ID: " + c.getId() + " | Nome: " + c.getName());
            }

            Collections.sort(clientesQ19, new ClienteComparator(ClienteComparator.Criterio.ID));
            System.out.println("[Depois da ordenação 2: por ID]");
            for (Client c : clientesQ19) {
                imprimir(" - ID: " + c.getId() + " | Nome: " + c.getName());
            }
        }

        // Questao 20 - Método encontrar (Iterator + Comparator) vs Collections.binarySearch()
        {
            System.out.println("\n=== QUESTÃO 20: ENCONTRAR() VS COLLECTIONS.BINARYSEARCH() ===");
            ArrayList<Client> clientesQ20 = new ArrayList<>();
            Client c1 = new Client(50, "Fernanda", "Rua F", "31900000050", "fernanda@email.com", "50050050050", true);
            Client c2 = new Client(15, "Daniel", "Rua D", "31900000015", "daniel@email.com", "15015015015", true);
            Client c3 = new Client(40, "Beatriz", "Rua B", "31900000040", "beatriz@email.com", "40040040040", true);
            clientesQ20.add(c1);
            clientesQ20.add(c2);
            clientesQ20.add(c3);

            Comparator<Client> compNome = new ClienteComparator(ClienteComparator.Criterio.NOME);
            Client chaveBusca = new Client(0, "Daniel", "", "", "", "00000000000", true);
            Client chaveInexistente = new Client(0, "Zorro", "", "", "", "00000000000", true);

            // 1) Busca linear com encontrar() (funciona mesmo com a lista desordenada)
            Client achadoLinear = encontrar(clientesQ20, chaveBusca, compNome);
            Client naoAchadoLinear = encontrar(clientesQ20, chaveInexistente, compNome);
            System.out.println("[encontrar() na lista desordenada] Encontrado 'Daniel': " + (achadoLinear != null ? achadoLinear.getName() + " (ID " + achadoLinear.getId() + ")" : "null"));
            System.out.println("[encontrar() na lista desordenada] Buscando 'Zorro': " + naoAchadoLinear);

            // 2) Pré-condição obrigatória do binarySearch: ordenar pelo MESMO Comparator
            Collections.sort(clientesQ20, compNome);
            int idxEncontrado = Collections.binarySearch(clientesQ20, chaveBusca, compNome);
            int idxNaoEncontrado = Collections.binarySearch(clientesQ20, chaveInexistente, compNome);
            System.out.println("[binarySearch() na lista ordenada] Índice de 'Daniel': " + idxEncontrado + " -> " + clientesQ20.get(idxEncontrado).getName());
            System.out.println("[binarySearch() na lista ordenada] Índice de 'Zorro': " + idxNaoEncontrado + " (ponto de inserção negativo)");

            /*
             * Comparação entre encontrar() e Collections.binarySearch():
             * - Pré-condição: encontrar() não exige ordenação prévia; binarySearch() exige que a lista
             *   esteja previamente ordenada pelo mesmo Comparator, caso contrário o resultado é indefinido.
             * - Complexidade: encontrar() realiza busca linear O(n); binarySearch() realiza busca binária O(log n).
             * - Retorno: encontrar() devolve o próprio objeto Client (ou null se ausente); binarySearch()
             *   devolve o índice >= 0 se encontrado, ou (-(pontoDeInsercao) - 1) negativo se ausente.
             */
        }

        // Questao 21 - Teste de Cenários: Atendimento Completo e Individualizado de 10 Clientes
        {
            System.out.println("\n==================================================");
            System.out.println("   QUESTÃO 21: ATENDIMENTO COMPLETO DE 10 CLIENTES");
            System.out.println("==================================================");

            LocalDate hoje = LocalDate.of(2026, 11, 1);
            LocalDate dataAula = LocalDate.of(2026, 11, 10);

            // Infraestrutura compartilhada da academia para a simulação
            Estoque estoque = new Estoque();
            Produto pWhey = new Produto(201, "Whey Protein 900g", 150.00);
            Produto pCreatina = new Produto(202, "Creatina 300g", 90.00);
            Produto pBarra = new Produto(203, "Barra Proteica", 15.00);
            Produto pCoqueteleira = new Produto(204, "Coqueteleira 700ml", 30.00);
            Produto pIsotonico = new Produto(205, "Isotônico 500ml", 10.00);
            estoque.adicionarProduto(pWhey);
            estoque.adicionarProduto(pCreatina);
            estoque.adicionarProduto(pBarra);
            estoque.adicionarProduto(pCoqueteleira);
            estoque.adicionarProduto(pIsotonico);

            Extrato extratoGeral = new Extrato();
            List<Client> clientesAtendidos = new ArrayList<>();
            List<Agendamento> agendamentosGeral = new ArrayList<>();

            // Autenticação de funcionários (Decorator)
            Usuario colab = new Colaborador("Carlos Recepção", "carlos.op", "op123");
            Usuario admin = new Administrador("Ana Diretora", "ana.dir", "dir999");
            Autenticador autenticador = new BloqueioTentativasDecorator(
                    new LogAuditoriaDecorator(new AutenticadorBasico(Arrays.asList(colab, admin)))
            );
            Usuario operadorLogado = autenticador.autenticar("carlos.op", "op123");
            System.out.println("Operador em turno: " + operadorLogado.getNome());

            ValidadorFinanceiro valFinanceiro = new ValidadorFinanceiro();
            ValidadorCapacidadeSala valCapacidade = new ValidadorCapacidadeSala(GymRoom.SPINNING, 18);
            ValidadorAcesso cadeiaCatraca = new ValidadorCadastroAtivo();
            cadeiaCatraca.setProximo(valFinanceiro).setProximo(valCapacidade);
            Catraca catraca = new Catraca(cadeiaCatraca);

            // --- CLIENTE 1: Turista, Spinning Diária, confirmação no prazo, catraca OK, compra Whey ---
            System.out.println("\n--- [Cenário Cliente 1] ---");
            Client cli1 = new Client(1, "Gabriel Turista", "Pousada Sol", "31900010001", "gabriel@tur.com", "11111111101", true);
            clientesAtendidos.add(cli1);
            Agendamento ag1 = new Agendamento(101, cli1, new Spinning(new PlanoDiaria()), hoje, dataAula, "Ana", "4111111111111111");
            ag1.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            agendamentosGeral.add(ag1);
            valFinanceiro.adicionarAgendamento(ag1);
            extratoGeral.adicionarAgendamento(ag1);
            catraca.registrarEntrada(cli1);
            estoque.removerProduto(pWhey);
            extratoGeral.registrarVendaProduto(pWhey, hoje);
            catraca.registrarSaida(cli1);

            // --- CLIENTE 2: Mensalista, Musculação Mensal, confirmação no prazo, catraca OK, compra Creatina ---
            System.out.println("\n--- [Cenário Cliente 2] ---");
            Client cli2 = new Client(2, "Juliana Mensal", "Rua Bela", "31900010002", "juliana@email.com", "11111111102", true);
            clientesAtendidos.add(cli2);
            Agendamento ag2 = new Agendamento(102, cli2, new Musculacao(new PlanoMensal()), hoje, dataAula, "Samuel", "4222222222222222");
            ag2.confirmarAgendamento(LocalDate.of(2026, 11, 3));
            agendamentosGeral.add(ag2);
            valFinanceiro.adicionarAgendamento(ag2);
            extratoGeral.adicionarAgendamento(ag2);
            catraca.registrarEntrada(cli2);
            estoque.removerProduto(pCreatina);
            extratoGeral.registrarVendaProduto(pCreatina, hoje);
            catraca.registrarSaida(cli2);

            // --- CLIENTE 3: Anual, Pilates (15% desc.), confirma e depois desiste com 3 dias (reembolso 50%) ---
            System.out.println("\n--- [Cenário Cliente 3] ---");
            Client cli3 = new Client(3, "Ricardo Anual", "Av. Brasil", "31900010003", "ricardo@email.com", "11111111103", true);
            clientesAtendidos.add(cli3);
            Agendamento ag3 = new Agendamento(103, cli3, new Pilates(new PlanoAnual()), hoje, dataAula, "Antony", "4333333333333333");
            ag3.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            ag3.cancelarComReembolso(LocalDate.of(2026, 11, 7)); // 3 dias antes -> devolve 50%
            agendamentosGeral.add(ag3);
            extratoGeral.adicionarAgendamento(ag3);
            catraca.registrarEntrada(cli3); // Barrado no financeiro pois reserva foi cancelada

            // --- CLIENTE 4: FitDance Mensal, confirmação tardia (< 5 dias) -> cancelado automaticamente ---
            System.out.println("\n--- [Cenário Cliente 4] ---");
            Client cli4 = new Client(4, "Patrícia Atrasada", "Rua C", "31900010004", "patricia@email.com", "11111111104", true);
            clientesAtendidos.add(cli4);
            Agendamento ag4 = new Agendamento(104, cli4, new FitDance(new PlanoMensal()), hoje, dataAula, "Bob", "4444444444444444");
            ag4.confirmarAgendamento(LocalDate.of(2026, 11, 6)); // 4 dias antes -> expirado/cancelado
            agendamentosGeral.add(ag4);
            catraca.registrarEntrada(cli4);

            // --- CLIENTE 5: Spinning Anual, confirma e desiste com 1 dia (< 3 dias -> retenção 100%), compra Barra ---
            System.out.println("\n--- [Cenário Cliente 5] ---");
            Client cli5 = new Client(5, "Eduardo Tarde", "Rua D", "31900010005", "eduardo@email.com", "11111111105", true);
            clientesAtendidos.add(cli5);
            Agendamento ag5 = new Agendamento(105, cli5, new Spinning(new PlanoAnual()), hoje, dataAula, "Ana", "4555555555555555");
            ag5.confirmarAgendamento(LocalDate.of(2026, 11, 1));
            ag5.cancelarComReembolso(LocalDate.of(2026, 11, 9)); // 1 dia antes -> retenção 100%
            agendamentosGeral.add(ag5);
            extratoGeral.adicionarAgendamento(ag5);
            estoque.removerProduto(pBarra);
            extratoGeral.registrarVendaProduto(pBarra, hoje);

            // --- CLIENTE 6: Cadastro inativo -> barrado na catraca pelo ValidadorCadastroAtivo ---
            System.out.println("\n--- [Cenário Cliente 6] ---");
            Client cli6 = new Client(6, "Sandra Inativa", "Rua E", "31900010006", "sandra@email.com", "11111111106", false);
            clientesAtendidos.add(cli6);
            catraca.registrarEntrada(cli6);

            // --- CLIENTE 7: Cadastro ativo sem plano/diária paga -> barrado pelo ValidadorFinanceiro ---
            System.out.println("\n--- [Cenário Cliente 7] ---");
            Client cli7 = new Client(7, "Marcos Pendente", "Rua F", "31900010007", "marcos@email.com", "11111111107", true);
            clientesAtendidos.add(cli7);
            catraca.registrarEntrada(cli7);

            // --- CLIENTE 8: Turista Pilates Diária, sala lotada -> barrado; libera após vaga; compra Coqueteleira ---
            System.out.println("\n--- [Cenário Cliente 8] ---");
            Client cli8 = new Client(8, "Camila Turista", "Hotel Serra", "31900010008", "camila@tur.com", "11111111108", true);
            clientesAtendidos.add(cli8);
            Agendamento ag8 = new Agendamento(108, cli8, new Pilates(new PlanoDiaria()), hoje, dataAula, "Antony", "4888888888888888");
            ag8.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            agendamentosGeral.add(ag8);
            valFinanceiro.adicionarAgendamento(ag8);
            extratoGeral.adicionarAgendamento(ag8);
            valCapacidade.setLotacaoAtual(20); // Sala cheia
            catraca.registrarEntrada(cli8);
            valCapacidade.setLotacaoAtual(19); // Abriu vaga
            catraca.registrarEntrada(cli8);
            estoque.removerProduto(pCoqueteleira);
            extratoGeral.registrarVendaProduto(pCoqueteleira, hoje);
            catraca.registrarSaida(cli8);

            // --- CLIENTE 9: Musculação Anual, compra Isotônico, emite nota/extrato e cancela matrícula ---
            System.out.println("\n--- [Cenário Cliente 9] ---");
            Client cli9 = new Client(9, "Rafael Encerrando", "Rua G", "31900010009", "rafael@email.com", "11111111109", true);
            clientesAtendidos.add(cli9);
            Agendamento ag9 = new Agendamento(109, cli9, new Musculacao(new PlanoAnual()), hoje, dataAula, "Samuel", "4999999999999999");
            ag9.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            agendamentosGeral.add(ag9);
            valFinanceiro.adicionarAgendamento(ag9);
            extratoGeral.adicionarAgendamento(ag9);
            catraca.registrarEntrada(cli9);
            estoque.removerProduto(pIsotonico);
            extratoGeral.registrarVendaProduto(pIsotonico, hoje);
            catraca.registrarSaida(cli9);
            cli9.setSituation(false); // Cancelamento de matrícula após conclusão dos processos
            System.out.println("Matrícula de " + cli9.getName() + " cancelada ao fim do atendimento (Ativo: " + cli9.getSituation() + ").");

            // --- CLIENTE 10: FitDance Mensal, catraca OK, fechamento gerencial e persistência JSON ---
            System.out.println("\n--- [Cenário Cliente 10] ---");
            Client cli10 = new Client(10, "Larissa Vip", "Rua H", "31900010010", "larissa@email.com", "11111111110", true);
            clientesAtendidos.add(cli10);
            Agendamento ag10 = new Agendamento(110, cli10, new FitDance(new PlanoMensal()), hoje, dataAula, "Bob", "4000000000000010");
            ag10.confirmarAgendamento(LocalDate.of(2026, 11, 3));
            agendamentosGeral.add(ag10);
            valFinanceiro.adicionarAgendamento(ag10);
            extratoGeral.adicionarAgendamento(ag10);
            catraca.registrarEntrada(cli10);
            catraca.registrarSaida(cli10);

            // Fechamento de caixa / Nota Fiscal / Extrato e Balanço restrito ao Administrador
            System.out.println("\n" + extratoGeral.gerarRelatorioDiario(hoje));
            System.out.println("\n" + extratoGeral.gerarRelatorioMensal(YearMonth.of(2026, 11)));

            BalancoMensal balancoFinal = new BalancoMensal(
                    YearMonth.of(2026, 11),
                    extratoGeral.calcularReceitaAulas(),
                    extratoGeral.calcularReceitaLoja(),
                    180.00,
                    220.00,
                    60.00
            );
            try {
                balancoFinal.gerarBalanco(operadorLogado);
            } catch (SecurityException ex) {
                System.out.println("\n[Trava de Acesso] " + ex.getMessage());
            }

            Usuario adminLogado = autenticador.autenticar("ana.dir", "dir999");
            System.out.println("\n" + balancoFinal.gerarBalanco(adminLogado));

            // Persistência final do estado completo em JSON
            PersistenciaJSON.salvarClientes(clientesAtendidos, "dados/clientes_q21.json");
            PersistenciaJSON.salvarAgendamentos(agendamentosGeral, "dados/agendamentos_q21.json");
            PersistenciaJSON.salvarRegistrosAcesso(catraca.getHistoricoAcessos(), "dados/acessos_q21.json");
            PersistenciaJSON.salvarExtrato(extratoGeral, "dados/extrato_q21.json");
            PersistenciaJSON.salvarEstoque(estoque, "dados/estoque_q21.json");
            PersistenciaJSON.salvarUsuarios(Arrays.asList(colab, admin), "dados/usuarios_q21.json");
            System.out.println("\n[Persistência JSON] Estado completo dos 10 cenários salvo com sucesso em dados/*.json.");
            System.out.println("Estoque restante após vendas: " + estoque.getProdutos().size() + " itens.");
        }
    }
}
