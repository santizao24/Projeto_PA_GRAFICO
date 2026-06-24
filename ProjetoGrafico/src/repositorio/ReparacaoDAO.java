package repositorio;

import java.sql.*;
import java.util.ArrayList;

import Enums.EstadoReparacao;
import model.Reparacao;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência
 * de reparações na base de dados. Gere todo o ciclo de vida dos processos,
 * incluindo inserção, atualização de estado, conclusão e arquivamento.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ReparacaoDAO {

    /**
     * Insere uma nova reparação na base de dados.
     *
     * @param aReparacao objeto Reparação com os dados a inserir
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirReparacao(Reparacao aReparacao) {
        Connection conn = null;
        PreparedStatement ps = null;

        String sql = "INSERT INTO REPARACAO (R_NUM_REPARACAO, E_ID_EQUIPAMENTO, U_ID_UTILIZADOR, R_DATA_CRIACAO, R_ESTADO, R_OBSERVACOES) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);

            ps.clearParameters();

            ps.setString(1, aReparacao.getNumReparacao());
            ps.setInt(2, aReparacao.getIdEquipamento());

            if (aReparacao.getIdUtilizador() <= 0) {
                ps.setNull(3, java.sql.Types.INTEGER);
            } else {
                ps.setInt(3, aReparacao.getIdUtilizador());
            }

            ps.setString(4, aReparacao.getDataCriacao());
            ps.setString(5, aReparacao.getEstado().name());
            ps.setString(6, aReparacao.getObservacoes());

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
     * Conta o número total de reparações na base de dados.
     *
     * @return número total de reparações
     */
    public int contarReparacoes() {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM REPARACAO";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
        return total;
    }

    private Reparacao criarReparacaoDoResultSet(ResultSet rs) throws SQLException {
        String estadoStr = rs.getString("R_ESTADO");
        String estado = (estadoStr != null) ? estadoStr.toUpperCase() : "CRIADO";
        EstadoReparacao estadoReparacao;

        try {
            estadoReparacao = EstadoReparacao.valueOf(estado);
        } catch (Exception e) {
            estadoReparacao = EstadoReparacao.CRIADO;
        }

        return new Reparacao(
                rs.getInt("R_ID_REPARACAO"),
                rs.getString("R_NUM_REPARACAO"),
                rs.getInt("E_ID_EQUIPAMENTO"),
                rs.getInt("U_ID_UTILIZADOR"),
                rs.getString("R_DATA_CRIACAO"),
                rs.getString("R_DATA_INICIO"),
                rs.getString("R_DATA_FIM_REPARACAO"),
                rs.getInt("R_TEMPO_DECORRIDO"),
                rs.getDouble("R_CUSTO"),
                estadoReparacao,
                rs.getString("R_OBSERVACOES"));
    }

    /**
     * Lista todas as reparações com estado CRIADO (pendentes de aprovação).
     *
     * @return lista de reparações pendentes
     */
    public ArrayList<Reparacao> listarReparacoesPendentes() {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE R_ESTADO = 'CRIADO'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Atualiza o estado de uma reparação e o funcionário atribuído.
     * Se o novo estado for DECORRER, atualiza também a data de início.
     *
     * @param idReparacao   identificador da reparação
     * @param idFuncionario identificador do funcionário (0 para remover atribuição)
     * @param novoEstado    novo estado a atribuir
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarEstadoReparacao(int idReparacao, int idFuncionario, String novoEstado) {
        String sql;
        if (novoEstado.equals(EstadoReparacao.DECORRER.name())) {
            sql = "UPDATE REPARACAO SET U_ID_UTILIZADOR = ?, R_ESTADO = ?, R_DATA_INICIO = NOW() WHERE R_ID_REPARACAO = ?";
        } else {
            sql = "UPDATE REPARACAO SET U_ID_UTILIZADOR = ?, R_ESTADO = ? WHERE R_ID_REPARACAO = ?";
        }

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            if (idFuncionario <= 0) {
                ps.setNull(1, java.sql.Types.INTEGER);
            } else {
                ps.setInt(1, idFuncionario);
            }

            ps.setString(2, novoEstado);
            ps.setInt(3, idReparacao);

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
     * Lista as reparações atribuídas a um funcionário filtradas por estado.
     *
     * @param idFuncionario identificador do funcionário
     * @param estado        estado das reparações a filtrar
     * @return lista de reparações do funcionário no estado indicado
     */
    public ArrayList<Reparacao> listarReparacoesPorFuncionario(int idFuncionario, String estado) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE U_ID_UTILIZADOR = ? AND R_ESTADO = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idFuncionario);
            ps.setString(2, estado);

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Conclui uma reparação, registando a data de fim, tempo decorrido, custo e
     * observações.
     *
     * @param idReparacao    identificador da reparação
     * @param dataFim        data e hora de conclusão
     * @param tempoDecorrido tempo decorrido em minutos
     * @param custo          custo final em euros
     * @param observacoes    observações finais
     * @return {@code true} se a conclusão for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean concluirReparacao(int idReparacao, String dataFim, int tempoDecorrido, double custo,
            String observacoes) {
        String sql = "UPDATE REPARACAO SET R_DATA_FIM_REPARACAO = ?, R_TEMPO_DECORRIDO = ?, R_CUSTO = ?, R_ESTADO = 'FINALIZADO', R_OBSERVACOES = ? WHERE R_ID_REPARACAO = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, dataFim);
            ps.setInt(2, tempoDecorrido);
            ps.setDouble(3, custo);
            ps.setString(4, observacoes);
            ps.setInt(5, idReparacao);

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
     * Lista todas as reparações no estado FINALIZADO.
     *
     * @return lista de reparações concluídas
     */
    public ArrayList<Reparacao> listarReparacoesConcluidas() {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE R_ESTADO = 'FINALIZADO'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Arquiva uma reparação, alterando o seu estado para ARQUIVADO.
     *
     * @param idReparacao identificador da reparação a arquivar
     * @return {@code true} se o arquivamento for bem sucedido, {@code false} caso
     *         contrário
     */
    public boolean arquivarReparacao(int idReparacao) {
        String sql = "UPDATE REPARACAO SET R_ESTADO = 'ARQUIVADO' WHERE R_ID_REPARACAO = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idReparacao);

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
     * Regista que um funcionário rejeitou uma reparação.
     *
     * @param idReparacao   identificador da reparação
     * @param idFuncionario identificador do funcionário que rejeitou
     * @return {@code true} se o registo for bem sucedido, {@code false} caso
     *         contrário
     */
    public boolean registarRejeicao(int idReparacao, int idFuncionario) {
        String sql = "INSERT INTO REPARACAO_REJEITADA (R_ID_REPARACAO, U_ID_UTILIZADOR) VALUES (?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idReparacao);
            ps.setInt(2, idFuncionario);

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
     * Obtém as reparações com mais de 10 dias sem finalizar.
     *
     * @return lista de reparações atrasadas
     */
    public ArrayList<Reparacao> obterReparacoesAtrasadas() {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE R_ESTADO IN ('DECORRER', 'ACEITE', 'CRIADO') AND DATEDIFF(CURDATE(), R_DATA_CRIACAO) > 10";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Lista todas as reparações ordenadas por um critério.
     *
     * @param escolha    critério de ordenação (1 = data de criação, 2 = número)
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de reparações ordenadas
     */
    public ArrayList<Reparacao> listarTodasReparacoesOrdenadas(int escolha, boolean ascendente) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "R_DATA_CRIACAO" : "R_NUM_REPARACAO";
        String ordem = ascendente ? "ASC" : "DESC";
        String sql = "SELECT * FROM REPARACAO ORDER BY " + coluna + " " + ordem;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Lista todas as reparações não finalizadas (estados CRIADO, ACEITE, DECORRER).
     *
     * @return lista de reparações não finalizadas
     */
    public ArrayList<Reparacao> listarReparacoesNaoFinalizadas() {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE R_ESTADO IN ('CRIADO', 'ACEITE', 'DECORRER')";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Pesquisa reparações com base num critério e termo de pesquisa parcial.
     *
     * @param escolha       critério (1 = número, 2 = estado, 3 = nome do cliente)
     * @param termoPesquisa termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoes(int escolha, String termoPesquisa) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "";

        if (escolha == 1) {
            sql = "SELECT * FROM REPARACAO WHERE R_NUM_REPARACAO LIKE ?";
        } else if (escolha == 2) {
            sql = "SELECT * FROM REPARACAO WHERE R_ESTADO LIKE ?";
        } else if (escolha == 3) {
            sql = "SELECT r.* FROM REPARACAO r JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO JOIN UTILIZADOR u ON e.U_ID_UTILIZADOR = u.U_ID_UTILIZADOR WHERE u.U_NOME LIKE ?";
        }

        if (sql.isEmpty()) {
            return lista;
        }

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
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Lista as reparações de um cliente ordenadas por um critério.
     *
     * @param idCliente  identificador do cliente
     * @param escolha    critério de ordenação (1 = data, 2 = número)
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de reparações do cliente ordenadas
     */
    public ArrayList<Reparacao> listarReparacoesClienteOrdenadas(int idCliente, int escolha, boolean ascendente) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "r.R_DATA_CRIACAO" : "r.R_NUM_REPARACAO";
        String ordem = ascendente ? "ASC" : "DESC";
        String sql = "SELECT r.* FROM REPARACAO r " +
                "JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO " +
                "WHERE e.U_ID_UTILIZADOR = ? " +
                "ORDER BY " + coluna + " " + ordem;

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
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Pesquisa reparações de um cliente com base num critério e termo de pesquisa.
     *
     * @param idCliente identificador do cliente
     * @param escolha   critério (1 = número, 2 = estado)
     * @param termo     termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoesCliente(int idCliente, int escolha, String termo) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "";

        if (escolha == 1) {
            sql = "SELECT r.* FROM REPARACAO r " +
                    "JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO " +
                    "WHERE e.U_ID_UTILIZADOR = ? AND r.R_NUM_REPARACAO LIKE ?";
        } else if (escolha == 2) {
            sql = "SELECT r.* FROM REPARACAO r " +
                    "JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO " +
                    "WHERE e.U_ID_UTILIZADOR = ? AND r.R_ESTADO LIKE ?";
        }

        if (sql.isEmpty()) {
            return lista;
        }

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idCliente);
            ps.setString(2, "%" + termo + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Lista as reparações de um funcionário ordenadas por um critério.
     *
     * @param IdFuncionario identificador do funcionário
     * @param escolha       critério de ordenação (1 = data, 2 = número)
     * @param ascendente    {@code true} para ordem crescente, {@code false} para
     *                      decrescente
     * @return lista de reparações do funcionário ordenadas
     */
    public ArrayList<Reparacao> listarReparacoesFuncionarioOrdenadas(int IdFuncionario, int escolha,
            boolean ascendente) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "R_DATA_CRIACAO" : "R_NUM_REPARACAO";
        String ordem = ascendente ? "ASC" : "DESC";
        String sql = "SELECT * FROM REPARACAO WHERE U_ID_UTILIZADOR = ? ORDER BY " + coluna + " " + ordem;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, IdFuncionario);

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Pesquisa reparações de um funcionário com base num critério e termo de
     * pesquisa.
     *
     * @param IdFuncionario identificador do funcionário
     * @param escolha       critério (1 = número, 2 = estado)
     * @param termo         termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoesFuncionario(int IdFuncionario, int escolha, String termo) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String coluna = (escolha == 1) ? "R_NUM_REPARACAO" : "R_ESTADO";
        String sql = "SELECT * FROM REPARACAO WHERE U_ID_UTILIZADOR = ? AND " + coluna + " LIKE ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, IdFuncionario);
            ps.setString(2, "%" + termo + "%");

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Pesquisa reparações criadas num intervalo de datas.
     *
     * @param aDataInicio data inicial (formato YYYY-MM-DD)
     * @param aDataFim    data final (formato YYYY-MM-DD)
     * @return lista de reparações criadas no intervalo
     */
    public ArrayList<Reparacao> pesquisarReparacoesPorData(String aDataInicio, String aDataFim) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO WHERE R_DATA_CRIACAO BETWEEN ? AND ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setString(1, aDataInicio);
            ps.setString(2, aDataFim);

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Obtém o identificador do cliente associado a uma reparação.
     *
     * @param idReparacao identificador da reparação
     * @return identificador do cliente (0 se não encontrado)
     */
    public int obterIdClienteDaReparacao(int idReparacao) {
        int idCliente = 0;
        String sql = "SELECT e.U_ID_UTILIZADOR FROM REPARACAO r " +
                "JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO " +
                "WHERE r.R_ID_REPARACAO = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idReparacao);

            rs = ps.executeQuery();
            if (rs.next()) {
                idCliente = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
        return idCliente;
    }

    /**
     * Obtém todas as reparações associadas aos equipamentos de um cliente.
     *
     * @param idCliente identificador do cliente
     * @return lista de reparações do cliente
     */
    public ArrayList<Reparacao> obterReparacoesPorCliente(int idCliente) {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT r.* FROM REPARACAO r INNER JOIN EQUIPAMENTO e ON r.E_ID_EQUIPAMENTO = e.E_ID_EQUIPAMENTO WHERE e.U_ID_UTILIZADOR = ?";
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
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
     * Obtém todas as reparações registadas no sistema.
     *
     * @return lista de todas as reparações
     */
    public ArrayList<Reparacao> obterTodasReparacoes() {
        ArrayList<Reparacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM REPARACAO";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                lista.add(criarReparacaoDoResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
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