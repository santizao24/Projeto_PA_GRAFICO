package model;

/**
 * Classe que representa um teste de operacionalidade realizado durante uma
 * reparação.
 * Cada teste regista a designação, descrição do resultado e a data de
 * realização.
 *
 * @author Hugo
 * @version 1.0
 */
public class TesteOperacionalidade {

    private int idTeste;
    private int idReparacao;
    private String designacao;
    private String descricao;
    private String dataRealizacao;

    /**
     * Constrói um novo TesteOperacionalidade com todos os atributos.
     *
     * @param aIdTeste     identificador único do teste
     * @param aIdReparacao identificador da reparação associada
     * @param aDesignacao  designação do teste (ex: Teste de Bateria)
     * @param aDescricao   descrição ou resultado do teste
     * @param aData        data de realização do teste
     */
    public TesteOperacionalidade(int aIdTeste, int aIdReparacao, String aDesignacao,
            String aDescricao,
            String aData) {
        idTeste = aIdTeste;
        idReparacao = aIdReparacao;
        designacao = aDesignacao;
        descricao = aDescricao;
        dataRealizacao = aData;
    }

    /**
     * Obtém o identificador da reparação associada ao teste.
     *
     * @return identificador da reparação
     */
    public int getIdReparacao() {
        return idReparacao;
    }

    /**
     * Obtém a designação do teste.
     *
     * @return designação do teste
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Obtém a descrição ou resultado do teste.
     *
     * @return descrição do teste
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Obtém a data de realização do teste.
     *
     * @return data de realização
     */
    public String getDataRealizacao() {
        return dataRealizacao;
    }

}