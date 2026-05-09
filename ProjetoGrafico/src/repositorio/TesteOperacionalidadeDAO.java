package repositorio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.TesteOperacionalidade;
import util.ConexaoBD;

/**
 * Classe de acesso a dados (DAO) responsável pela persistência
 * de testes de operacionalidade na base de dados.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class TesteOperacionalidadeDAO {

    /**
     * Insere um novo teste de operacionalidade na base de dados.
     *
     * @param aTeste objeto TesteOperacionalidade com os dados a inserir
     * @return {@code true} se a inserção for bem sucedida, {@code false} caso
     *         contrário
     */
    public boolean inserirTeste(TesteOperacionalidade aTeste) {
        String sql = "INSERT INTO TESTE_OPERACIONALIDADE (R_ID_REPARACAO, T_P_DESIGNACAO, T_DESCRICAO, T_DATA_REALIZACAO) VALUES (?, ?, ?, ?)";

        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConexaoBD.obterConexao();
            ps = conn.prepareStatement(sql);

            ps.clearParameters();

            ps.setInt(1, aTeste.getIdReparacao());
            ps.setString(2, aTeste.getDesignacao());
            ps.setString(3, aTeste.getDescricao());
            ps.setString(4, aTeste.getDataRealizacao());

            int linhasAfetadas = ps.executeUpdate();
            return linhasAfetadas > 0;

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