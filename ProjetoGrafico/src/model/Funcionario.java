package model;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;

/**
 * Classe que representa um funcionário do sistema de gestão de oficina.
 * Estende {@link Utilizador} com atributos específicos do perfil de
 * funcionário,
 * como NIF, telefone, morada, nível de especialização e data de início de
 * atividade.
 *
 * @author Hugo
 * @version 1.0
 */
public class Funcionario extends Utilizador {

    private String nif;
    private String telefone;
    private String morada;
    private int especializacao;
    private String dataInicio;

    /**
     * Constrói um novo Funcionário com todos os atributos.
     *
     * @param aIdUtilizador   identificador único do utilizador
     * @param aLogin          nome de utilizador (username) para autenticação
     * @param aPassword       palavra-passe do utilizador
     * @param aNome           nome completo do utilizador
     * @param aEmail          endereço de email do utilizador
     * @param aTipo           tipo do utilizador
     * @param aEstado         estado atual da conta
     * @param aNif            número de identificação fiscal (9 dígitos)
     * @param aTelefone       número de telefone (9 dígitos)
     * @param aMorada         morada do funcionário
     * @param aEspecializacao nível de especialização (1 a 5)
     * @param aDataInicio     data de início de atividade (formato YYYY-MM-DD)
     */
    public Funcionario(int aIdUtilizador, String aLogin, String aPassword, String aNome, String aEmail,
            TipoUtilizador aTipo, EstadoUtilizador aEstado,
            String aNif, String aTelefone, String aMorada, int aEspecializacao, String aDataInicio) {
        super(aIdUtilizador, aLogin, aPassword, aNome, aEmail, aTipo, aEstado);
        nif = aNif;
        telefone = aTelefone;
        morada = aMorada;
        especializacao = aEspecializacao;
        dataInicio = aDataInicio;
    }

    /**
     * Define o NIF do funcionário.
     *
     * @param aNif novo NIF
     */
    public void setNif(String aNif) {
        nif = aNif;
    }

    /**
     * Define o número de telefone do funcionário.
     *
     * @param aTelefone novo telefone
     */
    public void setTelefone(String aTelefone) {
        telefone = aTelefone;
    }

    /**
     * Define a morada do funcionário.
     *
     * @param aMorada nova morada
     */
    public void setMorada(String aMorada) {
        morada = aMorada;
    }

    /**
     * Define o nível de especialização do funcionário.
     *
     * @param aEspecializacao novo nível de especialização (1 a 5)
     */
    public void setEspecializacao(int aEspecializacao) {
        especializacao = aEspecializacao;
    }

    /**
     * Define a data de início de atividade do funcionário.
     *
     * @param aDataInicio nova data de início (formato YYYY-MM-DD)
     */
    public void setDataInicio(String aDataInicio) {
        dataInicio = aDataInicio;
    }

    /**
     * Obtém o NIF do funcionário.
     *
     * @return número de identificação fiscal
     */
    public String getNif() {
        return nif;
    }

    /**
     * Obtém o número de telefone do funcionário.
     *
     * @return número de telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Obtém a morada do funcionário.
     *
     * @return morada
     */
    public String getMorada() {
        return morada;
    }

    /**
     * Obtém o nível de especialização do funcionário.
     *
     * @return nível de especialização (1 a 5)
     */
    public int getEspecializacao() {
        return especializacao;
    }

    /**
     * Obtém a data de início de atividade do funcionário.
     *
     * @return data de início (formato YYYY-MM-DD)
     */
    public String getDataInicio() {
        return dataInicio;
    }
}