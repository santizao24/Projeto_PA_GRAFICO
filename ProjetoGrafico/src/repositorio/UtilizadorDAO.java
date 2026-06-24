package repositorio;

import java.sql.*;
import java.util.ArrayList;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;
import model.Cliente;
import model.Funcionario;
import model.Utilizador;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pelas operações de persistência
 * de utilizadores na base de dados. Gere o registo, validação de credenciais,
 * listagem e atualização de perfis de Clientes, Funcionários e Gestores.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class UtilizadorDAO {

    /**
     * Insere um novo utilizador na base de dados. Assegura a inserção coerente
     * nas tabelas específicas (cliente ou funcionario) utilizando transações.
     *
     * @param aUtilizador objeto (Cliente, Funcionario ou Gestor) a ser inserido
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirUtilizador(Utilizador aUtilizador) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sqlUtilizador = "INSERT INTO UTILIZADOR (U_NOME, U_USERNAME, U_EMAIL, U_PASSWORD, U_TIPO, U_ESTADO, U_FOTO, U_OBSERVACOES) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            conn = ConexaoBD.obterConexao();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sqlUtilizador, Statement.RETURN_GENERATED_KEYS);
            ps.clearParameters();

            ps.setString(1, aUtilizador.getNome());
            ps.setString(2, aUtilizador.getLogin());
            ps.setString(3, aUtilizador.getEmail());
            ps.setString(4, aUtilizador.getPassword());
            ps.setString(5, aUtilizador.getTipo().name());
            ps.setString(6, aUtilizador.getEstado().name());
            ps.setString(7, aUtilizador.getFotoPath());
            ps.setString(8, aUtilizador.getObservacoes());

            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            int novoId = 0;
            if (rs.next()) {
                novoId = rs.getInt(1);
            }

            if (aUtilizador instanceof Cliente) {
                Cliente c = (Cliente) aUtilizador;
                String sqlCliente = "INSERT INTO cliente (U_ID_UTILIZADOR, C_NUM_CONTRIBUINTE, NUM_TELEFONE_, C_MORADA, C_SETOR_ATIVIDADE, C_ESCALAO) VALUES (?, ?, ?, ?, ?, ?)";

                if (ps != null) {
                    try {
                        ps.close();
                    } catch (Exception e) {
                    }
                }

                ps = conn.prepareStatement(sqlCliente);
                ps.clearParameters();

                ps.setInt(1, novoId);
                ps.setString(2, c.getNif());
                ps.setString(3, c.getTelefone());
                ps.setString(4, c.getMorada());
                ps.setString(5, c.getSetorAtividade());
                ps.setString(6, c.getEscalao());
                ps.executeUpdate();
            } else if (aUtilizador instanceof Funcionario) {
                Funcionario f = (Funcionario) aUtilizador;
                String sqlFunc = "INSERT INTO funcionario (U_ID_UTILIZADOR, C_NUM_CONTRIBUINTE, F_NUM_TELEFONE , C_MORADA, F_ESPECIALIZACAO, F_DATA_INICIO_ATIVIDADE) VALUES (?, ?, ?, ?, ?, ?)";

                if (ps != null) {
                    try {
                        ps.close();
                    } catch (Exception e) {
                    }
                }

                ps = conn.prepareStatement(sqlFunc);
                ps.clearParameters();

                ps.setInt(1, novoId);
                ps.setString(2, f.getNif());
                ps.setString(3, f.getTelefone());
                ps.setString(4, f.getMorada());
                ps.setInt(5, f.getEspecializacao());
                ps.setString(6, f.getDataInicio());
                ps.executeUpdate();
            }

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
                    conn.setAutoCommit(true);
                    ConexaoBD.fecharBd(conn);
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Valida o login de um utilizador, verificando username e password.
     *
     * @param aUsername username inserido
     * @param aPassword password inserida
     * @return objeto {@link Utilizador} instanciado se credenciais válidas, ou
     *         {@code null}
     */
    public Utilizador validarLogin(String aUsername, String aPassword) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Utilizador utilizadorEncontrado = null;

        String sql = "SELECT * FROM UTILIZADOR WHERE U_USERNAME = ? AND U_PASSWORD = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, aUsername);
            ps.setString(2, aPassword);

            rs = ps.executeQuery();

            if (rs.next()) {
                TipoUtilizador tipoEnum;
                EstadoUtilizador estadoUtilizador;

                String tipoStr = rs.getString("U_TIPO");
                try {
                    tipoEnum = TipoUtilizador.valueOf(tipoStr != null ? tipoStr.toUpperCase() : "");
                } catch (Exception e) {
                    tipoEnum = TipoUtilizador.INDEFINIDO;
                }

                String estadoStr = rs.getString("U_ESTADO");
                try {
                    estadoUtilizador = EstadoUtilizador.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoUtilizador = EstadoUtilizador.PENDENTE;
                }

                utilizadorEncontrado = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        rs.getString("U_PASSWORD"),
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        tipoEnum,
                        estadoUtilizador);
                utilizadorEncontrado.setFotoPath(rs.getString("U_FOTO"));
                utilizadorEncontrado.setObservacoes(rs.getString("U_OBSERVACOES"));
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
        return utilizadorEncontrado;
    }

    /**
     * Lista todos os funcionários que se encontram no estado ATIVO.
     *
     * @return lista de funcionários ativos
     */
    public ArrayList<Utilizador> listarFuncionariosAtivos() {
        ArrayList<Utilizador> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT U_ID_UTILIZADOR, U_USERNAME, U_NOME, U_EMAIL, U_FOTO, U_OBSERVACOES FROM UTILIZADOR WHERE U_TIPO = 'FUNCIONARIO' AND U_ESTADO = 'ATIVO'";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                Utilizador u = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        "",
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        TipoUtilizador.FUNCIONARIO,
                        EstadoUtilizador.ATIVO);
                u.setFotoPath(rs.getString("U_FOTO"));
                u.setObservacoes(rs.getString("U_OBSERVACOES"));
                lista.add(u);
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
     * Verifica se existe pelo menos um gestor registado no sistema.
     *
     * @return {@code true} se existir algum gestor, {@code false} caso contrário
     */
    public boolean existeGestor() {
        boolean existe = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT COUNT(*) FROM UTILIZADOR WHERE U_TIPO = 'GESTOR'";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            if (rs.next()) {
                int total = rs.getInt(1);
                if (total > 0) {
                    existe = true;
                }
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
        return existe;
    }

    /**
     * Lista todos os utilizadores registados na base de dados.
     *
     * @return lista de todos os utilizadores
     */
    public ArrayList<Utilizador> listarTodosUtilizadores() {
        ArrayList<Utilizador> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT U_ID_UTILIZADOR, U_USERNAME, U_NOME, U_EMAIL, U_TIPO, U_ESTADO, U_FOTO, U_OBSERVACOES FROM UTILIZADOR";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                String tipoStr = rs.getString("U_TIPO");
                TipoUtilizador tipoEnum;
                try {
                    tipoEnum = TipoUtilizador.valueOf(tipoStr != null ? tipoStr.toUpperCase() : "");
                } catch (Exception e) {
                    tipoEnum = TipoUtilizador.INDEFINIDO;
                }

                String estadoStr = rs.getString("U_ESTADO");
                EstadoUtilizador estadoEnum;
                try {
                    estadoEnum = EstadoUtilizador.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoUtilizador.PENDENTE;
                }

                Utilizador u = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        "",
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        tipoEnum,
                        estadoEnum);
                u.setFotoPath(rs.getString("U_FOTO"));
                u.setObservacoes(rs.getString("U_OBSERVACOES"));
                lista.add(u);
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
     * Atualiza os dados do perfil de um cliente na base de dados.
     * Atualiza tanto na tabela UTILIZADOR como na tabela CLIENTE.
     *
     * @param idUtilizador identificador do cliente
     * @param novoEmail    novo endereço de email
     * @param novaPassword nova password
     * @param novoTelefone novo número de telefone
     * @param novaMorada   nova morada
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarPerfilCliente(int idUtilizador, String novoEmail, String novaPassword, String novoTelefone,
            String novaMorada) {
        String sqlUser = "UPDATE UTILIZADOR SET U_EMAIL = ?, U_PASSWORD = ? WHERE U_ID_UTILIZADOR = ?";
        String sqlCli = "UPDATE cliente SET NUM_TELEFONE_ = ?, C_MORADA = ? WHERE U_ID_UTILIZADOR = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sqlUser);
            ps.clearParameters();
            ps.setString(1, novoEmail);
            ps.setString(2, novaPassword);
            ps.setInt(3, idUtilizador);
            ps.executeUpdate();

            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }

            ps = conn.prepareStatement(sqlCli);
            ps.clearParameters();
            ps.setString(1, novoTelefone);
            ps.setString(2, novaMorada);
            ps.setInt(3, idUtilizador);
            ps.executeUpdate();

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
            if (ps != null) {
                try {
                    ps.close();
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
     * Atualiza os dados do perfil de um funcionário na base de dados.
     * Atualiza tanto na tabela UTILIZADOR como na tabela FUNCIONARIO.
     *
     * @param idUtilizador identificador do funcionário
     * @param novoEmail    novo endereço de email
     * @param novaPassword nova password
     * @param novoTelefone novo número de telefone
     * @param novaMorada   nova morada
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarPerfilFuncionario(int idUtilizador, String novoEmail, String novaPassword,
            String novoTelefone, String novaMorada) {
        String sqlUser = "UPDATE UTILIZADOR SET U_EMAIL = ?, U_PASSWORD = ? WHERE U_ID_UTILIZADOR = ?";
        String sqlFunc = "UPDATE funcionario SET F_NUM_TELEFONE = ?, C_MORADA = ? WHERE U_ID_UTILIZADOR = ?";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            conn.setAutoCommit(false);

            ps = conn.prepareStatement(sqlUser);
            ps.clearParameters();
            ps.setString(1, novoEmail);
            ps.setString(2, novaPassword);
            ps.setInt(3, idUtilizador);
            ps.executeUpdate();

            if (ps != null) {
                try {
                    ps.close();
                } catch (Exception e) {
                }
            }

            ps = conn.prepareStatement(sqlFunc);
            ps.clearParameters();
            ps.setString(1, novoTelefone);
            ps.setString(2, novaMorada);
            ps.setInt(3, idUtilizador);
            ps.executeUpdate();

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
            if (ps != null) {
                try {
                    ps.close();
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
     * Permite a um gestor atualizar os dados genéricos base de qualquer utilizador.
     *
     * @param idUtilizador identificador do utilizador alvo
     * @param novoNome     novo nome
     * @param novoEmail    novo email
     * @param novaPassword nova password
     * @param novoUsername novo username
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarDadosGenericos(int idUtilizador, String novoNome, String novoEmail, String novaPassword,
            String novoUsername) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE UTILIZADOR SET U_NOME = ?, U_EMAIL = ?, U_PASSWORD = ?, U_USERNAME = ? WHERE U_ID_UTILIZADOR = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, novoNome);
            ps.setString(2, novoEmail);
            ps.setString(3, novaPassword);
            ps.setString(4, novoUsername);
            ps.setInt(5, idUtilizador);

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
     * Lista todos os utilizadores ordenados alfabeticamente pelo nome.
     *
     * @param ascendente {@code true} para ordem crescente (A-Z), {@code false} para
     *                   decrescente (Z-A)
     * @return lista de utilizadores ordenados
     */
    public ArrayList<Utilizador> listarTodosUtilizadoresOrdenadosPorNome(boolean ascendente) {
        ArrayList<Utilizador> lista = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String ordem = ascendente ? "ASC" : "DESC";
        String sql = "SELECT U_ID_UTILIZADOR, U_USERNAME, U_NOME, U_EMAIL, U_TIPO, U_ESTADO, U_FOTO, U_OBSERVACOES FROM UTILIZADOR ORDER BY U_NOME "
                + ordem;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            rs = ps.executeQuery();
            while (rs.next()) {
                String tipoStr = rs.getString("U_TIPO");
                TipoUtilizador tipoEnum;
                try {
                    tipoEnum = TipoUtilizador.valueOf(tipoStr != null ? tipoStr.toUpperCase() : "");
                } catch (Exception e) {
                    tipoEnum = TipoUtilizador.INDEFINIDO;
                }

                String estadoStr = rs.getString("U_ESTADO");
                EstadoUtilizador estadoEnum;
                try {
                    estadoEnum = EstadoUtilizador.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoUtilizador.PENDENTE;
                }

                Utilizador u = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        "",
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        tipoEnum,
                        estadoEnum);
                u.setFotoPath(rs.getString("U_FOTO"));
                u.setObservacoes(rs.getString("U_OBSERVACOES"));
                lista.add(u);
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
     * Obtém um utilizador a partir do seu identificador único.
     *
     * @param id identificador do utilizador a procurar
     * @return objeto {@link Utilizador} se encontrado, ou {@code null} caso
     *         contrário
     */
    public Utilizador obterUtilizadorPorId(int id) {
        Utilizador u = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql = "SELECT U_ID_UTILIZADOR, U_USERNAME, U_NOME, U_EMAIL, U_TIPO, U_ESTADO, U_FOTO, U_OBSERVACOES FROM UTILIZADOR WHERE U_ID_UTILIZADOR = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();
            ps.setInt(1, id);

            rs = ps.executeQuery();
            if (rs.next()) {
                String tipoStr = rs.getString("U_TIPO");
                TipoUtilizador tipoEnum;
                try {
                    tipoEnum = TipoUtilizador.valueOf(tipoStr != null ? tipoStr.toUpperCase() : "");
                } catch (Exception e) {
                    tipoEnum = TipoUtilizador.INDEFINIDO;
                }

                String estadoStr = rs.getString("U_ESTADO");
                EstadoUtilizador estadoEnum;
                try {
                    estadoEnum = EstadoUtilizador.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoUtilizador.PENDENTE;
                }

                u = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        "",
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        tipoEnum,
                        estadoEnum);
                u.setFotoPath(rs.getString("U_FOTO"));
                u.setObservacoes(rs.getString("U_OBSERVACOES"));
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
        return u;
    }

    /**
     * Pesquisa utilizadores com base num critério e termo de pesquisa parcial.
     *
     * @param escolha       critério (1 = nome, 2 = username, 3 = tipo)
     * @param termoPesquisa termo de pesquisa parcial
     * @return lista de utilizadores que correspondem aos critérios
     */
    public ArrayList<Utilizador> pesquisarUtilizadores(int escolha, String termoPesquisa) {
        ArrayList<Utilizador> lista = new ArrayList<>();
        String sql = "";

        if (escolha == 1) {
            sql = "SELECT * FROM UTILIZADOR WHERE U_NOME LIKE ?";
        } else if (escolha == 2) {
            sql = "SELECT * FROM UTILIZADOR WHERE U_USERNAME LIKE ?";
        } else if (escolha == 3) {
            sql = "SELECT * FROM UTILIZADOR WHERE U_TIPO LIKE ?";
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
                String tipoStr = rs.getString("U_TIPO");
                TipoUtilizador tipoEnum;
                try {
                    tipoEnum = TipoUtilizador.valueOf(tipoStr != null ? tipoStr.toUpperCase() : "");
                } catch (Exception e) {
                    tipoEnum = TipoUtilizador.INDEFINIDO;
                }

                String estadoStr = rs.getString("U_ESTADO");
                EstadoUtilizador estadoEnum;
                try {
                    estadoEnum = EstadoUtilizador.valueOf(estadoStr != null ? estadoStr.toUpperCase() : "");
                } catch (Exception e) {
                    estadoEnum = EstadoUtilizador.PENDENTE;
                }

                Utilizador u = new Utilizador(
                        rs.getInt("U_ID_UTILIZADOR"),
                        rs.getString("U_USERNAME"),
                        "",
                        rs.getString("U_NOME"),
                        rs.getString("U_EMAIL"),
                        tipoEnum,
                        estadoEnum);
                u.setFotoPath(rs.getString("U_FOTO"));
                u.setObservacoes(rs.getString("U_OBSERVACOES"));
                lista.add(u);
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
     * Altera o estado da conta de um utilizador de forma arbitrária (por um
     * gestor).
     *
     * @param idUtilizador identificador do utilizador
     * @param novoEstado   estado a atribuir à conta
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarEstadoUtilizador(int idUtilizador, Enums.EstadoUtilizador novoEstado) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE UTILIZADOR SET U_ESTADO = ? WHERE U_ID_UTILIZADOR = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, novoEstado.name());
            ps.setInt(2, idUtilizador);
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
     * Remove os dados sensíveis de um utilizador do sistema (remoção lógica).
     * Substitui os dados por informações genéricas e marca como REMOVIDA.
     *
     * @param idUtilizador identificador do utilizador a remover
     * @return {@code true} se a remoção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean removerUtilizador(int idUtilizador) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE UTILIZADOR SET U_NOME = 'Conta Removida', U_USERNAME = ?, U_EMAIL = ?, U_PASSWORD = '***', U_ESTADO = 'REMOVIDA' WHERE U_ID_UTILIZADOR = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, "removido_" + idUtilizador);
            ps.setString(2, "removido_" + idUtilizador + "@sistema.local");
            ps.setInt(3, idUtilizador);
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
     * Atualiza o caminho da foto de perfil de um utilizador.
     *
     * @param idUtilizador identificador do utilizador
     * @param fotoPath     caminho relativo da nova foto
     * @return {@code true} se a atualização for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean atualizarFoto(int idUtilizador, String fotoPath) {
        Connection conn = null;
        PreparedStatement ps = null;
        String sql = "UPDATE UTILIZADOR SET U_FOTO = ? WHERE U_ID_UTILIZADOR = ?";

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);
            ps.clearParameters();

            ps.setString(1, fotoPath);
            ps.setInt(2, idUtilizador);
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