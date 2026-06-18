package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.sql.*;
import java.util.Properties;

/**
 * Classe utilitária responsável pela gestão de conexões à base de dados MySQL.
 * Lê as configurações de acesso a partir do ficheiro {@code configbd.txt}.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ConexaoBD {

    /**
     * Obtém uma nova conexão à base de dados a partir das configurações
     * armazenadas no ficheiro {@code configbd.txt}.
     *
     * @return objeto {@link Connection} representando a conexão ativa
     * @throws SQLException se as configurações não existirem ou a conexão falhar
     */
    public static Connection obterConexao() throws SQLException {
        Properties prop = new Properties();
        String url = "";
        String user = "";
        String password = "";

        try (FileInputStream fis = new FileInputStream("configbd.txt")) {
            prop.load(fis);
            url = prop.getProperty("url");
            user = prop.getProperty("user");
            password = prop.getProperty("password");
        } catch (Exception e) {
            throw new SQLException("Configuração da Base de Dados inexistente.");
        }

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Guarda as configurações de acesso no ficheiro {@code configbd.txt}.
     *
     * @param url URL da base de dados
     * @param user username
     * @param password password
     * @throws Exception em caso de erro na gravação do ficheiro
     */
    public static void guardarConfiguracao(String url, String user, String password) throws Exception {
        Properties prop = new Properties();
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream("configbd.txt");
            prop.setProperty("url", url);
            prop.setProperty("user", user);
            prop.setProperty("password", password);
            prop.store(fos, "Configuracoes da Base de Dados");
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * Fecha uma conexão à base de dados de forma segura, ignorando exceções.
     *
     * @param conn conexão a fechar (pode ser null)
     */
    public static void fecharBd(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }
}