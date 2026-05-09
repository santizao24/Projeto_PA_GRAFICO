package model;

/**
 * Classe que representa a associação entre uma reparação e uma peça utilizada.
 * Regista a quantidade de uma determinada peça que foi consumida numa
 * reparação.
 *
 * @author Hugo
 * @version 1.0
 */
public class ReparacaoPeca {

    private int idReparacao;
    private String codigoInternoPeca;
    private int quantidadeUsada;

    /**
     * Constrói uma nova associação ReparacaoPeca.
     *
     * @param aIdReparacao   identificador da reparação
     * @param aCodigoInterno código interno da peça utilizada
     * @param aQuantidade    quantidade de peças utilizadas
     */
    public ReparacaoPeca(int aIdReparacao, String aCodigoInterno, int aQuantidade) {
        idReparacao = aIdReparacao;
        codigoInternoPeca = aCodigoInterno;
        quantidadeUsada = aQuantidade;
    }

    /**
     * Obtém o identificador da reparação associada.
     *
     * @return identificador da reparação
     */
    public int getIdReparacao() {
        return idReparacao;
    }

    /**
     * Obtém o código interno da peça utilizada.
     *
     * @return código interno da peça
     */
    public String getCodigoInternoPeca() {
        return codigoInternoPeca;
    }

    /**
     * Obtém a quantidade de peças utilizadas na reparação.
     *
     * @return quantidade utilizada
     */
    public int getQuantidadeUsada() {
        return quantidadeUsada;
    }
}