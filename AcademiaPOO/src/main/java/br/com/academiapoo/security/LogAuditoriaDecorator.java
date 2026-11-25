package br.com.academiapoo.security;

import br.com.academiapoo.model.Usuario;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Decorator que registra data, hora e resultado de cada tentativa de login.
 */
public class LogAuditoriaDecorator extends AutenticadorDecorator {

    private static final DateTimeFormatter FORMATADOR = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /** Lista com o historico de logs gravados. */
    // nao tem setter porque o historico e gerado automaticamente no autenticar()
    private List<String> logsAuditoria;

    /** Construtor do decorator de auditoria. */
    public LogAuditoriaDecorator(Autenticador autenticadorDecorado) {
        super(autenticadorDecorado);
        this.logsAuditoria = new ArrayList<>();
    }

    @Override
    public Usuario autenticar(String login, String senha) {
        Usuario temp = super.autenticar(login, senha);
        String status;
        if (temp != null) {
            status = "SUCESSO";
        } else {
            status = "FALHA";
        }

        String dataAgora = LocalDateTime.now().format(FORMATADOR);
        String registro = String.format("[Auditoria %s] Login: '%s' | Status: %s",
                dataAgora, login, status);
        // System.out.println("debug: " + registro);
        this.logsAuditoria.add(registro);
        System.out.println(registro);
        return temp;
    }

    /** Retorna a lista de logs (somente leitura). */
    public List<String> getLogsAuditoria() {
        return Collections.unmodifiableList(this.logsAuditoria);
    }

    @Override
    public String toString() {
        return String.format("LogAuditoriaDecorator [Logs: %d] -> %s",
                this.logsAuditoria.size(), super.toString());
    }
}
