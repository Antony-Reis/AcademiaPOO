package br.com.academiapoo;

import br.com.academiapoo.config.Sistema;
import br.com.academiapoo.model.Administrador;
import br.com.academiapoo.model.Agendamento;
import br.com.academiapoo.model.Client;
import br.com.academiapoo.model.Colaborador;
import br.com.academiapoo.model.Estoque;
import br.com.academiapoo.model.FitDance;
import br.com.academiapoo.model.GymClass;
import br.com.academiapoo.model.GymRoom;
import br.com.academiapoo.model.Modalidade;
import br.com.academiapoo.model.Musculacao;
import br.com.academiapoo.model.Pilates;
import br.com.academiapoo.model.PlanoAnual;
import br.com.academiapoo.model.PlanoDiaria;
import br.com.academiapoo.model.PlanoMensal;
import br.com.academiapoo.model.Produto;
import br.com.academiapoo.model.RegistoAcesso;
import br.com.academiapoo.model.Spinning;
import br.com.academiapoo.model.Usuario;
import br.com.academiapoo.repository.PersistenciaJSON;
import br.com.academiapoo.security.Autenticador;
import br.com.academiapoo.security.AutenticadorBasico;
import br.com.academiapoo.security.BloqueioTentativasDecorator;
import br.com.academiapoo.security.LogAuditoriaDecorator;
import br.com.academiapoo.service.BalancoMensal;
import br.com.academiapoo.service.Catraca;
import br.com.academiapoo.service.Extrato;
import br.com.academiapoo.service.ValidadorAcesso;
import br.com.academiapoo.service.ValidadorCadastroAtivo;
import br.com.academiapoo.service.ValidadorCapacidadeSala;
import br.com.academiapoo.service.ValidadorFinanceiro;
import br.com.academiapoo.util.AgendamentoComparator;
import br.com.academiapoo.util.ClienteComparator;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * Classe principal com os testes de todas as questoes e cenarios da academia.
 */
public class AcademiaPOO {

    private static void imprimir(Object objeto) {
        System.out.println(objeto);
    }

    // Questao 20 - busca usando Iterator + Comparator (ideia baseada no exemplo de comparator)
    /**
     * Procura um cliente na lista usando Iterator e Comparator.
     */
    public static Client encontrar(List<Client> clientes, Client alvo, Comparator<Client> comparator) {
        if (clientes == null || alvo == null || comparator == null) {
            return null;
        }
        Client achou = null;
        Iterator<Client> it = clientes.iterator();
        while (it.hasNext()) {
            Client aux = it.next();
            if (comparator.compare(aux, alvo) == 0) {
                achou = aux;
                break;
            }
        }
        // System.out.println("debug: encontrar retornou " + achou);
        return achou;
    }

