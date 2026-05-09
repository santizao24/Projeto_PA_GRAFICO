package model;

/**
 * Classe que representa uma peça disponível no armazém da oficina.
 * Cada peça possui um código interno único, designação, fabricante e quantidade
 * em stock.
 *
 * @author Hugo
 * @version 1.0
 */
public class Peca {

    private String codigoInterno;
    private String designacao;
    private String fabricante;
    private int quantidade;

    /**
     * Constrói uma nova Peça com todos os atributos.
     *
     * @param aCodigoInterno código interno único da peça
     * @param aDesignacao    designação ou nome da peça
     * @param aFabricante    nome do fabricante da peça
     * @param aQuantidade    quantidade em stock no armazém
     */
    public Peca(String aCodigoInterno, String aDesignacao, String aFabricante, int aQuantidade) {
        codigoInterno = aCodigoInterno;
        designacao = aDesignacao;
        fabricante = aFabricante;
        quantidade = aQuantidade;
    }

    /**
     * Define o código interno da peça.
     *
     * @param aCodigoInterno novo código interno
     */
    public void setCodigoInterno(String aCodigoInterno) {
        codigoInterno = aCodigoInterno;
    }

    /**
     * Define a quantidade em stock da peça.
     *
     * @param aQuantidade nova quantidade
     */
    public void setQuantidade(int aQuantidade) {
        quantidade = aQuantidade;
    }

    /**
     * Obtém o código interno da peça.
     *
     * @return código interno único
     */
    public String getCodigoInterno() {
        return codigoInterno;
    }

    /**
     * Obtém a quantidade em stock da peça.
     *
     * @return quantidade em stock
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Obtém a designação da peça.
     *
     * @return designação ou nome da peça
     */
    public String getDesignacao() {
        return designacao;
    }

    /**
     * Obtém o nome do fabricante da peça.
     *
     * @return nome do fabricante
     */
    public String getFabricante() {
        return fabricante;
    }
}