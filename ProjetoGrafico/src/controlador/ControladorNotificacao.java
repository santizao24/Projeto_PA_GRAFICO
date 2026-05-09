package controlador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;

import Enums.CategoriaNotificacao;
import Enums.EstadoNotificacao;
import Enums.TipoUtilizador;
import model.Notificacao;
import model.Utilizador;
import repositorio.NotificacaoDAO;
import repositorio.UtilizadorDAO;

/**
 * Controlador responsável pela lógica de negócio relacionada com notificações.
 * Permite gerar, listar, contar e marcar notificações como lidas,
 * incluindo operações por categoria e envios em massa para gestores.
 *
 * @author Santiago
 * @version 1.0
 */
public class ControladorNotificacao {

    private NotificacaoDAO nDao = new NotificacaoDAO();
    private UtilizadorDAO uDao = new UtilizadorDAO();

    /**
     * Gera uma nova notificação para um utilizador específico.
     *
     * @param idDestinatario identificador do utilizador destinatário
     * @param mensagem       conteúdo textual da notificação
     * @param categoria      categoria da notificação
     */
    public void gerarNotificacao(int idDestinatario, String mensagem, CategoriaNotificacao categoria) {
        String dataAtual = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Notificacao nova = new Notificacao(0, idDestinatario, mensagem, dataAtual, EstadoNotificacao.POR_LER,
                categoria);
        nDao.inserirNotificacao(nova);
    }

    /**
     * Gera uma notificação para todos os utilizadores do tipo Gestor.
     *
     * @param mensagem  conteúdo textual da notificação
     * @param categoria categoria da notificação
     */
    public void gerarNotificacaoParaGestores(String mensagem, CategoriaNotificacao categoria) {
        ArrayList<Utilizador> todosUtilizadores = uDao.listarTodosUtilizadores();
        Iterator<Utilizador> it = todosUtilizadores.iterator();

        while (it.hasNext()) {
            Utilizador u = it.next();
            if (u.getTipo() == TipoUtilizador.GESTOR) {
                gerarNotificacao(u.getIdUtilizador(), mensagem, categoria);
            }
        }
    }

    /**
     * Obtém todas as notificações de um utilizador, ordenadas da mais recente para a mais antiga.
     *
     * @param idUtilizador identificador do utilizador
     * @return lista de notificações do utilizador
     */
    public ArrayList<Notificacao> obterNotificacoes(int idUtilizador) {
        return nDao.listarNotificacoesPorUtilizador(idUtilizador);
    }

    /**
     * Conta o número de notificações não lidas de um utilizador.
     *
     * @param idUtilizador identificador do utilizador
     * @return número de notificações com estado POR_LER
     */
    public int contarNaoLidas(int idUtilizador) {
        return nDao.contarNotificacoesNaoLidas(idUtilizador);
    }

    /**
     * Marca todas as notificações não lidas de um utilizador como lidas.
     *
     * @param idUtilizador identificador do utilizador
     */
    public void marcarComoLidas(int idUtilizador) {
        nDao.marcarComoLidas(idUtilizador);
    }

    /**
     * Obtém as notificações de um utilizador filtradas por uma categoria específica.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    categoria das notificações a obter
     * @return lista de notificações da categoria especificada
     */
    public ArrayList<Notificacao> obterNotificacoesPorCategoria(int idUtilizador, CategoriaNotificacao categoria) {
        return nDao.listarNotificacoesPorCategoria(idUtilizador, categoria.name());
    }

    /**
     * Conta o número de notificações não lidas de um utilizador numa categoria específica.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    categoria das notificações a contar
     * @return número de notificações não lidas na categoria
     */
    public int contarNaoLidasPorCategoria(int idUtilizador, CategoriaNotificacao categoria) {
        return nDao.contarNaoLidasPorCategoria(idUtilizador, categoria.name());
    }

    /**
     * Marca como lidas todas as notificações não lidas de um utilizador numa categoria específica.
     *
     * @param idUtilizador identificador do utilizador
     * @param categoria    categoria das notificações a marcar como lidas
     */
    public void marcarComoLidasPorCategoria(int idUtilizador, CategoriaNotificacao categoria) {
        nDao.marcarComoLidasPorCategoria(idUtilizador, categoria.name());
    }
}