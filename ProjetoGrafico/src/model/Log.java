package model;

/**
 * Classe que representa um registo de log (auditoria) no sistema.
 * Cada log regista uma ação realizada por um utilizador num determinado
 * momento.
 *
 * @author Hugo
 * @version 1.0
 */
public class Log {

    private int idLog;
    private String data;
    private String hora;
    private String utilizador;
    private String acao;

    /**
     * Constrói um novo Log com todos os atributos.
     *
     * @param aIdLog      identificador único do registo de log
     * @param aData       data da ação (formato YYYY-MM-DD)
     * @param aHora       hora da ação (formato HH:mm:ss)
     * @param aUtilizador username do utilizador que realizou a ação
     * @param aAcao       descrição textual da ação realizada
     */
    public Log(int aIdLog, String aData, String aHora, String aUtilizador, String aAcao) {
        idLog = aIdLog;
        data = aData;
        hora = aHora;
        utilizador = aUtilizador;
        acao = aAcao;
    }

    /**
     * Define o identificador do log.
     *
     * @param aIdLog novo identificador
     */
    public void setIdLog(int aIdLog) {
        idLog = aIdLog;
    }

    /**
     * Define a data do log.
     *
     * @param aData nova data
     */
    public void setData(String aData) {
        data = aData;
    }

    /**
     * Define a hora do log.
     *
     * @param aHora nova hora
     */
    public void setHora(String aHora) {
        hora = aHora;
    }

    /**
     * Define o username do utilizador associado ao log.
     *
     * @param aUtilizador novo username
     */
    public void setUtilizador(String aUtilizador) {
        utilizador = aUtilizador;
    }

    /**
     * Define a descrição da ação registada.
     *
     * @param aAcao nova descrição da ação
     */
    public void setAcao(String aAcao) {
        acao = aAcao;
    }

    /**
     * Obtém o identificador do log.
     *
     * @return identificador único do log
     */
    public int getIdLog() {
        return idLog;
    }

    /**
     * Obtém a data do log.
     *
     * @return data da ação
     */
    public String getData() {
        return data;
    }

    /**
     * Obtém a hora do log.
     *
     * @return hora da ação
     */
    public String getHora() {
        return hora;
    }

    /**
     * Obtém o username do utilizador que realizou a ação.
     *
     * @return username do utilizador
     */
    public String getUtilizador() {
        return utilizador;
    }

    /**
     * Obtém a descrição da ação registada.
     *
     * @return descrição da ação
     */
    public String getAcao() {
        return acao;
    }

}