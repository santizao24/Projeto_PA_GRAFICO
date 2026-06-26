package model;

import Enums.EstadoReparacao;

/**
 * Classe que representa um processo de reparação de um equipamento.
 * Contém toda a informação relevante do ciclo de vida de uma reparação,
 * desde a sua criação até à conclusão e arquivamento.
 *
 * @author Santiago
 * @version 1.0
 */
public class Reparacao {

    private int idReparacao;
    private String numReparacao;
    private int idEquipamento;
    private int idUtilizador;
    private String dataCriacao;
    private String dataInicio;
    private String dataFim;
    private int tempoDecorrido;
    private double custo;
    private EstadoReparacao estado;
    private String observacoes;

    /**
     * Constrói uma nova Reparação com todos os atributos.
     *
     * @param aIdReparacao   identificador único da reparação
     * @param aNumReparacao  número de referência da reparação
     * @param aIdEquipamento identificador do equipamento a reparar
     * @param aIdUtilizador  identificador do funcionário atribuído (0 se não
     *                       atribuído)
     * @param aDataCriacao   data de criação do pedido
     * @param aDataInicio    data de início da reparação (pode ser null)
     * @param aDataFim       data de conclusão da reparação (pode ser null)
     * @param aTempo         tempo decorrido da reparação em minutos
     * @param aCusto         custo final da reparação em euros
     * @param aEstado        estado atual do processo
     * @param aObservacoes   observações finais sobre a reparação (pode ser null)
     */
    public Reparacao(int aIdReparacao, String aNumReparacao, int aIdEquipamento, int aIdUtilizador, String aDataCriacao,
            String aDataInicio, String aDataFim,
            int aTempo, double aCusto, EstadoReparacao aEstado, String aObservacoes) {
        idReparacao = aIdReparacao;
        numReparacao = aNumReparacao;
        idEquipamento = aIdEquipamento;
        idUtilizador = aIdUtilizador;
        dataCriacao = aDataCriacao;
        dataInicio = aDataInicio;
        dataFim = aDataFim;
        tempoDecorrido = aTempo;
        custo = aCusto;
        estado = aEstado;
        observacoes = aObservacoes;
    }

    /**
     * Define o número de referência da reparação.
     *
     * @param aNumReparacao novo número de reparação
     */
    public void setNumReparacao(String aNumReparacao) {
        numReparacao = aNumReparacao;
    }

    /**
     * Define o estado do processo de reparação.
     *
     * @param aEstado novo estado
     */
    public void setEstado(EstadoReparacao aEstado) {
        estado = aEstado;
    }

    /**
     * Define o custo final da reparação.
     *
     * @param aCusto novo custo em euros
     */
    public void setCusto(double aCusto) {
        custo = aCusto;
    }

    /**
     * Define o identificador da reparação.
     *
     * @param aIdReparacao novo identificador
     */
    public void setIdReparacao(int aIdReparacao) {
        idReparacao = aIdReparacao;
    }

    /**
     * Define a data de início da reparação.
     *
     * @param aDataInicio nova data de início
     */
    public void setDataInicio(String aDataInicio) {
        dataInicio = aDataInicio;
    }

    /**
     * Define a data de conclusão da reparação.
     *
     * @param aDataFim nova data de conclusão
     */
    public void setDataFim(String aDataFim) {
        dataFim = aDataFim;
    }

    /**
     * Define as observações finais da reparação.
     *
     * @param aObservacoes novas observações
     */
    public void setObservacoes(String aObservacoes) {
        observacoes = aObservacoes;
    }

    /**
     * Define o tempo decorrido da reparação.
     *
     * @param aTempo tempo em minutos
     */
    public void setTempoDecorrido(int aTempo) {
        tempoDecorrido = aTempo;
    }

    /**
     * Obtém o número de referência da reparação.
     *
     * @return número de reparação
     */
    public String getNumReparacao() {
        return numReparacao;
    }

    /**
     * Obtém o identificador da reparação.
     *
     * @return identificador único da reparação
     */
    public int getIdReparacao() {
        return idReparacao;
    }

    /**
     * Obtém o identificador do equipamento associado.
     *
     * @return identificador do equipamento
     */
    public int getIdEquipamento() {
        return idEquipamento;
    }

    /**
     * Obtém o identificador do funcionário atribuído à reparação.
     *
     * @return identificador do funcionário (0 se não atribuído)
     */
    public int getIdUtilizador() {
        return idUtilizador;
    }

    /**
     * Obtém o estado atual do processo de reparação.
     *
     * @return estado da reparação
     */
    public EstadoReparacao getEstado() {
        return estado;
    }

    /**
     * Obtém o custo final da reparação.
     *
     * @return custo em euros
     */
    public double getCusto() {
        return custo;
    }

    /**
     * Obtém a data de criação do pedido de reparação.
     *
     * @return data de criação
     */
    public String getDataCriacao() {
        return dataCriacao;
    }

    /**
     * Obtém a data de início da reparação.
     *
     * @return data de início (pode ser null)
     */
    public String getDataInicio() {
        return dataInicio;
    }

    /**
     * Obtém a data de conclusão da reparação.
     *
     * @return data de conclusão (pode ser null)
     */
    public String getDataFim() {
        return dataFim;
    }

    /**
     * Obtém o tempo decorrido da reparação.
     *
     * @return tempo em minutos
     */
    public int getTempoDecorrido() {
        return tempoDecorrido;
    }

    /**
     * Obtém as observações finais da reparação.
     *
     * @return observações (pode ser null)
     */
    public String getObservacoes() {
        return observacoes;
    }

}