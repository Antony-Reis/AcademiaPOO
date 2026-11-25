package br.com.academiapoo.config;

import br.com.academiapoo.model.GymRoom;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Classe central com as salas fixas e contadores estaticos da academia.
 */
public class Sistema {

    /** Sala fixa de Spinning. */
    public static final GymRoom SALA_SPINNING = GymRoom.SPINNING;

    /** Sala fixa de Musculacao. */
    public static final GymRoom SALA_MUSCULACAO = GymRoom.MUSCULACAO;

    /** Sala fixa de Fit Dance. */
    public static final GymRoom SALA_FIT_DANCE = GymRoom.FIT_DANCE;

    /** Sala fixa de Pilates. */
    public static final GymRoom SALA_PILATES = GymRoom.PILATES;

    /** Lista imutavel com as 4 salas da academia. */
    private static final List<GymRoom> SALAS = Collections.unmodifiableList(
            Arrays.asList(SALA_SPINNING, SALA_MUSCULACAO, SALA_FIT_DANCE, SALA_PILATES)
    );

    /*
     * Estrategia 1 (private static com get/set):
     * - Vantagens: protege o contador com encapsulamento total, da pra validar no setter
     *   pra ninguem colocar numero negativo e facilita manutencao depois.
     * - Desvantagens: precisa escrever mais codigo (getter e setter) pra acessar.
     */
    /** Contador privado de clientes instanciados. */
    private static int totalClientesPrivado = 0;

    /*
     * Estrategia 2 (protected static):
     * - Vantagens: acesso direto e mais curto dentro do pacote e subclasses, sem chamar getter/setter.
     * - Desvantagens: quebra parte do encapsulamento, porque qualquer classe do pacote pode
     *   alterar o valor direto (ate colocar numero negativo) sem passar por validacao.
     */
    /** Contador protegido de clientes instanciados. */
    protected static int totalClientesProtegido = 0;

    /** Contador privado de produtos criados na lojinha. */
    private static int totalProdutosCriados = 0;

    /** Construtor padrao. */
    public Sistema() {
        super();
    }

    /** Retorna a lista estatica das 4 salas. */
    public static List<GymRoom> getSalas() {
        return SALAS;
    }

    /** Incrementa os contadores quando um novo cliente e criado. */
    public static void registrarNovoCliente() {
        totalClientesPrivado++;
        totalClientesProtegido++;
    }

    /** Retorna o total de clientes (private static). */
    public static int getTotalClientesPrivado() {
        return totalClientesPrivado;
    }

    /** Atualiza o contador privado de clientes com validacao. */
    public static void setTotalClientesPrivado(int total) {
        if (total < 0) {
            throw new IllegalArgumentException("O total de clientes não pode ser negativo.");
        }
        totalClientesPrivado = total;
    }

    /** Retorna o total de clientes (protected static). */
    public static int getTotalClientesProtegido() {
        return totalClientesProtegido;
    }

    /** Incrementa o contador de produtos criados. */
    public static void registrarNovoProduto() {
        totalProdutosCriados++;
    }

    /** Retorna o total de produtos criados. */
    public static int getTotalProdutosCriados() {
        return totalProdutosCriados;
    }

    /** Retorna quantas instancias de Cliente e Produto foram criadas juntas. */
    public static String getResumoInstanciasClientesEProdutos() {
        int totalJuntos = totalClientesPrivado + totalProdutosCriados;
        return String.format(
                "Clientes criados: %d | Produtos criados: %d | Total juntos: %d",
                totalClientesPrivado, totalProdutosCriados, totalJuntos
        );
    }

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
