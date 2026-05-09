package model;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;

/**
 * Classe que representa um cliente do sistema de gestão de oficina.
 * Estende {@link Utilizador} com atributos específicos do perfil de cliente,
 * como NIF, telefone, morada, setor de atividade e escalão.
 *
 * @author Santiago
 * @version 1.0
 */
public class Cliente extends Utilizador {

    private String nif;
    private String telefone;
    private String morada;
    private String setorAtividade;
    private String escalao;

    /**
     * Constrói um novo Cliente com todos os atributos.
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
     * @param aMorada         morada do cliente
     * @param aSetorAtividade setor de atividade profissional do cliente
     * @param aEscalao        escalão do cliente (A, B, C ou D)
     */
    public Cliente(int aIdUtilizador, String aLogin, String aPassword, String aNome, String aEmail,
            TipoUtilizador aTipo, EstadoUtilizador aEstado,
            String aNif, String aTelefone, String aMorada, String aSetorAtividade, String aEscalao) {
        super(aIdUtilizador, aLogin, aPassword, aNome, aEmail, aTipo, aEstado);
        nif = aNif;
        telefone = aTelefone;
        morada = aMorada;
        setorAtividade = aSetorAtividade;
        escalao = aEscalao;
    }

    /**
     * Define o NIF do cliente.
     *
     * @param aNif novo NIF
     */
    public void setNif(String aNif) {
        nif = aNif;
    }

    /**
     * Define o número de telefone do cliente.
     *
     * @param aTelefone novo telefone
     */
    public void setTelefone(String aTelefone) {
        telefone = aTelefone;
    }

    /**
     * Define a morada do cliente.
     *
     * @param aMorada nova morada
     */
    public void setMorada(String aMorada) {
        morada = aMorada;
    }

    /**
     * Define o setor de atividade do cliente.
     *
     * @param aSetorAtividade novo setor de atividade
     */
    public void setSetorAtividade(String aSetorAtividade) {
        setorAtividade = aSetorAtividade;
    }

    /**
     * Define o escalão do cliente.
     *
     * @param aEscalao novo escalão (A, B, C ou D)
     */
    public void setEscalao(String aEscalao) {
        escalao = aEscalao;
    }

    /**
     * Obtém o NIF do cliente.
     *
     * @return número de identificação fiscal
     */
    public String getNif() {
        return nif;
    }

    /**
     * Obtém o número de telefone do cliente.
     *
     * @return número de telefone
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Obtém a morada do cliente.
     *
     * @return morada
     */
    public String getMorada() {
        return morada;
    }

    /**
     * Obtém o setor de atividade do cliente.
     *
     * @return setor de atividade
     */
    public String getSetorAtividade() {
        return setorAtividade;
    }

    /**
     * Obtém o escalão do cliente.
     *
     * @return escalão (A, B, C ou D)
     */
    public String getEscalao() {
        return escalao;
    }
}