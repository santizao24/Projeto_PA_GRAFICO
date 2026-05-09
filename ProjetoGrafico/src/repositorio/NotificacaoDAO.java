package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Enums.EstadoNotificacao;
import model.Notificacao;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência
 * de notificações na base de dados.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class NotificacaoDAO {

    /**
     * Insere uma nova notificação na base de dados.
     *
     * @param aNotificacao objeto Notificação com os dados a inserir
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirNotificacao(Notificacao aNotificacao) {
        String sql = "INSERT INTO NOTIFICACAO (U_ID_UTILIZADOR, N_MENSAGEM, N_DATA_CRIACAO, N_ESTADO, N_CATEGORIA) VALUES (?, ?, ?, ?, ?)";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setInt(1, aNotificacao.getIdUtilizador());
            ps.setString(2, aNotificacao.getMensagem());
            ps.setString(3, aNotificacao.getDataCriacao());
            ps.setString(4, aNotificacao.getEstado().name());
            ps.setString(5, aNotificacao.getCategoria().name());

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
     * Lista todas as notificações de um utilizador, ordenadas da mais recente para
     * a mais antiga.
     *
     * @param idUtilizador identificador do utilizador
     * @return lista de notificações do utilizador
     */
    public ArrayList<Notificacao> listarNotificacoesPorUtilizador(int idUtilizador) {
        ArrayList<Notificacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM NOTIFICACAO WHERE U_ID_UTILIZADOR = ? ORDER BY N_ID_NOTIFICACAO DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);

            rs = ps.executeQuery();
            while (rs.next()) {
                String estadoStr = rs.getString("N_ESTADO");
                EstadoNotificacao estadoEnum;
                try {
                    estadoEnum = EstadoNotificacao.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoNotificacao.POR_LER;
                }

                String categoriaStr = rs.getString("N_CATEGORIA");
                Enums.CategoriaNotificacao categoriaEnum;
                try {
                    categoriaEnum = Enums.CategoriaNotificacao
                            .valueOf(categoriaStr != null ? categoriaStr.toUpperCase() : "");
                } catch (Exception e) {
                    categoriaEnum = Enums.CategoriaNotificacao.GERAL;
                }

                Notificacao n = new Notificacao(
                        rs.getInt("N_ID_NOTIFICACAO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("N_MENSAGEM"),
                        rs.getString("N_DATA_CRIACAO"),
                        estadoEnum,
                        categoriaEnum);
                lista.add(n);
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
     * Conta o número de notificações não lidas de um utilizador.
     *
     * @param idUtilizador identificador do utilizador
     * @return número de notificações com estado POR_LER
     */
    public int contarNotificacoesNaoLidas(int idUtilizador) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM NOTIFICACAO WHERE U_ID_UTILIZADOR = ? AND N_ESTADO = 'POR_LER'";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);

            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
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
        return total;
    }

    /**
     * Marca como lidas todas as notificações não lidas de um utilizador.
     *
     * @param idUtilizador identificador do utilizador
     * @return {@code true} se a operação for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean marcarComoLidas(int idUtilizador) {
        String sql = "UPDATE NOTIFICACAO SET N_ESTADO = 'LIDA' WHERE U_ID_UTILIZADOR = ? AND N_ESTADO = 'POR_LER'";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);

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
     * Lista as notificações de um utilizador filtradas por categoria.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    nome da categoria a filtrar
     * @return lista de notificações da categoria especificada
     */
    public ArrayList<Notificacao> listarNotificacoesPorCategoria(int idUtilizador, String categoria) {
        ArrayList<Notificacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM NOTIFICACAO WHERE U_ID_UTILIZADOR = ? AND N_CATEGORIA = ? ORDER BY N_ID_NOTIFICACAO DESC";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);
            ps.setString(2, categoria);

            rs = ps.executeQuery();
            while (rs.next()) {
                String estadoStr = rs.getString("N_ESTADO");
                EstadoNotificacao estadoEnum;
                try {
                    estadoEnum = EstadoNotificacao.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoNotificacao.POR_LER;
                }
                String categoriaStr = rs.getString("N_CATEGORIA");
                Enums.CategoriaNotificacao categoriaEnum;
                try {
                    categoriaEnum = Enums.CategoriaNotificacao
                            .valueOf(categoriaStr != null ? categoriaStr.toUpperCase() : "");
                } catch (Exception e) {
                    categoriaEnum = Enums.CategoriaNotificacao.GERAL;
                }

                Notificacao n = new Notificacao(
                        rs.getInt("N_ID_NOTIFICACAO"),
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("N_MENSAGEM"),
                        rs.getString("N_DATA_CRIACAO"),
                        estadoEnum,
                        categoriaEnum);
                lista.add(n);
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
     * Conta o número de notificações não lidas de um utilizador numa categoria
     * específica.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    nome da categoria a filtrar
     * @return número de notificações não lidas na categoria
     */
    public int contarNaoLidasPorCategoria(int idUtilizador, String categoria) {
        int total = 0;
        String sql = "SELECT COUNT(*) FROM NOTIFICACAO WHERE U_ID_UTILIZADOR = ? AND N_ESTADO = 'POR_LER' AND N_CATEGORIA = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);
            ps.setString(2, categoria);

            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
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
        return total;
    }

    /**
     * Marca como lidas as notificações não lidas de um utilizador numa categoria
     * específica.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    nome da categoria a filtrar
     * @return {@code true} se a operação for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean marcarComoLidasPorCategoria(int idUtilizador, String categoria) {
        String sql = "UPDATE NOTIFICACAO SET N_ESTADO = 'LIDA' WHERE U_ID_UTILIZADOR = ? AND N_ESTADO = 'POR_LER' AND N_CATEGORIA = ?";
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, idUtilizador);
            ps.setString(2, categoria);

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
}