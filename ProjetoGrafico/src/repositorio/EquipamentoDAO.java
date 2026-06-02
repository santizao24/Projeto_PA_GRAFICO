package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.ConexaoBD;
import model.Equipamento;
import model.Utilizador;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações CRUD
 * de equipamentos na base de dados.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class EquipamentoDAO {

    /**
     * Insere um novo equipamento na base de dados associado a um cliente.
     *
     * @param clienteLogado utilizador (cliente) proprietário do equipamento
     * @param aEquipamento  objeto Equipamento com os dados a inserir
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirEquipamento(Utilizador clienteLogado, Equipamento aEquipamento) {
        String sql = "INSERT INTO EQUIPAMENTO (U_ID_UTILIZADOR, E_MARCA, E_CODIGO_MODELO, E_CODIGO_SKU, E_DATA_FABRICO, E_LOTE, E_OBSERVACOES) values (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setInt(1, clienteLogado.getIdUtilizador());
            ps.setString(2, aEquipamento.getMarca());
            ps.setString(3, aEquipamento.getModelo());
            ps.setInt(4, aEquipamento.getSku());
            ps.setString(5, aEquipamento.getDataFabrico());
            ps.setString(6, aEquipamento.getLote());
            ps.setString(7, aEquipamento.getObservacoes());

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
     * Lista todos os equipamentos pertencentes a um cliente.
     *
     * @param idCliente identificador do cliente
     * @return lista de equipamentos do cliente
     */
    public ArrayList<Equipamento> listarEquipamentosCliente(int idCliente) {
        ArrayList<Equipamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM EQUIPAMENTO WHERE U_ID_UTILIZADOR = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idCliente);

            rs = ps.executeQuery();
            while (rs.next()) {
                Equipamento eq = new Equipamento(
                        rs.getInt("E_ID_EQUIPAMENTO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("E_MARCA"),
                        rs.getString("E_CODIGO_MODELO"),
                        rs.getInt("E_CODIGO_SKU"),
                        rs.getString("E_DATA_FABRICO"),
                        rs.getString("E_LOTE"));
                lista.add(eq);
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
     * Verifica se um código SKU já existe na base de dados.
     *
     * @param sku código SKU a verificar
     * @return {@code true} se o SKU existir, {@code false} caso contrário
     */
    public boolean skuExiste(int sku) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM EQUIPAMENTO WHERE E_CODIGO_SKU = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, sku);

            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
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
        return existe;
    }

    /**
     * Verifica se um código de modelo já existe na base de dados.
     *
     * @param codigoModelo código de modelo a verificar
     * @return {@code true} se o modelo existir, {@code false} caso contrário
     */
    public boolean modeloExiste(String codigoModelo) {
        boolean existe = false;
        String sql = "SELECT COUNT(*) FROM EQUIPAMENTO WHERE E_CODIGO_MODELO = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setString(1, codigoModelo);

            rs = ps.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                existe = true;
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
        return existe;
    }

    /**
     * Pesquisa equipamentos com base num critério e termo de pesquisa parcial.
     *
     * @param escolha       critério de pesquisa (1 = marca, 2 = código de modelo)
     * @param termoPesquisa termo de pesquisa parcial
     * @return lista de equipamentos que correspondem aos critérios
     */
    public ArrayList<Equipamento> pesquisarEquipamentos(int escolha, String termoPesquisa) {
        ArrayList<Equipamento> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "E_MARCA" : "E_CODIGO_MODELO";
        String sql = "SELECT * FROM EQUIPAMENTO WHERE " + coluna + " LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setString(1, "%" + termoPesquisa + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                Equipamento eq = new Equipamento(
                        rs.getInt("E_ID_EQUIPAMENTO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("E_MARCA"),
                        rs.getString("E_CODIGO_MODELO"),
                        rs.getInt("E_CODIGO_SKU"),
                        rs.getString("E_DATA_FABRICO"),
                        rs.getString("E_LOTE"));
                lista.add(eq);
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
     * Lista os equipamentos de um cliente ordenados por um critério.
     *
     * @param idCliente  identificador do cliente
     * @param escolha    critério de ordenação (1 = marca, 2 = código SKU)
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de equipamentos ordenados
     */
    public ArrayList<Equipamento> listarEquipamentosClienteOrdenados(int idCliente, int escolha, boolean ascendente) {
        ArrayList<Equipamento> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "E_MARCA" : "E_CODIGO_SKU";
        String ordem = ascendente ? "ASC" : "DESC";
        String sql = "SELECT * FROM EQUIPAMENTO WHERE U_ID_UTILIZADOR = ? ORDER BY " + coluna + " " + ordem;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idCliente);

            rs = ps.executeQuery();
            while (rs.next()) {
                Equipamento eq = new Equipamento(
                        rs.getInt("E_ID_EQUIPAMENTO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("E_MARCA"),
                        rs.getString("E_CODIGO_MODELO"),
                        rs.getInt("E_CODIGO_SKU"),
                        rs.getString("E_DATA_FABRICO"),
                        rs.getString("E_LOTE"));
                lista.add(eq);
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
     * Pesquisa equipamentos de um cliente com base num critério e termo de
     * pesquisa.
     *
     * @param idCliente     identificador do cliente
     * @param escolha       critério de pesquisa (1 = marca, 2 = código SKU)
     * @param termoPesquisa termo de pesquisa parcial
     * @return lista de equipamentos que correspondem aos critérios
     */
    public ArrayList<Equipamento> pesquisarEquipamentosCliente(int idCliente, int escolha, String termoPesquisa) {
        ArrayList<Equipamento> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "E_MARCA" : "E_CODIGO_SKU";
        String sql = "SELECT * FROM EQUIPAMENTO WHERE U_ID_UTILIZADOR = ? AND " + coluna + " LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idCliente);
            ps.setString(2, "%" + termoPesquisa + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                Equipamento eq = new Equipamento(
                        rs.getInt("E_ID_EQUIPAMENTO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("E_MARCA"),
                        rs.getString("E_CODIGO_MODELO"),
                        rs.getInt("E_CODIGO_SKU"),
                        rs.getString("E_DATA_FABRICO"),
                        rs.getString("E_LOTE"));
                lista.add(eq);
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