package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Peca;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência
 * de peças no armazém na base de dados.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PecaDAO {

    /**
     * Lista todas as peças com stock disponível (quantidade superior a zero).
     *
     * @return lista de peças com stock
     */
    public ArrayList<Peca> listarPecasComStock() {
        ArrayList<Peca> lista = new ArrayList<>();
        String sql = "SELECT * FROM PECA WHERE P_QUANTIDADE > 0";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                Peca p = new Peca(
                        rs.getString("P_CODIGO_INTERNO"),
                        rs.getString("T_P_DESIGNACAO"),
                        rs.getString("P_FABRICANTE"),
                        rs.getInt("P_QUANTIDADE"));
                lista.add(p);
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
     * Lista todas as peças registadas no sistema, independentemente da quantidade
     * em stock.
     *
     * @return lista de todas as peças
     */
    public ArrayList<Peca> listarTodasPecas() {
        ArrayList<Peca> lista = new ArrayList<>();
        String sql = "SELECT * FROM PECA ORDER BY T_P_DESIGNACAO";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                Peca p = new Peca(
                        rs.getString("P_CODIGO_INTERNO"),
                        rs.getString("T_P_DESIGNACAO"),
                        rs.getString("P_FABRICANTE"),
                        rs.getInt("P_QUANTIDADE"));
                lista.add(p);
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
     * Regista a utilização de uma peça numa reparação, com transação atómica.
     * Insere o registo na tabela de associação e atualiza o stock.
     *
     * @param idReparacao     identificador da reparação
     * @param codigoPeca      código interno da peça
     * @param quantidadeUsada quantidade utilizada
     * @return {@code true} se a operação for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean registarPecaUsada(int idReparacao, String codigoPeca, int quantidadeUsada) {
        String sqlInsert = "INSERT INTO REPARACAO_PECA (R_ID_REPARACAO, P_CODIGO_INTERNO, RP_QUANTIDADE_USADA) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE PECA SET P_QUANTIDADE = P_QUANTIDADE - ? WHERE P_CODIGO_INTERNO = ?";

        Connection conn = null;
        PreparedStatement psInsert = null;
        PreparedStatement psUpdate = null;

        try {
            conn = ConexaoBD.obterConexao();
            conn.setAutoCommit(false);

            psInsert = conn.prepareStatement(sqlInsert);
            psInsert.clearParameters();
            psInsert.setInt(1, idReparacao);
            psInsert.setString(2, codigoPeca);
            psInsert.setInt(3, quantidadeUsada);
            psInsert.executeUpdate();

            psUpdate = conn.prepareStatement(sqlUpdate);
            psUpdate.clearParameters();
            psUpdate.setInt(1, quantidadeUsada);
            psUpdate.setString(2, codigoPeca);
            psUpdate.executeUpdate();

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            return false;

        } finally {
            if (psInsert != null) {
                try {
                    psInsert.close();
                } catch (Exception e) {
                }
            }
            if (psUpdate != null) {
                try {
                    psUpdate.close();
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    ConexaoBD.fecharBd(conn);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Insere uma nova peça na base de dados.
     *
     * @param aPeca objeto Peca com os dados a inserir
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirPeca(Peca aPeca) {
        String sql = "INSERT INTO PECA (P_CODIGO_INTERNO, T_P_DESIGNACAO, P_FABRICANTE, P_QUANTIDADE) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, aPeca.getCodigoInterno());
            ps.setString(2, aPeca.getDesignacao());
            ps.setString(3, aPeca.getFabricante());
            ps.setInt(4, aPeca.getQuantidade());

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
     * Atualiza a quantidade em stock de uma peça, adicionando a quantidade
     * indicada.
     *
     * @param codigoPeca          código interno da peça
     * @param quantidadeAdicionar quantidade a adicionar ao stock
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarQuantidadePeca(String codigoPeca, int quantidadeAdicionar) {
        String sql = "UPDATE PECA SET P_QUANTIDADE = P_QUANTIDADE + ? WHERE P_CODIGO_INTERNO = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setInt(1, quantidadeAdicionar);
            ps.setString(2, codigoPeca);

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
     * Obtém uma peça a partir do seu código interno.
     *
     * @param codigoPeca código interno da peça a pesquisar
     * @return objeto {@link Peca} se encontrada, ou {@code null} caso contrário
     */
    public Peca obterPecaPorCodigo(String codigoPeca) {
        Peca pecaEncontrada = null;
        String sql = "SELECT * FROM PECA WHERE P_CODIGO_INTERNO = ?";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setString(1, codigoPeca);

            rs = ps.executeQuery();
            if (rs.next()) {
                pecaEncontrada = new Peca(
                        rs.getString("P_CODIGO_INTERNO"),
                        rs.getString("T_P_DESIGNACAO"),
                        rs.getString("P_FABRICANTE"),
                        rs.getInt("P_QUANTIDADE"));
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
        return pecaEncontrada;
    }
}