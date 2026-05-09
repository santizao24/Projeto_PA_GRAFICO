package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Log;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência
 * de registos de log (auditoria) na base de dados.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class LogDAO {

    /**
     * Insere um novo registo de log na base de dados.
     *
     * @param aLog objeto Log com os dados a inserir
     * @return true se a inserção for bem-sucedida, false caso contrário
     */
    public boolean inserirLog(Log aLog) {
        String sql = "INSERT INTO LOGS (L_DATA, L_HORA, L_UTILIZADOR, L_ACAO) VALUES (?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, aLog.getData());
            ps.setString(2, aLog.getHora());
            ps.setString(3, aLog.getUtilizador());
            ps.setString(4, aLog.getAcao());

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    ConexaoBD.fecharBd(conn);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Lista todos os registos de log do sistema, ordenados por data e hora
     * decrescente.
     *
     * @return lista de todos os logs
     */
    public ArrayList<Log> listarTodosLogs() {
        ArrayList<Log> lista = new ArrayList<>();
        String sql = "SELECT * FROM LOGS ORDER BY L_DATA DESC, L_HORA DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                Log l = new Log(
                        rs.getInt("L_ID_LOG"),
                        rs.getString("L_DATA"),
                        rs.getString("L_HORA"),
                        rs.getString("L_UTILIZADOR"),
                        rs.getString("L_ACAO"));
                lista.add(l);
            }

        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    ConexaoBD.fecharBd(conn);
                } catch (Exception e) {
                }
            }
        }
        return lista;
    }

    /**
     * Pesquisa registos de log por username do utilizador (pesquisa parcial).
     *
     * @param username username ou parte do username a pesquisar
     * @return lista de logs que correspondem ao critério
     */
    public ArrayList<Log> pesquisarLogsPorUtilizador(String username) {
        ArrayList<Log> lista = new ArrayList<>();
        String sql = "SELECT * FROM LOGS WHERE L_UTILIZADOR LIKE ? ORDER BY L_DATA DESC, L_HORA DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setString(1, "%" + username + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                Log l = new Log(
                        rs.getInt("L_ID_LOG"),
                        rs.getString("L_DATA"),
                        rs.getString("L_HORA"),
                        rs.getString("L_UTILIZADOR"),
                        rs.getString("L_ACAO"));
                lista.add(l);
            }

        } catch (SQLException e) {
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    ConexaoBD.fecharBd(conn);
                } catch (Exception e) {
                }
            }
        }
        return lista;
    }
}