    /** Executa os testes de todas as questoes do sistema. */
    public static void main(String[] args) {

        // Questao 1 - CRUD de Clientes (incluir, editar e remover)
        {
            System.out.println("=== QUESTÃO 1: CRUD DE CLIENTES ===");
            List<Client> cadastroClientes = new ArrayList<>();
            Client c1 = new Client(1, "Samuel", "Rua A", "12345678", "samuel@gmail.com", "12345678901", true);
            Client c2 = new Client(2, "Maria", "Rua B", "31988887777", "maria@gmail.com", "98765432100", true);
            cadastroClientes.add(c1);
            cadastroClientes.add(c2);
            imprimir("Incluídos: " + cadastroClientes.size() + " | " + c1);

            // edicao
            c1.setName("Tom");
            c1.setSituation(false);
            c1.setFone("3182659742");
            System.out.println("Editado: " + c1.getName() + " | Ativo: " + c1.getSituation());

            // remocao
            cadastroClientes.remove(c2);
            System.out.println("Após remoção, total na lista: " + cadastroClientes.size());
        }

        // Questao 2 - CRUD de Colaborador e Administrador + alterar senha do Admin
        {
            System.out.println("\n=== QUESTÃO 2: COLABORADORES E ADMINISTRADOR ===");
            List<Usuario> equipe = new ArrayList<>();
            Colaborador colab = new Colaborador("Samuel Recepção", "samuel.rec", "1234");
            Administrador adm = new Administrador("Antony Admin", "antony.adm", "4321");
            equipe.add(colab);
            equipe.add(adm);

            // editar colaborador
            colab.setNome("Samuel Silva");
            colab.setLogin("samuel.silva");
            imprimir(colab + " | Senha confere: " + colab.verificarSenha("1234"));

            // alterar senha do administrador
            boolean trocouErrado = adm.alterarSenha("errada", "nova999");
            boolean trocouCerto = adm.alterarSenha("4321", "nova999");
            System.out.println("Alterar senha (atual errada): " + trocouErrado + " | (atual certa): " + trocouCerto);
            System.out.println("Login adm com nova senha: " + adm.verificarSenha("nova999"));

            // remover colaborador
            equipe.remove(colab);
            System.out.println("Equipe após remover colaborador: " + equipe.size() + " (" + equipe.get(0) + ")");
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

        // Questao 4 - Lojinha e Estoque (incluir, verificar por ID, buscar e remover)
        {
            System.out.println("\n=== QUESTÃO 4: LOJINHA E ESTOQUE ===");
            Produto whey = new Produto(101, "Whey Protein Concentrado 900g", 149.90);
            Produto barra = new Produto(103, "Barra de Proteína Cookies", 12.50);
            Estoque estoque = new Estoque();
            estoque.adicionarProduto(whey);
            estoque.adicionarProduto(barra);
            System.out.println("Tem produto 101? " + estoque.verficarProdutoPorId(101));
            System.out.println("Busca ID 103: " + estoque.buscarProdutoPorId(103).orElse(null));
            estoque.removerProdutoPorId(103);
            imprimir(estoque);
        }

        // Questao 5 - Classe Sistema, 4 salas estaticas e contadores
        {
            System.out.println("\n=== QUESTÃO 5: SISTEMA E ATRIBUTOS ESTÁTICOS ===");
            for (GymRoom sala : Sistema.getSalas()) {
                System.out.println(" - Sala: " + sala);
            }
            System.out.println("Clientes (private static): " + Sistema.getTotalClientesPrivado());
            System.out.println("Clientes (protected static): " + Sistema.getTotalClientesProtegido());
            System.out.println("Resumo unificado: " + Sistema.getResumoInstanciasClientesEProdutos());
        }

        // Questao 6 - Modalidades e Planos (Bridge)
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

        // Questao 7 - Agendamento (5 dias uteis) e cancelamento com 50% de reembolso
        {
            System.out.println("\n=== QUESTÃO 7: AGENDAMENTO E PAGAMENTO ===");
            Client c = new Client(10, "Lucas", "Rua das Flores", "31999991111", "lucas@email.com", "11122233344", true);
            LocalDate hoje = LocalDate.of(2026, 11, 1);
            LocalDate aula = LocalDate.of(2026, 11, 10);
            Agendamento ag = new Agendamento(10, c, new Pilates(new PlanoAnual()), hoje, aula, "Antony", "1234567890123456");
            ag.confirmarAgendamento(LocalDate.of(2026, 11, 3)); // 5 dias uteis antes de 10/11
            double reembolso = ag.cancelarComReembolso(LocalDate.of(2026, 11, 7));
            System.out.println("Reembolso (50%): R$ " + reembolso + " | " + ag);
        }

        // Questao 8 - Catraca (Chain of Responsibility + presentes + relatorios diario, mensal e anual)
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
            System.out.println("Presentes na academia agora: " + catraca.contarPessoasPresentes() + " -> " + catraca.listarPessoasPresentes().get(0).getName());
            catraca.registrarSaida(c);
            System.out.println("Presentes após saída: " + catraca.contarPessoasPresentes());

            System.out.println(catraca.gerarRelatorioDiario(LocalDate.now()));
            System.out.println(catraca.gerarRelatorioMensal(YearMonth.now()));
            System.out.println(catraca.gerarRelatorioAnual(LocalDate.now().getYear()));
        }

        // Questao 9 - Login com Decorator (auditoria + bloqueio apos 3 tentativas)
        {
            System.out.println("\n=== QUESTÃO 9: LOGIN E AUDITORIA (DECORATOR) ===");
            Usuario adm = new Administrador("Ana Souza", "ana.admin", "admin456");
            BloqueioTentativasDecorator bloqueio = new BloqueioTentativasDecorator(
                    new LogAuditoriaDecorator(new AutenticadorBasico(Arrays.asList(adm)))
            );
            Autenticador auth = bloqueio;

            // 3 tentativas erradas pra testar o bloqueio
            auth.autenticar("ana.admin", "errada1");
            auth.autenticar("ana.admin", "errada2");
            auth.autenticar("ana.admin", "errada3");
            System.out.println("Bloqueada após 3 falhas? " + bloqueio.isBloqueado("ana.admin"));
            auth.autenticar("ana.admin", "admin456"); // barrado enquanto bloqueado

            // desbloqueia e autentica certo
            bloqueio.desbloquear("ana.admin");
            imprimir("Autenticado após desbloqueio: " + auth.autenticar("ana.admin", "admin456"));
        }

        // Questao 10 - Conta/Extrato individual do cliente (aulas + loja)
        {
            System.out.println("\n=== QUESTÃO 10: EXTRATO / CONTA INDIVIDUAL DO CLIENTE ===");
            Client cli = new Client(12, "Beatriz", "Rua Flor", "31977776666", "bia@email.com", "33344455566", true);
            LocalDate hoje = LocalDate.of(2026, 11, 1);
            Agendamento ag = new Agendamento(12, cli, new FitDance(new PlanoMensal()), hoje, LocalDate.of(2026, 11, 10), "Bob", "5555666677778888");
            ag.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            Produto agua = new Produto(104, "Água Mineral 500ml", 5.00);

            Extrato ext = new Extrato();
            ext.adicionarAgendamento(ag);
            ext.registrarVendaProduto(cli, agua, hoje);
            System.out.println(ext.gerarExtratoCliente(cli));
        }

        // Questao 11 - Balanco Mensal com lancamento de despesa travado para Colaborador
        {
            System.out.println("\n=== QUESTÃO 11: BALANÇO MENSAL E CONTROLE DE DESPESAS ===");
            Usuario colab = new Colaborador("João", "joao.op", "123");
            Usuario adm = new Administrador("Ana", "ana.adm", "999");
            BalancoMensal balanco = new BalancoMensal(YearMonth.of(2026, 11), 500.0, 120.0, 100.0, 150.0, 30.0);

            try {
                balanco.registrarDespesa(colab, "Material de uso diário", 45.0);
            } catch (SecurityException ex) {
                System.out.println("[Trava Colaborador] " + ex.getMessage());
            }

            balanco.registrarDespesa(adm, "Material de uso diário", 45.0);
            System.out.println(balanco.gerarBalanco(adm));
        }

        // Questao 12 - AgendamentoComparator com 2 criterios diferentes
        {
            System.out.println("\n=== QUESTÃO 12: AGENDAMENTO COMPARATOR ===");
            Client cA = new Client(21, "Zeca", "Rua 1", "31900001", "z@email.com", "11111111111", true);
            Client cB = new Client(22, "Ana", "Rua 2", "31900002", "a@email.com", "22222222222", true);
            LocalDate hoje = LocalDate.of(2026, 11, 1);
            LocalDate aula = LocalDate.of(2026, 11, 10);
            Agendamento a1 = new Agendamento(201, cA, new Pilates(new PlanoDiaria()), hoje, aula, "Antony", "1111222233334444");
            Agendamento a2 = new Agendamento(202, cB, new FitDance(new PlanoMensal()), hoje, aula, "Bob", "2222333344445555");
            List<Agendamento> listaAg = new ArrayList<>(Arrays.asList(a1, a2));

            Collections.sort(listaAg, new AgendamentoComparator(AgendamentoComparator.Criterio.VALOR));
            System.out.println("Ordenado por VALOR: " + listaAg.get(0).getCliente().getName() + " (R$ " + listaAg.get(0).getPreco() + ")");

            Collections.sort(listaAg, new AgendamentoComparator(AgendamentoComparator.Criterio.CLIENTE_NOME));
            System.out.println("Ordenado por CLIENTE_NOME: " + listaAg.get(0).getCliente().getName() + ", " + listaAg.get(1).getCliente().getName());
        }

        // Questao 13 - Persistencia JSON (Salvar e Carregar Clientes, Agendamentos, Produtos, Relatorios, Usuarios e Estoque)
        {
            System.out.println("\n=== QUESTÃO 13: PERSISTÊNCIA JSON (SALVAR E CARREGAR) ===");
            Client cJson = new Client(301, "Cliente JSON", "Rua JSON", "31999990000", "json@email.com", "44455566677", true);
            Produto pJson = new Produto(301, "Garrafa Térmica", 49.90);
            Estoque estJson = new Estoque(Arrays.asList(pJson));
            Usuario uJson = new Colaborador("Pedro", "pedro.op", "abc");
            Agendamento agJson = new Agendamento(301, cJson, new Spinning(new PlanoDiaria()), LocalDate.of(2026, 11, 1), LocalDate.of(2026, 11, 10), "Ana", "6666777788889999");

            PersistenciaJSON.salvarClientes(Arrays.asList(cJson), "dados/clientes.json");
            PersistenciaJSON.salvarAgendamentos(Arrays.asList(agJson), "dados/agendamentos.json");
            PersistenciaJSON.salvarProdutos(Arrays.asList(pJson), "dados/produtos.json");
            PersistenciaJSON.salvarRelatorios(Arrays.asList("Relatório Diário Teste: R$ 49.90"), "dados/relatorios_vendas.json");
            PersistenciaJSON.salvarUsuarios(Arrays.asList(uJson), "dados/usuarios.json");
            PersistenciaJSON.salvarEstoque(estJson, "dados/estoque.json");

            List<Client> lidosCli = PersistenciaJSON.carregarClientes("dados/clientes.json");
            List<Agendamento> lidosAg = PersistenciaJSON.carregarAgendamentos("dados/agendamentos.json");
            List<Produto> lidosProd = PersistenciaJSON.carregarProdutos("dados/produtos.json");
            List<String> lidosRel = PersistenciaJSON.carregarRelatorios("dados/relatorios_vendas.json");
            List<Usuario> lidosUser = PersistenciaJSON.carregarUsuarios("dados/usuarios.json");
            Estoque lidoEst = PersistenciaJSON.carregarEstoque("dados/estoque.json");
            System.out.println("Carregado JSON -> Clientes: " + lidosCli.size()
                    + " | Agendamentos: " + lidosAg.size()
                    + " | Produtos: " + lidosProd.size()
                    + " | Relatórios: " + lidosRel.size()
                    + " | Usuários: " + lidosUser.size()
                    + " | Estoque: " + lidoEst.getQuantidadeTotal());
        }

        // Questao 14 - Encapsulamento, construtores e JavaDoc do projeto
        {
            System.out.println("\n=== QUESTÃO 14: ENCAPSULAMENTO E JAVADOC ===");
            System.out.println("Encapsulamento aplicado em todas as classes (atributos private/protected, getters/setters e super() nos construtores).");
        }

        // Questao 18 - Iterator e Laco foreach
        {
            System.out.println("\n=== QUESTÃO 18: ITERATOR E FOREACH ===");
            ArrayList<Client> clientesQ18 = new ArrayList<>();
            clientesQ18.add(new Client(181, "Bruno", "Rua 1", "31911110001", "bruno@email.com", "10020030001", true));
            clientesQ18.add(new Client(182, "Amanda", "Rua 2", "31911110002", "amanda@email.com", "10020030002", true));
            clientesQ18.add(new Client(183, "Carlos", "Rua 3", "31911110003", "carlos@email.com", "10020030003", false));

            /*
             * Como funciona o Iterator e sua relacao com o foreach:
             * O ArrayList implementa Iterable, que devolve um Iterator com cursor interno.
             * hasNext() checa se existe proximo item e next() pega o item e anda o cursor.
             * O laco foreach ("for (Client c : lista)") e traduzido pelo compilador Java
             * exatamente para esse while usando iterator(), hasNext() e next().
             */
            System.out.println("Explicação: o foreach do Java usa Iterator por baixo dos panos (iterator() + hasNext() + next()).");
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

        // Questao 19 - Comparator (chamada direta a compare + Collections.sort com 2 criterios)
        {
            System.out.println("\n=== QUESTÃO 19: COMPARATOR E COLLECTIONS.SORT ===");
            ArrayList<Client> listaTemp = new ArrayList<>();
            Client zelia = new Client(30, "Zelia", "Rua Z", "31900000030", "zelia@email.com", "30030030030", true);
            Client alberto = new Client(10, "Alberto", "Rua A", "31900000010", "alberto@email.com", "10010010010", true);
            Client mariana = new Client(20, "Mariana", "Rua M", "31900000020", "mariana@email.com", "20020020020", false);
            listaTemp.add(zelia);
            listaTemp.add(alberto);
            listaTemp.add(mariana);

            Comparator<Client> compPorNome = new ClienteComparator(ClienteComparator.Criterio.NOME);
            System.out.println("Chamada direta compare(Zelia, Alberto): " + compPorNome.compare(zelia, alberto));

            System.out.println("[Antes da ordenação]");
            for (int i = 0; i < listaTemp.size(); i++) {
                Client aux = listaTemp.get(i);
                imprimir(" - ID: " + aux.getId() + " | Nome: " + aux.getName());
            }

            Collections.sort(listaTemp, compPorNome);
            System.out.println("[Depois da ordenação 1: por NOME]");
            for (int i = 0; i < listaTemp.size(); i++) {
                Client aux = listaTemp.get(i);
                imprimir(" - ID: " + aux.getId() + " | Nome: " + aux.getName());
            }

            Collections.sort(listaTemp, new ClienteComparator(ClienteComparator.Criterio.ID));
            System.out.println("[Depois da ordenação 2: por ID]");
            for (int i = 0; i < listaTemp.size(); i++) {
                Client aux = listaTemp.get(i);
                imprimir(" - ID: " + aux.getId() + " | Nome: " + aux.getName());
            }
        }

        // Questao 20 - encontrar() vs Collections.binarySearch()
        {
            System.out.println("\n=== QUESTÃO 20: ENCONTRAR() VS COLLECTIONS.BINARYSEARCH() ===");
            ArrayList<Client> listaQ20 = new ArrayList<>();
            Client c1 = new Client(50, "Fernanda", "Rua F", "31900000050", "fernanda@email.com", "50050050050", true);
            Client c2 = new Client(15, "Daniel", "Rua D", "31900000015", "daniel@email.com", "15015015015", true);
            Client c3 = new Client(40, "Beatriz", "Rua B", "31900000040", "beatriz@email.com", "40040040040", true);
            listaQ20.add(c1);
            listaQ20.add(c2);
            listaQ20.add(c3);

            Comparator<Client> compNome = new ClienteComparator(ClienteComparator.Criterio.NOME);
            Client chaveBusca = new Client(0, "Daniel", "", "", "", "00000000000", true);
            Client chaveInexistente = new Client(0, "Zorro", "", "", "", "00000000000", true);

            Client achadoLinear = encontrar(listaQ20, chaveBusca, compNome);
            Client naoAchadoLinear = encontrar(listaQ20, chaveInexistente, compNome);

            String msgAchado;
            if (achadoLinear != null) {
                msgAchado = achadoLinear.getName() + " (ID " + achadoLinear.getId() + ")";
            } else {
                msgAchado = "null";
            }
            System.out.println("[encontrar() na lista desordenada] Encontrado 'Daniel': " + msgAchado);
            System.out.println("[encontrar() na lista desordenada] Buscando 'Zorro': " + naoAchadoLinear);

            // tem que ordenar antes pro binarySearch funcionar
            Collections.sort(listaQ20, compNome);
            int idxEncontrado = Collections.binarySearch(listaQ20, chaveBusca, compNome);
            int idxNaoEncontrado = Collections.binarySearch(listaQ20, chaveInexistente, compNome);
            // System.out.println("debug: idxEncontrado=" + idxEncontrado + " idxNaoEncontrado=" + idxNaoEncontrado);
            System.out.println("[binarySearch() na lista ordenada] Índice de 'Daniel': " + idxEncontrado + " -> " + listaQ20.get(idxEncontrado).getName());
            System.out.println("[binarySearch() na lista ordenada] Índice de 'Zorro': " + idxNaoEncontrado + " (ponto de inserção negativo)");
            System.out.println("Comparação: encontrar() faz busca linear O(n) sem exigir lista ordenada e retorna o Client (ou null);");
            System.out.println("            binarySearch() faz busca binária O(log n), exige lista ordenada pelo mesmo Comparator e retorna o índice.");
        }

        // Questao 21 - Atendimento completo e individualizado dos 10 clientes
        {
            System.out.println("\n==================================================");
            System.out.println("   QUESTÃO 21: ATENDIMENTO COMPLETO DE 10 CLIENTES");
            System.out.println("==================================================");

            LocalDate hoje = LocalDate.of(2026, 11, 1);
            LocalDate dataAula = LocalDate.of(2026, 11, 10);

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

            // Cliente 1
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
            extratoGeral.registrarVendaProduto(cli1, pWhey, hoje);
            catraca.registrarSaida(cli1);
            System.out.println(extratoGeral.gerarExtratoCliente(cli1));

            // Cliente 2
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
            extratoGeral.registrarVendaProduto(cli2, pCreatina, hoje);
            catraca.registrarSaida(cli2);
            System.out.println(extratoGeral.gerarExtratoCliente(cli2));

            // Cliente 3
            System.out.println("\n--- [Cenário Cliente 3] ---");
            Client cli3 = new Client(3, "Ricardo Anual", "Av. Brasil", "31900010003", "ricardo@email.com", "11111111103", true);
            clientesAtendidos.add(cli3);
            Agendamento ag3 = new Agendamento(103, cli3, new Pilates(new PlanoAnual()), hoje, dataAula, "Antony", "4333333333333333");
            ag3.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            ag3.cancelarComReembolso(LocalDate.of(2026, 11, 7));
            agendamentosGeral.add(ag3);
            extratoGeral.adicionarAgendamento(ag3);
            catraca.registrarEntrada(cli3);
            System.out.println(extratoGeral.gerarExtratoCliente(cli3));

            // Cliente 4
            System.out.println("\n--- [Cenário Cliente 4] ---");
            Client cli4 = new Client(4, "Patrícia Atrasada", "Rua C", "31900010004", "patricia@email.com", "11111111104", true);
            clientesAtendidos.add(cli4);
            Agendamento ag4 = new Agendamento(104, cli4, new FitDance(new PlanoMensal()), hoje, dataAula, "Bob", "4444444444444444");
            ag4.confirmarAgendamento(LocalDate.of(2026, 11, 6));
            agendamentosGeral.add(ag4);
            catraca.registrarEntrada(cli4);

            // Cliente 5
            System.out.println("\n--- [Cenário Cliente 5] ---");
            Client cli5 = new Client(5, "Eduardo Tarde", "Rua D", "31900010005", "eduardo@email.com", "11111111105", true);
            clientesAtendidos.add(cli5);
            Agendamento ag5 = new Agendamento(105, cli5, new Spinning(new PlanoAnual()), hoje, dataAula, "Ana", "4555555555555555");
            ag5.confirmarAgendamento(LocalDate.of(2026, 11, 1));
            ag5.cancelarComReembolso(LocalDate.of(2026, 11, 9));
            agendamentosGeral.add(ag5);
            extratoGeral.adicionarAgendamento(ag5);
            estoque.removerProduto(pBarra);
            extratoGeral.registrarVendaProduto(cli5, pBarra, hoje);
            System.out.println(extratoGeral.gerarExtratoCliente(cli5));

            // Cliente 6
            System.out.println("\n--- [Cenário Cliente 6] ---");
            Client cli6 = new Client(6, "Sandra Inativa", "Rua E", "31900010006", "sandra@email.com", "11111111106", false);
            clientesAtendidos.add(cli6);
            catraca.registrarEntrada(cli6);

            // Cliente 7
            System.out.println("\n--- [Cenário Cliente 7] ---");
            Client cli7 = new Client(7, "Marcos Pendente", "Rua F", "31900010007", "marcos@email.com", "11111111107", true);
            clientesAtendidos.add(cli7);
            catraca.registrarEntrada(cli7);

            // Cliente 8
            System.out.println("\n--- [Cenário Cliente 8] ---");
            Client cli8 = new Client(8, "Camila Turista", "Hotel Serra", "31900010008", "camila@tur.com", "11111111108", true);
            clientesAtendidos.add(cli8);
            Agendamento ag8 = new Agendamento(108, cli8, new Pilates(new PlanoDiaria()), hoje, dataAula, "Antony", "4888888888888888");
            ag8.confirmarAgendamento(LocalDate.of(2026, 11, 2));
            agendamentosGeral.add(ag8);
            valFinanceiro.adicionarAgendamento(ag8);
            extratoGeral.adicionarAgendamento(ag8);
            valCapacidade.setLotacaoAtual(20);
            catraca.registrarEntrada(cli8);
            valCapacidade.setLotacaoAtual(19);
            catraca.registrarEntrada(cli8);
            estoque.removerProduto(pCoqueteleira);
            extratoGeral.registrarVendaProduto(cli8, pCoqueteleira, hoje);
            catraca.registrarSaida(cli8);
            System.out.println(extratoGeral.gerarExtratoCliente(cli8));

            // Cliente 9
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
            extratoGeral.registrarVendaProduto(cli9, pIsotonico, hoje);
            catraca.registrarSaida(cli9);
            System.out.println(extratoGeral.gerarExtratoCliente(cli9));
            cli9.setSituation(false);
            System.out.println("Matrícula de " + cli9.getName() + " cancelada ao fim do atendimento (Ativo: " + cli9.getSituation() + ").");

            // Cliente 10
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
            System.out.println(extratoGeral.gerarExtratoCliente(cli10));

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

            PersistenciaJSON.salvarClientes(clientesAtendidos, "dados/clientes_q21.json");
            PersistenciaJSON.salvarAgendamentos(agendamentosGeral, "dados/agendamentos_q21.json");
            PersistenciaJSON.salvarRegistrosAcesso(catraca.getHistoricoAcessos(), "dados/acessos_q21.json");
            PersistenciaJSON.salvarExtrato(extratoGeral, "dados/extrato_q21.json");
            PersistenciaJSON.salvarExtratoCliente(cli1, extratoGeral, "dados/conta_cliente1_q21.json");
            PersistenciaJSON.salvarEstoque(estoque, "dados/estoque_q21.json");
            PersistenciaJSON.salvarUsuarios(Arrays.asList(colab, admin), "dados/usuarios_q21.json");
            System.out.println("\n[Persistência JSON] Estado completo dos 10 cenários salvo em dados/*.json.");
            System.out.println("Estoque restante após vendas: " + estoque.getProdutos().size() + " itens.");
        }
    }
}
