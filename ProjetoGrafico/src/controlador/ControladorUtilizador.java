package controlador;

import java.util.ArrayList;
import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;
import model.Cliente;
import model.Funcionario;
import model.Utilizador;
import model.Log;
import Enums.CategoriaNotificacao;
import repositorio.UtilizadorDAO;

/**
 * Controlador responsável pela lógica de negócio relacionada com utilizadores.
 * Gere operações de autenticação, registo, gestão de contas, edição de perfis
 * e consulta de logs do sistema.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ControladorUtilizador {

    private UtilizadorDAO uDao = new UtilizadorDAO();
    private ControladorNotificacao cNotificacao = new ControladorNotificacao();
    private ControladorLog cLog = new ControladorLog();

    /**
     * Efetua o login de um utilizador com base no username e password.
     *
     * @param username nome de utilizador
     * @param password palavra-passe
     * @return objeto {@link Utilizador} se as credenciais forem válidas, ou
     *         {@code null} caso contrário
     */
    public Utilizador efetuarLogin(String username, String password) {
        return uDao.validarLogin(username, password);
    }

    /**
     * Regista no log que um utilizador iniciou sessão no sistema.
     *
     * @param usernameLogado username do utilizador que iniciou sessão
     */
    public void registarInicioSessao(String usernameLogado) {
        cLog.registarAcao(usernameLogado, "Iniciou sessão no sistema.");
    }

    /**
     * Regista no log que um utilizador terminou sessão no sistema.
     *
     * @param usernameLogado username do utilizador que terminou sessão
     */
    public void registarFimSessao(String usernameLogado) {
        cLog.registarAcao(usernameLogado, "Terminou sessão no sistema.");
    }

    /**
     * Regista um novo utilizador do tipo Gestor com o estado ATIVO.
     *
     * @param nome  nome completo do gestor
     * @param email endereço de email
     * @param user  username para autenticação
     * @param pass  palavra-passe
     * @return {@code true} se o registo for efetuado com sucesso, {@code false}
     *         caso contrário
     */
    public boolean registarGestor(String nome, String email, String user, String pass) {
        Utilizador novo = new Utilizador(0, user, pass, nome, email, TipoUtilizador.GESTOR, EstadoUtilizador.ATIVO);
        return uDao.inserirUtilizador(novo);
    }

    /**
     * Regista um novo utilizador do tipo Funcionário com o estado PENDENTE.
     * Gera uma notificação para todos os gestores a informar do novo registo.
     *
     * @param nome           nome completo do funcionário
     * @param email          endereço de email
     * @param user           username para autenticação
     * @param pass           palavra-passe
     * @param nif            número de identificação fiscal
     * @param telefone       número de telefone
     * @param morada         morada do funcionário
     * @param especializacao nível de especialização (1 a 5)
     * @param dataInicio     data de início de atividade
     * @return {@code true} se o registo for efetuado com sucesso, {@code false}
     *         caso contrário
     */
    public boolean registarFuncionario(String nome, String email, String user, String pass, String nif, String telefone,
            String morada, int especializacao, String dataInicio) {
        Funcionario novo = new Funcionario(0, user, pass, nome, email, TipoUtilizador.FUNCIONARIO,
                EstadoUtilizador.PENDENTE, nif, telefone, morada, especializacao, dataInicio);
        boolean sucesso = uDao.inserirUtilizador(novo);
        if (sucesso) {
            cNotificacao.gerarNotificacaoParaGestores("Novo Funcionário registado (" + nome + "). Aguarda aprovação.",
                    CategoriaNotificacao.REGISTO);
        }
        return sucesso;
    }

    /**
     * Regista um novo utilizador do tipo Cliente com o estado PENDENTE.
     * Gera uma notificação para todos os gestores a informar do novo registo.
     *
     * @param nome           nome completo do cliente
     * @param email          endereço de email
     * @param user           username para autenticação
     * @param pass           palavra-passe
     * @param nif            número de identificação fiscal
     * @param telefone       número de telefone
     * @param morada         morada do cliente
     * @param setorAtividade setor de atividade profissional
     * @param escalao        escalão do cliente (A, B, C ou D)
     * @return {@code true} se o registo for efetuado com sucesso, {@code false}
     *         caso contrário
     */
    public boolean registarCliente(String nome, String email, String user, String pass, String nif, String telefone,
            String morada, String setorAtividade, String escalao) {
        Cliente novo = new Cliente(0, user, pass, nome, email, TipoUtilizador.CLIENTE, EstadoUtilizador.PENDENTE, nif,
                telefone, morada, setorAtividade, escalao);
        boolean sucesso = uDao.inserirUtilizador(novo);
        if (sucesso) {
            cNotificacao.gerarNotificacaoParaGestores("Novo Cliente registado (" + nome + "). Aguarda aprovação.",
                    CategoriaNotificacao.REGISTO);
        }
        return sucesso;
    }

    /**
     * Obtém todas as contas de utilizadores com estado PENDENTE ou REJEITADO
     * (excluindo gestores).
     *
     * @return lista de utilizadores com contas pendentes de aprovação
     */
    public ArrayList<Utilizador> obterContasPendentes() {
        return uDao.listarUtilizadoresPendentes();
    }

    /**
     * Altera o estado de uma conta de utilizador (ativar ou rejeitar).
     *
     * @param id             identificador do utilizador
     * @param ativar         {@code true} para ativar, {@code false} para rejeitar
     * @param usernameLogado username do gestor que realizou a ação (para log)
     */
    public void alterarEstadoConta(int id, boolean ativar, String usernameLogado) {
        uDao.alterarEstadoUtilizador(id, ativar);
        if (ativar) {
            cLog.registarAcao(usernameLogado, "Aprovou o registo do utilizador ID: " + id);
        } else {
            cLog.registarAcao(usernameLogado, "Recusou o registo do utilizador ID: " + id);
        }
    }

    /**
     * Obtém todos os funcionários com o estado ATIVO.
     *
     * @return lista de funcionários ativos
     */
    public ArrayList<Utilizador> obterFuncionariosAtivos() {
        return uDao.listarFuncionariosAtivos();
    }

    /**
     * Obtém os funcionários ativos disponíveis para atribuição a uma reparação,
     * excluindo aqueles que já rejeitaram essa reparação.
     *
     * @param idReparacao identificador da reparação
     * @return lista de funcionários disponíveis
     */
    public ArrayList<Utilizador> obterFuncionariosDisponiveis(int idReparacao) {
        return uDao.listarFuncionariosDisponiveis(idReparacao);
    }

    /**
     * Verifica se existe pelo menos um gestor registado no sistema.
     *
     * @return {@code true} se existir pelo menos um gestor, {@code false} caso
     *         contrário
     */
    public boolean existeGestor() {
        return uDao.existeGestor();
    }

    /**
     * Obtém todos os utilizadores registados no sistema.
     *
     * @return lista de todos os utilizadores
     */
    public ArrayList<Utilizador> obterTodosUtilizadores() {
        return uDao.listarTodosUtilizadores();
    }

    /**
     * Atualiza os dados do perfil de um cliente (email, password, telefone e
     * morada).
     *
     * @param idUtilizador   identificador do cliente
     * @param email          novo email
     * @param pass           nova password
     * @param telefone       novo telefone
     * @param morada         nova morada
     * @param usernameLogado username do utilizador autenticado (para log)
     * @return {@code true} se a atualização for efetuada com sucesso, {@code false}
     *         caso contrário
     */
    public boolean atualizarPerfilCliente(int idUtilizador, String email, String pass, String telefone, String morada,
            String usernameLogado) {
        boolean sucesso = uDao.atualizarPerfilCliente(idUtilizador, email, pass, telefone, morada);
        if (sucesso) {
            cLog.registarAcao(usernameLogado, "Atualizou os dados do seu próprio perfil de Cliente.");
        }
        return sucesso;
    }

    /**
     * Atualiza os dados do perfil de um funcionário (email, password, telefone e
     * morada).
     *
     * @param idUtilizador   identificador do funcionário
     * @param email          novo email
     * @param pass           nova password
     * @param telefone       novo telefone
     * @param morada         nova morada
     * @param usernameLogado username do utilizador autenticado (para log)
     * @return {@code true} se a atualização for efetuada com sucesso, {@code false}
     *         caso contrário
     */
    public boolean atualizarPerfilFuncionario(int idUtilizador, String email, String pass, String telefone,
            String morada, String usernameLogado) {
        boolean sucesso = uDao.atualizarPerfilFuncionario(idUtilizador, email, pass, telefone, morada);
        if (sucesso) {
            cLog.registarAcao(usernameLogado, "Atualizou os dados do seu próprio perfil de Funcionário.");
        }
        return sucesso;
    }

    /**
     * Lista todos os utilizadores do sistema ordenados por nome.
     *
     * @param ascendente {@code true} para ordem crescente, {@code false} para
     *                   decrescente
     * @return lista de utilizadores ordenados
     */
    public ArrayList<Utilizador> listarUtilizadoresOrdenados(boolean ascendente) {
        return uDao.listarTodosUtilizadoresOrdenadosPorNome(ascendente);
    }

    /**
     * Obtém um utilizador a partir do seu identificador.
     *
     * @param id identificador do utilizador
     * @return objeto {@link Utilizador} se encontrado, ou {@code null} caso
     *         contrário
     */
    public Utilizador obterUtilizadorPorId(int id) {
        return uDao.obterUtilizadorPorId(id);
    }

    /**
     * Pesquisa utilizadores com base num critério e termo de pesquisa.
     *
     * @param escolha critério (1 = nome, 2 = username, 3 = tipo)
     * @param termo   termo de pesquisa parcial
     * @return lista de utilizadores que correspondem aos critérios
     */
    public ArrayList<Utilizador> pesquisarUtilizadores(int escolha, String termo) {
        return uDao.pesquisarUtilizadores(escolha, termo);
    }

    /**
     * Altera o estado de uma conta de utilizador para um estado específico.
     *
     * @param idUtilizador   identificador do utilizador
     * @param novoEstado     novo estado a atribuir à conta
     * @param usernameLogado username do utilizador que realizou a ação (para log)
     * @param descricaoLog   descrição da ação para o registo de log
     */
    public void mudarEstadoConta(int idUtilizador, Enums.EstadoUtilizador novoEstado, String usernameLogado,
            String descricaoLog) {
        uDao.atualizarEstadoUtilizador(idUtilizador, novoEstado);
        cLog.registarAcao(usernameLogado, descricaoLog);
    }

    /**
     * Apaga os dados pessoais de um utilizador (remoção lógica da conta).
     *
     * @param idUtilizador   identificador do utilizador a remover
     * @param usernameLogado username do gestor que aprovou a remoção (para log)
     */
    public void apagarDadosPessoais(int idUtilizador, String usernameLogado) {
        uDao.removerUtilizador(idUtilizador);
        cLog.registarAcao(usernameLogado, "Aprovou a remoção da conta com ID: " + idUtilizador);
    }

    /**
     * Lista todos os registos de log (auditoria) do sistema.
     *
     * @return lista de todos os logs
     */
    public ArrayList<Log> listarTodosLogs() {
        return cLog.listarTodosLogs();
    }

    /**
     * Pesquisa registos de log por username do utilizador.
     *
     * @param username username ou parte do username a pesquisar
     * @return lista de logs que correspondem ao critério
     */
    public ArrayList<Log> pesquisarLogsPorUtilizador(String username) {
        return cLog.pesquisarPorUtilizador(username);
    }

    /**
     * Atualiza os dados base de qualquer utilizador por parte do gestor
     * (nome, email e password).
     *
     * @param idAlvo         identificador do utilizador a alterar
     * @param nome           novo nome
     * @param email          novo email
     * @param pass           nova password
     * @param usernameLogado username do gestor que realizou a alteração (para log)
     * @return {@code true} se a atualização for efetuada com sucesso, {@code false}
     *         caso contrário
     */
    public boolean atualizarPerfilPeloGestor(int idAlvo, String nome, String email, String pass,
            String usernameLogado) {
        boolean sucesso = uDao.atualizarDadosGenericos(idAlvo, nome, email, pass);

        if (sucesso) {
            cLog.registarAcao(usernameLogado, "O Gestor alterou os dados base do utilizador ID: " + idAlvo);
        }

        return sucesso;
    }
}