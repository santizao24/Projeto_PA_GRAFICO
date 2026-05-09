package controlador;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import model.Log;
import repositorio.LogDAO;

/**
 * Controlador responsável pela lógica de negócio relacionada com o registo de
 * logs (auditoria).
 * Gera automaticamente a data e hora atuais para cada ação registada.
 *
 * @author Hugo
 * @version 1.0
 */
public class ControladorLog {

    private LogDAO lDao = new LogDAO();

    /**
     * Regista uma ação realizada por um utilizador no sistema de auditoria.
     * A data e hora do registo são obtidas automaticamente no momento da chamada.
     *
     * @param username username do utilizador que realizou a ação
     * @param acao     descrição textual da ação realizada
     */
    public void registarAcao(String username, String acao) {
        String dataAtual = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String horaAtual = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Log novoLog = new Log(0, dataAtual, horaAtual, username, acao);
        lDao.inserirLog(novoLog);
    }

    /**
     * Lista todos os registos de log do sistema, ordenados por data e hora
     * decrescente.
     *
     * @return lista de todos os logs
     */
    public ArrayList<Log> listarTodosLogs() {
        return lDao.listarTodosLogs();
    }

    /**
     * Pesquisa registos de log por username do utilizador (pesquisa parcial).
     *
     * @param username username ou parte do username a pesquisar
     * @return lista de logs que correspondem ao critério
     */
    public ArrayList<Log> pesquisarPorUtilizador(String username) {
        return lDao.pesquisarLogsPorUtilizador(username);
    }
}