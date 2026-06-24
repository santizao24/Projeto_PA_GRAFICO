package controlador;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ArrayList;

import Enums.EstadoReparacao;
import model.Reparacao;
import Enums.CategoriaNotificacao;
import repositorio.ReparacaoDAO;

/**
 * Controlador responsável pela lógica de negócio relacionada com reparações.
 * Gere todo o ciclo de vida de uma reparação, desde a criação do pedido
 * até à conclusão e arquivamento, incluindo a verificação de prazos.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ControladorReparacao {

    private ReparacaoDAO rDao = new ReparacaoDAO();
    private ControladorNotificacao cNotificacao = new ControladorNotificacao();
    private ControladorLog cLog = new ControladorLog();
    private static String ultimaVerificacaoAtrasos = "";

    /**
     * Regista um novo pedido de reparação para um equipamento.
     * Gera automaticamente o número de reparação e notifica os gestores.
     *
     * @param idEquipamento  identificador do equipamento a reparar
     * @param usernameLogado username do utilizador autenticado (para log)
     * @param obs            observações do cliente sobre o pedido
     */
    public void registarNovoPedido(int idEquipamento, String usernameLogado, String obs) {
        LocalDateTime agora = LocalDateTime.now();
        String dataFormatada = agora.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        int numSequencial = rDao.contarReparacoes() + 1;
        String numRep = numSequencial + dataFormatada;
        String dataAtualBD = agora.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        String observacoes = (obs != null && !obs.isEmpty()) ? obs : null;
        Reparacao novaReparacao = new Reparacao(0, numRep, idEquipamento, 0, dataAtualBD, null, null, 0, 0.0,
                EstadoReparacao.CRIADO, observacoes);
        rDao.inserirReparacao(novaReparacao);
        cNotificacao.gerarNotificacaoParaGestores(
                "Novo pedido de reparação submetido (Equipamento ID: " + idEquipamento + ").",
                CategoriaNotificacao.REPARACAO);
        cLog.registarAcao(usernameLogado,
                "Submeteu um pedido de reparação para o equipamento ID: " + idEquipamento + ".");
    }

    /**
     * Lista todos os pedidos de reparação no estado CRIADO (pendentes de
     * aprovação).
     *
     * @return lista de reparações pendentes
     */
    public ArrayList<Reparacao> listarPedidosPendentes() {
        return rDao.listarReparacoesPendentes();
    }

    /**
     * Gere o estado de uma reparação (aceitar, rejeitar ou iniciar).
     * Gera notificações e registos de log conforme a ação realizada.
     *
     * @param idReparacao    identificador da reparação
     * @param idFuncionario  identificador do funcionário a atribuir (0 se não
     *                       aplicável)
     * @param novoEstado     novo estado a atribuir (ACEITE, REJEITADO ou DECORRER)
     * @param usernameLogado username do utilizador autenticado (para log)
     */
    public void gerirEstadoReparacao(int idReparacao, int idFuncionario, String novoEstado, String usernameLogado) {

        String estadoAGravar = novoEstado;
        if (novoEstado.equals(EstadoReparacao.REJEITADO.name())) {
            estadoAGravar = EstadoReparacao.ARQUIVADO.name();
        }

        rDao.atualizarEstadoReparacao(idReparacao, idFuncionario, estadoAGravar);

        if (novoEstado.equals(EstadoReparacao.ACEITE.name())) {
            cNotificacao.gerarNotificacao(idFuncionario,
                    "Foi-te atribuída uma nova reparação (Processo ID: " + idReparacao + ").",
                    CategoriaNotificacao.GERAL);
            cLog.registarAcao(usernameLogado,
                    "Atribuiu a reparação ID: " + idReparacao + " ao funcionário ID: " + idFuncionario);

        } else if (novoEstado.equals(EstadoReparacao.REJEITADO.name())) {
            int idCliente = rDao.obterIdClienteDaReparacao(idReparacao);
            if (idCliente > 0) {
                cNotificacao.gerarNotificacao(idCliente,
                        "O teu pedido de reparação (Processo ID: " + idReparacao
                                + ") foi rejeitado pelo Gestor e o processo foi arquivado.",
                        CategoriaNotificacao.GERAL);
            }
            cLog.registarAcao(usernameLogado, "Rejeitou e arquivou a reparação ID: " + idReparacao);

        } else if (novoEstado.equals(EstadoReparacao.DECORRER.name())) {
            cLog.registarAcao(usernameLogado, "Iniciou a reparação ID: " + idReparacao);
        }
    }

    /**
     * Lista as reparações atribuídas a um funcionário filtradas por estado.
     *
     * @param idFuncionario identificador do funcionário
     * @param estado        estado das reparações a filtrar
     * @return lista de reparações do funcionário no estado indicado
     */
    public ArrayList<Reparacao> listarReparacoesPorEstado(int idFuncionario, String estado) {
        return rDao.listarReparacoesPorFuncionario(idFuncionario, estado);
    }

    /**
     * Conclui o processo de reparação, registando a data de fim, tempo decorrido,
     * custo e observações.
     * Calcula automaticamente o tempo decorrido e notifica o cliente da conclusão.
     *
     * @param rep            objeto Reparação a concluir
     * @param custo          custo final da reparação em euros
     * @param obs            observações finais sobre o trabalho realizado
     * @param usernameLogado username do utilizador autenticado (para log)
     */
    public void concluirReparacaoFinal(Reparacao rep, double custo, String obs, String usernameLogado) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date dataInicioAUsar;
            if (rep.getDataInicio() != null) {
                dataInicioAUsar = sdf.parse(rep.getDataInicio());
            } else {
                dataInicioAUsar = sdf.parse(rep.getDataCriacao());
            }

            Date agora = new Date();
            String dataFim = sdf.format(agora);

            long diferencaMilissegundos = agora.getTime() - dataInicioAUsar.getTime();
            int minutosDecorridos = (int) (diferencaMilissegundos / (1000 * 60));

            rDao.concluirReparacao(rep.getIdReparacao(), dataFim, minutosDecorridos, custo, obs);

            int idCliente = rDao.obterIdClienteDaReparacao(rep.getIdReparacao());

            if (idCliente > 0) {
                cNotificacao.gerarNotificacao(idCliente,
                        "Boa notícia! A tua reparação (Ref: " + rep.getNumReparacao() + ") foi concluída.",
                        CategoriaNotificacao.GERAL);
            }

            cLog.registarAcao(usernameLogado,
                    "Concluiu a reparação ID: " + rep.getIdReparacao() + " em " + minutosDecorridos + " minutos.");

        } catch (Exception e) {
            System.out.println("Erro técnico ao calcular o tempo da reparação.");
        }
    }

    /**
     * Obtém todas as reparações no estado FINALIZADO, prontas a serem arquivadas.
     *
     * @return lista de reparações concluídas
     */
    public ArrayList<Reparacao> obterReparacoesProntasAArquivar() {
        return rDao.listarReparacoesConcluidas();
    }

    /**
     * Arquiva uma reparação concluída, alterando o seu estado para ARQUIVADO.
     *
     * @param idReparacao identificador da reparação a arquivar
     */
    public void arquivarReparacao(int idReparacao) {
        rDao.arquivarReparacao(idReparacao);
    }

    /**
     * Lista todas as reparações do sistema ordenadas por um critério.
     *
     * @param escolha    critério de ordenação (1 = data de criação, 2 = número de
     *                   reparação)
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de reparações ordenadas
     */
    public ArrayList<Reparacao> listarReparacoesOrdenadas(int escolha, boolean ascendente) {
        return rDao.listarTodasReparacoesOrdenadas(escolha, ascendente);
    }

    /**
     * Lista todas as reparações que não se encontram finalizadas (CRIADO, ACEITE,
     * DECORRER).
     *
     * @return lista de reparações não finalizadas
     */
    public ArrayList<Reparacao> listarReparacoesNaoFinalizadas() {
        return rDao.listarReparacoesNaoFinalizadas();
    }

    /**
     * Pesquisa reparações com base num critério e termo de pesquisa.
     *
     * @param escolha critério (1 = número, 2 = estado, 3 = nome do cliente)
     * @param termo   termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoes(int escolha, String termo) {
        return rDao.pesquisarReparacoes(escolha, termo);
    }

    /**
     * Lista as reparações de um cliente ordenadas por um critério.
     *
     * @param IdCliente  identificador do cliente
     * @param escolha    critério de ordenação (1 = data, 2 = número)
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de reparações do cliente ordenadas
     */
    public ArrayList<Reparacao> listarReparacoesClienteOrdenadas(int IdCliente, int escolha, boolean ascendente) {
        return rDao.listarReparacoesClienteOrdenadas(IdCliente, escolha, ascendente);
    }

    /**
     * Pesquisa reparações de um cliente com base num critério e termo.
     *
     * @param IdCliente identificador do cliente
     * @param escolha   critério (1 = número, 2 = estado)
     * @param termo     termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoesCliente(int IdCliente, int escolha, String termo) {
        return rDao.pesquisarReparacoesCliente(IdCliente, escolha, termo);
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
        return rDao.listarReparacoesFuncionarioOrdenadas(IdFuncionario, escolha, ascendente);
    }

    /**
     * Pesquisa reparações de um funcionário com base num critério e termo.
     *
     * @param IdFuncionario identificador do funcionário
     * @param escolha       critério (1 = número, 2 = estado)
     * @param termo         termo de pesquisa parcial
     * @return lista de reparações que correspondem aos critérios
     */
    public ArrayList<Reparacao> pesquisarReparacoesFuncionario(int IdFuncionario, int escolha, String termo) {
        return rDao.pesquisarReparacoesFuncionario(IdFuncionario, escolha, termo);
    }

    /**
     * Pesquisa reparações criadas num intervalo de datas.
     *
     * @param aDataInicio data inicial do intervalo (formato YYYY-MM-DD)
     * @param aDataFim    data final do intervalo (formato YYYY-MM-DD)
     * @return lista de reparações criadas no intervalo especificado
     */
    public ArrayList<Reparacao> pesquisarReparacoesPorData(String aDataInicio, String aDataFim) {
        return rDao.pesquisarReparacoesPorData(aDataInicio, aDataFim);
    }

    /**
     * Verifica se existem reparações com mais de 10 dias sem finalizar.
     * Gera notificação de alerta para todos os gestores se forem encontradas.
     * Esta verificação é executada no máximo uma vez por dia.
     */
    public void verificarReparacoesAtrasadas() {
        String dataHoje = java.time.LocalDate.now().toString();

        if (dataHoje.equals(ultimaVerificacaoAtrasos)) {
            return;
        }

        ArrayList<Reparacao> atrasadas = rDao.obterReparacoesAtrasadas();

        if (!atrasadas.isEmpty()) {
            String mensagem = "ALERTA: Existem " + atrasadas.size()
                    + " reparação(ões) com mais de 10 dias sem finalizar!";

            cNotificacao.gerarNotificacaoParaGestores(mensagem, CategoriaNotificacao.PRAZO);
        }

        ultimaVerificacaoAtrasos = dataHoje;
    }

    /**
     * Regista a rejeição de uma reparação por parte de um funcionário.
     * Devolve o pedido ao gestor e gera notificação de alerta.
     *
     * @param idReparacao    identificador da reparação a rejeitar
     * @param idFuncionario  identificador do funcionário que rejeita
     * @param usernameLogado username do utilizador autenticado (para log)
     */
    public void rejeitarReparacaoPorFuncionario(int idReparacao, int idFuncionario, String usernameLogado) {
        rDao.registarRejeicao(idReparacao, idFuncionario);
        rDao.atualizarEstadoReparacao(idReparacao, 0, EstadoReparacao.CRIADO.name());
        cNotificacao.gerarNotificacaoParaGestores(
                "ALERTA: O funcionário (ID: " + idFuncionario
                        + ") rejeitou a reparação (Processo: " + idReparacao + ").",
                CategoriaNotificacao.REJEICAO);
        cLog.registarAcao(usernameLogado, "Rejeitou o serviço da reparação ID: " + idReparacao);
    }

    /**
     * Obtém todas as reparações associadas aos equipamentos de um cliente.
     *
     * @param idCliente identificador do cliente
     * @return lista de reparações do cliente
     */
    public ArrayList<Reparacao> obterMinhasReparacoes(int idCliente) {
        return rDao.obterReparacoesPorCliente(idCliente);
    }

    /**
     * Obtém todas as reparações registadas no sistema.
     *
     * @return lista de todas as reparações
     */
    public ArrayList<Reparacao> obterTodasReparacoes() {
        return rDao.obterTodasReparacoes();
    }
}