package model;

import Enums.CategoriaNotificacao;
import Enums.EstadoNotificacao;

/**
 * Classe que representa uma notificação enviada a um utilizador do sistema.
 * As notificações são geradas automaticamente para informar sobre eventos
 * como novos registos, alterações de stock, prazos e reparações.
 *
 * @author Santiago
 * @version 1.0
 */
public class Notificacao {

    private int idNotificacao;
    private int idUtilizador;
    private String mensagem;
    private String dataCriacao;
    private EstadoNotificacao estado;
    private CategoriaNotificacao categoria;

    /**
     * Constrói uma nova Notificação com todos os atributos.
     *
     * @param aIdNotificacao identificador único da notificação
     * @param aIdUtilizador  identificador do utilizador destinatário
     * @param aMensagem      conteúdo textual da notificação
     * @param aDataCriacao   data e hora de criação (formato YYYY-MM-DD HH:mm:ss)
     * @param aEstado        estado da notificação (POR_LER ou LIDA)
     * @param aCategoria     categoria da notificação
     */
    public Notificacao(int aIdNotificacao, int aIdUtilizador, String aMensagem, String aDataCriacao,
            EstadoNotificacao aEstado, CategoriaNotificacao aCategoria) {
        idNotificacao = aIdNotificacao;
        idUtilizador = aIdUtilizador;
        mensagem = aMensagem;
        dataCriacao = aDataCriacao;
        estado = aEstado;
        categoria = aCategoria;
    }

    /**
     * Define o identificador da notificação.
     *
     * @param aIdNotificacao novo identificador
     */
    public void setIdNotificacao(int aIdNotificacao) {
        idNotificacao = aIdNotificacao;
    }

    /**
     * Define o identificador do utilizador destinatário.
     *
     * @param aIdUtilizador identificador do destinatário
     */
    public void setIdUtilizador(int aIdUtilizador) {
        idUtilizador = aIdUtilizador;
    }

    /**
     * Define a mensagem da notificação.
     *
     * @param aMensagem nova mensagem
     */
    public void setMensagem(String aMensagem) {
        mensagem = aMensagem;
    }

    /**
     * Define a data de criação da notificação.
     *
     * @param aDataCriacao nova data de criação
     */
    public void setDataCriacao(String aDataCriacao) {
        dataCriacao = aDataCriacao;
    }

    /**
     * Define o estado da notificação.
     *
     * @param aEstado novo estado (POR_LER ou LIDA)
     */
    public void setEstado(EstadoNotificacao aEstado) {
        estado = aEstado;
    }

    /**
     * Define a categoria da notificação.
     *
     * @param aCategoria nova categoria
     */
    public void setCategoria(CategoriaNotificacao aCategoria) {
        categoria = aCategoria;
    }

    /**
     * Obtém o identificador da notificação.
     *
     * @return identificador único da notificação
     */
    public int getIdNotificacao() {
        return idNotificacao;
    }

    /**
     * Obtém o identificador do utilizador destinatário.
     *
     * @return identificador do destinatário
     */
    public int getIdUtilizador() {
        return idUtilizador;
    }

    /**
     * Obtém a mensagem da notificação.
     *
     * @return conteúdo textual da notificação
     */
    public String getMensagem() {
        return mensagem;
    }

    /**
     * Obtém a data de criação da notificação.
     *
     * @return data e hora de criação
     */
    public String getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Obtém o estado da notificação.
     *
     * @return estado atual (POR_LER ou LIDA)
     */
    public EstadoNotificacao getEstado() {
        return estado;
    }

    /**
     * Obtém a categoria da notificação.
     *
     * @return categoria da notificação
     */
    public CategoriaNotificacao getCategoria() {
        return categoria;
    }

}