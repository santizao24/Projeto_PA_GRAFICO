package model;

/**
 * Classe que representa um equipamento registado no sistema de gestão de oficina.
 * Cada equipamento pertence a um cliente e pode ser alvo de pedidos de reparação.
 *
 * @author Santiago
 * @version 1.0
 */
public class Equipamento {

    private int idEquipamento;
    private int idUtilizador;
    private String marca;
    private String modelo;
    private int sku;
    private String dataFabrico;
    private String lote;
    private String observacoes;

    /**
     * Constrói um novo Equipamento com todos os atributos.
     *
     * @param aIdEquipamento identificador único do equipamento
     * @param aIdUtilizador  identificador do cliente proprietário
     * @param aMarca         marca do equipamento
     * @param aModelo        código do modelo do equipamento
     * @param aSku           código SKU único do equipamento
     * @param aDataFabrico   data de fabrico (formato YYYY-MM-DD)
     * @param aLote          identificação do lote de fabrico
     */
    public Equipamento(int aIdEquipamento, int aIdUtilizador, String aMarca, String aModelo, int aSku,
            String aDataFabrico, String aLote) {
        idEquipamento = aIdEquipamento;
        idUtilizador = aIdUtilizador;
        marca = aMarca;
        modelo = aModelo;
        sku = aSku;
        dataFabrico = aDataFabrico;
        lote = aLote;
        observacoes = null;
    }

    /**
     * Define o identificador do equipamento.
     *
     * @param aIdEquipamento novo identificador
     */
    public void setIdEquipamento(int aIdEquipamento) {
        idEquipamento = aIdEquipamento;
    }

    /**
     * Define o identificador do cliente proprietário.
     *
     * @param aIdUtilizador identificador do cliente
     */
    public void setIdUtilizador(int aIdUtilizador) {
        idUtilizador = aIdUtilizador;
    }

    /**
     * Define a marca do equipamento.
     *
     * @param aMarca nova marca
     */
    public void setMarca(String aMarca) {
        marca = aMarca;
    }

    /**
     * Define o código do modelo do equipamento.
     *
     * @param aModelo novo código de modelo
     */
    public void setModelo(String aModelo) {
        modelo = aModelo;
    }

    /**
     * Define o código SKU do equipamento.
     *
     * @param aSku novo código SKU
     */
    public void setSku(int aSku) {
        sku = aSku;
    }

    /**
     * Define a data de fabrico do equipamento.
     *
     * @param aDataFabrico nova data de fabrico
     */
    public void setDataFabrico(String aDataFabrico) {
        dataFabrico = aDataFabrico;
    }

    /**
     * Define o lote de fabrico do equipamento.
     *
     * @param aLote novo lote
     */
    public void setLote(String aLote) {
        lote = aLote;
    }

    /**
     * Obtém o identificador do equipamento.
     *
     * @return identificador único do equipamento
     */
    public int getIdEquipamento() {
        return idEquipamento;
    }

    /**
     * Obtém o identificador do cliente proprietário.
     *
     * @return identificador do cliente
     */
    public int getIdUtilizador() {
        return idUtilizador;
    }

    /**
     * Obtém a marca do equipamento.
     *
     * @return marca do equipamento
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Obtém o código do modelo do equipamento.
     *
     * @return código do modelo
     */
    public String getModelo() {
        return modelo;
    }

    /**
     * Obtém o código SKU do equipamento.
     *
     * @return código SKU
     */
    public int getSku() {
        return sku;
    }

    /**
     * Obtém a data de fabrico do equipamento.
     *
     * @return data de fabrico
     */
    public String getDataFabrico() {
        return dataFabrico;
    }

    /**
     * Obtém o lote de fabrico do equipamento.
     *
     * @return lote de fabrico
     */
    public String getLote() {
        return lote;
    }

    /**
     * Define as observações do equipamento (R4).
     *
     * @param aObservacoes texto das observações
     */
    public void setObservacoes(String aObservacoes) {
        observacoes = aObservacoes;
    }

    /**
     * Obtém as observações do equipamento (R4).
     *
     * @return observações (pode ser null)
     */
    public String getObservacoes() {
        return observacoes;
    }
}