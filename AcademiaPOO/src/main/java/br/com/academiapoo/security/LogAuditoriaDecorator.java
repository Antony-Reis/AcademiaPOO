package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorador concreto que registra data, hora e status (SUCESSO ou FALHA)
 * de todas as tentativas de autenticação.
 *
 * @author Samuel/Antony
 * @version 1.0
 */
public class LogAuditoriaDecorator extends AutenticadorDecorator {

    /** Formatador de data e hora para os registros de auditoria. */
    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /** Histórico em memória das tentativas de login auditadas. */
    private List<String> logsAuditoria;

    /**
     * Construtor do decorador de log de auditoria.
     *
     * @param autenticadorDecorado O autenticador a ser decorado.
     */
    public LogAuditoriaDecorator(Autenticador autenticadorDecorado) {
        super(autenticadorDecorado);
        this.logsAuditoria = new ArrayList<>();
    }

    /**
     * Executa a autenticação e registra data, hora e resultado da tentativa.
     *
     * @param login O login informado.
     * @param senha A senha informada.
     * @return O {@link Usuario} autenticado ou {@code null}.
     */
    @Override
    public Usuario autenticar(String login, String senha) {
        Usuario resultado = super.autenticar(login, senha);
        String status = (resultado != null) ? "SUCESSO" : "FALHA";
        String registro = String.format("[Auditoria %s] Login: '%s' | Status: %s",
                LocalDateTime.now().format(FORMATADOR),
                login,
                status);
        this.logsAuditoria.add(registro);
        System.out.println(registro);
        return resultado;
    }

    /**
     * Retorna a lista imutável de logs registrados.
     *
     * @return Lista de registros de auditoria.
     */
    public List<String> getLogsAuditoria() {
        return Collections.unmodifiableList(this.logsAuditoria);
    }

    /**
     * Retorna uma representação textual do decorador de auditoria.
     *
     * @return String descrevendo o decorador e o total de logs registrados.
     */
    @Override
    public String toString() {
        return String.format("LogAuditoriaDecorator [Logs: %d] -> %s",
                this.logsAuditoria.size(), super.toString());
    }
}
