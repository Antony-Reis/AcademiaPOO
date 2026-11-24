package br.com.academiapoo.config;

import br.com.academiapoo.model.GymRoom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe central de estado global (memória de curto prazo) da Academia.
 * Armazena estaticamente as 4 salas disponíveis, além de manter contadores
 * estáticos de instâncias criadas de {@link Client} e {@link Produto}.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class Sistema {

    /** Sala estática de Spinning. */
    public static final GymRoom SALA_SPINNING = GymRoom.SPINNING;

    /** Sala estática de Musculação. */
    public static final GymRoom SALA_MUSCULACAO = GymRoom.MUSCULACAO;

    /** Sala estática de Fit Dance. */
    public static final GymRoom SALA_FIT_DANCE = GymRoom.FIT_DANCE;

    /** Sala estática de Pilates. */
    public static final GymRoom SALA_PILATES = GymRoom.PILATES;

    /** Lista estática consolidada com as 4 salas da academia. */
    private static final List<GymRoom> SALAS = Collections.unmodifiableList(
            Arrays.asList(SALA_SPINNING, SALA_MUSCULACAO, SALA_FIT_DANCE, SALA_PILATES)
    );

    /*
     * =========================================================================
     * ESTRATÉGIA 1: ATRIBUTO ESTÁTICO PRIVATE (COM GETTER E SETTER ESTÁTICOS)
     * =========================================================================
     * Vantagens:
     *  - Garante encapsulamento completo: nenhuma classe externa altera o valor
     *    diretamente sem passar pelo setter.
     *  - Permite aplicar validações no setTotalClientesPrivado (ex: impedir valores negativos)
     *    e facilita futuras mudanças internas sem quebrar quem consome a classe.
     * Desvantagens:
     *  - Exige mais código (métodos getter e setter) para leitura e escrita.
     */
    private static int totalClientesPrivado = 0;

    /*
     * =========================================================================
     * ESTRATÉGIA 2: ATRIBUTO ESTÁTICO PROTECTED
     * =========================================================================
     * Vantagens:
     *  - Acesso direto e menos verboso para subclasses e para classes do mesmo pacote,
     *    dispensando chamadas de getter/setter internamente.
     * Desvantagens:
     *  - Quebra parcialmente o encapsulamento: qualquer classe do mesmo pacote ou
     *    subclasse pode modificar o contador diretamente para um valor inválido
     *    (ex: número negativo), sem passar por validação centralizada.
     */
    protected static int totalClientesProtegido = 0;

    /** Contador estático do total de produtos criados para a loja da academia. */
    private static int totalProdutosCriados = 0;

    /**
     * Construtor padrão da classe Sistema.
     */
    public Sistema() {
        super();
    }

    /**
     * Retorna a lista estática e imutável das 4 salas da academia.
     *
     * @return Lista contendo Spinning, Musculação, Fit Dance e Pilates.
     */
    public static List<GymRoom> getSalas() {
        return SALAS;
    }

    /**
     * Incrementa ambos os contadores estáticos de clientes sempre que um novo
     * {@link Client} é instanciado.
     */
    public static void registrarNovoCliente() {
        totalClientesPrivado++;
        totalClientesProtegido++;
    }

    /**
     * Retorna o total de clientes contabilizado pelo atributo {@code private static}.
     *
     * @return Quantidade de instâncias de {@link Client} criadas.
     */
    public static int getTotalClientesPrivado() {
        return totalClientesPrivado;
    }

    /**
     * Define manualmente o contador {@code private static} de clientes com validação.
     *
     * @param total Novo valor do contador (deve ser maior ou igual a zero).
     */
    public static void setTotalClientesPrivado(int total) {
        if (total < 0) {
            throw new IllegalArgumentException("O total de clientes não pode ser negativo.");
        }
        totalClientesPrivado = total;
    }

    /**
     * Retorna o total de clientes contabilizado pelo atributo {@code protected static}.
     *
     * @return Quantidade de instâncias de {@link Client} criadas.
     */
    public static int getTotalClientesProtegido() {
        return totalClientesProtegido;
    }

    /**
     * Incrementa o contador estático de produtos criados para a lojinha.
     */
    public static void registrarNovoProduto() {
        totalProdutosCriados++;
    }

    /**
     * Retorna o total de instâncias de {@link Produto} criadas para a loja.
     *
     * @return Total de produtos criados.
     */
    public static int getTotalProdutosCriados() {
        return totalProdutosCriados;
    }

    /**
     * Retorna um resumo textual do estado global armazenado na classe {@code Sistema}.
     *
     * @return String com as salas, total de clientes (private e protected) e total de produtos.
     */
    @Override
    public String toString() {
        return String.format(
                "Sistema [Salas: %d | Clientes (private): %d | Clientes (protected): %d | Produtos criados: %d]",
                SALAS.size(),
                totalClientesPrivado,
                totalClientesProtegido,
                totalProdutosCriados
        );
    }
}
