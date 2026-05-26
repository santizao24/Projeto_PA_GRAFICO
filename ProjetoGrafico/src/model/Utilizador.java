package model;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;

/**
 * Classe que representa um utilizador genérico do sistema de gestão de oficina.
 * Serve como classe base para {@link Cliente} e {@link Funcionario}.
 *
 * @author Santiago
 * @version 1.0
 */
public class Utilizador {

    private int idUtilizador;
    private String login;
    private String password;
    private String nome;
    private String email;
    private TipoUtilizador tipo;
    private EstadoUtilizador estado;
    private String fotoPath;

    /**
     * Constrói um novo Utilizador com todos os atributos.
     *
     * @param aIdUtilizador identificador único do utilizador
     * @param aLogin        nome de utilizador (username) para autenticação
     * @param aPassword     palavra-passe do utilizador
     * @param aNome         nome completo do utilizador
     * @param aEmail        endereço de email do utilizador
     * @param aTipo         tipo do utilizador (GESTOR, FUNCIONARIO, CLIENTE)
     * @param aEstado       estado atual da conta do utilizador
     */
    public Utilizador(int aIdUtilizador, String aLogin, String aPassword, String aNome, String aEmail,
            TipoUtilizador aTipo, EstadoUtilizador aEstado) {
        idUtilizador = aIdUtilizador;
        login = aLogin;
        password = aPassword;
        nome = aNome;
        email = aEmail;
        tipo = aTipo;
        estado = aEstado;
        fotoPath = null;
    }

    /**
     * Define o identificador do utilizador.
     *
     * @param aIdUtilizador novo identificador
     */
    public void setIdUtilizador(int aIdUtilizador) {
        idUtilizador = aIdUtilizador;
    }

    /**
     * Define o nome de utilizador (username).
     *
     * @param aLogin novo username
     */
    public void setLogin(String aLogin) {
        login = aLogin;
    }

    /**
     * Define a palavra-passe do utilizador.
     *
     * @param aPassword nova palavra-passe
     */
    public void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * Define o nome completo do utilizador.
     *
     * @param aNome novo nome
     */
    public void setNome(String aNome) {
        nome = aNome;
    }

    /**
     * Define o endereço de email do utilizador.
     *
     * @param aEmail novo email
     */
    public void setEmail(String aEmail) {
        email = aEmail;
    }

    /**
     * Define o tipo do utilizador.
     *
     * @param aTipo novo tipo
     */
    public void setTipo(TipoUtilizador aTipo) {
        tipo = aTipo;
    }

    /**
     * Define o estado da conta do utilizador.
     *
     * @param aEstado novo estado
     */
    public void setEstado(EstadoUtilizador estado) {
        this.estado = estado;
    }

    /**
     * Obtém o identificador do utilizador.
     *
     * @return identificador único do utilizador
     */
    public int getIdUtilizador() {
        return idUtilizador;
    }

    /**
     * Obtém o nome de utilizador (username).
     *
     * @return username do utilizador
     */
    public String getLogin() {
        return login;
    }

    /**
     * Obtém a palavra-passe do utilizador.
     *
     * @return palavra-passe
     */
    public String getPassword() {
        return password;
    }

    /**
     * Obtém o nome completo do utilizador.
     *
     * @return nome do utilizador
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém o endereço de email do utilizador.
     *
     * @return email do utilizador
     */
    public String getEmail() {
        return email;
    }

    /**
     * Obtém o tipo do utilizador.
     *
     * @return tipo do utilizador
     */
    public TipoUtilizador getTipo() {
        return tipo;
    }

    /**
     * Obtém o estado da conta do utilizador.
     *
     * @return estado atual da conta
     */
    public EstadoUtilizador getEstado() {
        return estado;
    }

    /**
     * Define o caminho da foto de perfil do utilizador (R2).
     *
     * @param aFotoPath caminho relativo para a foto (ex: fotos/user_42.png)
     */
    public void setFotoPath(String aFotoPath) {
        fotoPath = aFotoPath;
    }

    /**
     * Obtém o caminho da foto de perfil do utilizador (R2).
     *
     * @return caminho relativo da foto, ou null se não tiver foto
     */
    public String getFotoPath() {
        return fotoPath;
    }
}