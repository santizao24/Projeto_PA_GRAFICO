package view;

import java.util.Scanner;

import util.Validacoes;

import Enums.EstadoReparacao;
import Enums.CategoriaNotificacao;

import java.util.Iterator;
import java.util.ArrayList;

import controlador.ControladorEquipamento;
import controlador.ControladorNotificacao;
import controlador.ControladorReparacao;
import controlador.ControladorUtilizador;
import model.Equipamento;
import model.Log;
import model.Notificacao;
import model.Peca;
import model.Reparacao;
import model.Utilizador;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;

/**
 * Classe responsável pela interface de interação (consola) com um Utilizador do
 * tipo Gestor.
 * Apresenta o menu principal e submenus para gestão global do sistema,
 * incluindo
 * ativação de contas, gestão de permissões, alocação de reparações,
 * monitorização
 * de peças/stocks, e consulta de relatórios/logs e notificações.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ViewGestor {

    private Utilizador utilizadorLogado;
    private Scanner ler;
    private ControladorUtilizador cUtilizador;
    private ControladorEquipamento cEquipamento;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    /**
     * Construtor da ViewGestor.
     *
     * @param aUtilizador   utilizador (Gestor) atualmente autenticado
     * @param aScanner      objeto Scanner para leitura de entradas
     * @param aCUtilizador  controlador de utilizadores
     * @param aCEquipamento controlador de equipamentos
     * @param aCReparacao   controlador de reparações
     * @param aCNotificacao controlador de notificações
     */
    public ViewGestor(Utilizador aUtilizador, Scanner aScanner, ControladorUtilizador aCUtilizador,
            ControladorEquipamento aCEquipamento, ControladorReparacao aCReparacao,
            ControladorNotificacao aCNotificacao) {
        utilizadorLogado = aUtilizador;
        ler = aScanner;
        cUtilizador = aCUtilizador;
        cEquipamento = aCEquipamento;
        cReparacao = aCReparacao;
        cNotificacao = aCNotificacao;
    }

    /**
     * Apresenta o menu principal de Gestor e processa as opções selecionadas.
     * Inclui a verificação automática de reparações atrasadas logo ao inicio.
     * O ciclo mantém-se até o utilizador escolher a opção de voltar (logout).
     */
    public void mostrarMenu() {
        int opcao = -1;
        cReparacao.verificarReparacoesAtrasadas();
        while (opcao != 0) {
            if (utilizadorLogado == null)
                return;

            System.out.println("\n--- MENU GESTOR ---");
            int naoLidas = cNotificacao.contarNaoLidas(utilizadorLogado.getIdUtilizador());
            if (naoLidas > 0) {
                System.out.println("\n*** TENS " + naoLidas + " NOTIFICAÇÃO(ÕES) NÃO LIDA(S)! ***");
            }
            System.out.println("1 - Ativar Contas");
            System.out.println("2 - Gerir Pedidos de Reparação");
            System.out.println("3 - Criar Novo Gestor");
            System.out.println("4 - Gerir Stock de Peças");
            System.out.println("5 - Arquivar Processos");
            System.out.println("6 - Editar Dados de Qualquer Utilizador");
            System.out.println("7 - Listagens e Pesquisas");
            System.out.println("8 - Ver notificações");
            System.out.println("9 - Ver logs");
            System.out.println("10 - Ativar ou Inativar Contas");
            System.out.println("11 - Gerir pedidos de remoção de conta");
            System.out.println("12 - Solicitar Remoção de Conta");
            System.out.println("0 - Voltar (Logout)");
            System.out.print("Escolha: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    validarContas();
                    break;
                case 2:
                    gerirPedidosReparacao();
                    break;
                case 3:
                    criarNovoGestor();
                    break;
                case 4:
                    inserirPecasArmazem();
                    break;
                case 5:
                    menuArquivarReparacao();
                    break;
                case 6:
                    editarQualquerUtilizador();
                    break;
                case 7:
                    menuListagensGestor();
                    break;
                case 8:
                    verNotificacoes();
                    break;
                case 9:
                    menuLogs();
                    break;
                case 10:
                    alterarEstadoContas();
                    break;
                case 11:
                    gerirPedidosRemocao();
                    break;
                case 12:
                    solicitarRemocao();
                    break;
                case 0:
                    cUtilizador.registarFimSessao(utilizadorLogado.getLogin());
                    System.out.println(
                            "\nAdeus, " + utilizadorLogado.getNome() + " (" + utilizadorLogado.getTipo() + ")");
                    utilizadorLogado = null;
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void menuListagensGestor() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- SUBMENU: LISTAGENS E PESQUISAS ---");
            System.out.println("1 - Listar Utilizadores Ordenados por Nome");
            System.out.println("2 - Listar Pedidos de Reparação Ordenados");
            System.out.println("3 - Listar Reparações Não Finalizadas");
            System.out.println("4 - Pesquisar Pedidos de Reparação");
            System.out.println("5 - Pesquisar Equipamentos");
            System.out.println("6 - Pesquisar Utilizadores");
            System.out.println("7 - Pesquisar Reparações por Intervalo de Tempo");
            System.out.println("8 - Consultar todas as reparações");
            System.out.println("0 - Voltar ao Menu Anterior");
            System.out.print("Escolha: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    listarUtilizadoresOrdenados();
                    break;
                case 2:
                    listarReparacoesOrdenadas();
                    break;
                case 3:
                    listarReparacoesNaoFinalizadas();
                    break;
                case 4:
                    pesquisarReparacoesGestor();
                    break;
                case 5:
                    pesquisarEquipamentosGestor();
                    break;
                case 6:
                    pesquisarUtilizadoresGestor();
                    break;
                case 7:
                    pesquisarReparacoesPorIntervaloTempo();
                    break;
                case 8:
                    consultarTodasReparacoes();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void listarUtilizadoresOrdenados() {
        System.out.println("\n--- LISTAR UTILIZADORES ORDENADOS POR NOME ---");
        System.out.println("Como deseja ordenar a lista?");
        System.out.println("1 - Ordem Crescente");
        System.out.println("2 - Ordem Decrescente");
        System.out.print("Escolha: ");
        int opcaoOrdem = Validacoes.lerInteiro(ler);

        boolean ascendente = (opcaoOrdem == 1);

        ArrayList<Utilizador> lista = cUtilizador.listarUtilizadoresOrdenados(ascendente);

        if (lista.isEmpty()) {
            System.out.println("Não existem utilizadores registados no sistema.");
            return;
        }

        System.out.println("\n--- RESULTADOS ---");
        Iterator<Utilizador> it = lista.iterator();

        int contador = 0;

        while (it.hasNext()) {
            Utilizador u = it.next();
            System.out.println("ID: " + u.getIdUtilizador() +
                    " | Nome: " + u.getNome() +
                    " | Username: " + u.getLogin() +
                    " | Tipo: (" + u.getTipo() + ")");

            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out
                        .print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();

                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }

                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }
    }

    private void validarContas() {
        ArrayList<Utilizador> pendentes = cUtilizador.obterContasPendentes();

        if (pendentes.isEmpty()) {
            System.out.println("\nNão existem contas pendentes de clientes ou funcionários.");
            return;
        }

        System.out.println("\n--- Contas Pendentes ---");
        Iterator<Utilizador> it = pendentes.iterator();
        while (it.hasNext()) {
            Utilizador u = it.next();
            System.out.println("ID: " + u.getIdUtilizador() + " | Nome: " + u.getNome() + " | Email: " + u.getEmail()
                    + " | Tipo: (" + u.getTipo() + ")" + " | Estado: " + u.getEstado());
        }

        System.out.print("Insira o ID do utilizador que deseja modificar: ");
        int id = Validacoes.lerInteiro(ler);

        System.out.println("O que deseja fazer?");
        System.out.println("1- Ativar Conta");
        System.out.println("2- Rejeitar Conta");
        System.out.println("3- Voltar");
        System.out.print("Escolha: ");
        int opcao = Validacoes.lerInteiro(ler);

        switch (opcao) {
            case 1:
                cUtilizador.alterarEstadoConta(id, true, utilizadorLogado.getLogin());
                break;
            case 2:
                cUtilizador.alterarEstadoConta(id, false, utilizadorLogado.getLogin());
                break;
            case 3:
                return;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void gerirPedidosReparacao() {
        ArrayList<Reparacao> pendentes = cReparacao.listarPedidosPendentes();

        if (pendentes.isEmpty()) {
            System.out.println("\nNão existem pedidos de reparação pendentes.");
            return;
        }

        System.out.println("\n--- Pedidos de Reparação a Aguardar Aprovação ---");
        Iterator<Reparacao> itRep = pendentes.iterator();
        while (itRep.hasNext()) {
            Reparacao r = itRep.next();
            System.out.println("ID: " + r.getIdReparacao() + " | Código Real: " + r.getNumReparacao()
                    + " | Equipamento ID: " + r.getIdEquipamento());
        }

        System.out.print("\nInsira o ID da Reparação a gerir (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0)
            return;

        boolean idRepValido = false;
        Iterator<Reparacao> itVerificaRep = pendentes.iterator();
        while (itVerificaRep.hasNext()) {
            Reparacao rVerifica = itVerificaRep.next();
            if (rVerifica.getIdReparacao() == idEscolhido) {
                idRepValido = true;
            }
        }

        if (!idRepValido) {
            System.out.println("O ID introduzido não corresponde a nenhum pedido pendente.");
            return;
        }

        System.out.println("O que deseja fazer com este pedido?");
        System.out.println("1 - Aceitar Pedido (Atribuir Funcionário)");
        System.out.println("2 - Rejeitar Pedido");
        System.out.print("Escolha: ");
        int acao = Validacoes.lerInteiro(ler);

        if (acao == 1) {
            ArrayList<Utilizador> funcionarios = cUtilizador.obterFuncionariosDisponiveis(idEscolhido);

            if (funcionarios.isEmpty()) {
                System.out.println("Não há funcionários ativos no sistema para atribuir! Ative um primeiro.");
                return;
            }

            System.out.println("\n--- Funcionários Disponíveis ---");
            Iterator<Utilizador> itFunc = funcionarios.iterator();
            while (itFunc.hasNext()) {
                Utilizador f = itFunc.next();
                System.out.println("ID: " + f.getIdUtilizador() + " | Nome: " + f.getNome());
            }

            System.out.print("Insira o ID do Funcionário a atribuir: ");
            int idFunc = Validacoes.lerInteiro(ler);

            boolean funcValido = false;
            Iterator<Utilizador> itVerificaFunc = funcionarios.iterator();
            while (itVerificaFunc.hasNext()) {
                Utilizador fVerifica = itVerificaFunc.next();
                if (fVerifica.getIdUtilizador() == idFunc) {
                    funcValido = true;
                }
            }

            if (!funcValido) {
                System.out.println("O ID introduzido não pertence a nenhum funcionário disponível.");
                return;
            }

            cReparacao.gerirEstadoReparacao(idEscolhido, idFunc, EstadoReparacao.ACEITE.name(),
                    utilizadorLogado.getLogin());
        } else if (acao == 2) {
            cReparacao.gerirEstadoReparacao(idEscolhido, 0, EstadoReparacao.REJEITADO.name(),
                    utilizadorLogado.getLogin());
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void criarNovoGestor() {
        System.out.println("\n--- CRIAR CONTA DE NOVO GESTOR ---");
        System.out.print("Nome: ");
        String nome = ler.nextLine();

        String email;
        do {
            System.out.print("Email: ");
            email = ler.nextLine();
            if (!Validacoes.emailValido(email)) {
                System.out.println("Formato de email inválido! Use o formato: designacao@entidade.dominio");
            }
        } while (!Validacoes.emailValido(email));

        String user;
        do {
            System.out.print("Username (Login): ");
            user = ler.nextLine();
            if (user.isEmpty()) {
                System.out.println("O username não pode estar vazio!");
            }
        } while (user.isEmpty());

        String pass;
        do {
            System.out.print("Password: ");
            pass = ler.nextLine();
            if (pass.isEmpty()) {
                System.out.println("A password não pode estar vazia!");
            }
        } while (pass.isEmpty());

        boolean sucesso = cUtilizador.registarGestor(nome, email, user, pass);

        if (sucesso) {
            System.out.println("Novo Gestor registado e ativado com sucesso!");
        } else {
            System.out.println("Erro ao criar o gestor! O username ou email já está em uso.");
        }
    }

    private void inserirPecasArmazem() {
        System.out.println("\n--- ENTRADA DE PEÇAS NO ARMAZÉM ---");

        ArrayList<Peca> todasPecas = cReparacao.listarTodasPecas();
        if (todasPecas.isEmpty()) {
            System.out.println("Ainda não existem peças registadas no sistema.");
        } else {
            System.out.println("\n--- Peças Existentes no Armazém ---");
            Iterator<Peca> itPecas = todasPecas.iterator();
            while (itPecas.hasNext()) {
                Peca p = itPecas.next();
                System.out.println("Código: " + p.getCodigoInterno()
                        + " | Designação: " + p.getDesignacao()
                        + " | Fabricante: " + p.getFabricante()
                        + " | Stock: " + p.getQuantidade());
            }
            System.out.println("-----------------------------------");
        }

        String cod;
        do {
            System.out.print("Código Interno da Peça: ");
            cod = ler.nextLine();
            if (cod.isEmpty()) {
                System.out.println("O código da peça não pode estar vazio!");
            }
        } while (cod.isEmpty());

        Peca pecaExistente = cReparacao.obterPecaExistente(cod);

        String des;
        String fab;

        if (pecaExistente != null) {
            System.out.println(
                    "Peça encontrada: " + pecaExistente.getDesignacao() + " (" + pecaExistente.getFabricante() + ")");
            System.out.println("Stock atual: " + pecaExistente.getQuantidade() + " unidades.");
            des = pecaExistente.getDesignacao();
            fab = pecaExistente.getFabricante();
        } else {
            System.out.println("Peça NOVA! Por favor preencha os dados de registo.");
            System.out.print("Designação (ex: Ecrã LCD, Bateria): ");
            des = ler.nextLine();
            System.out.print("Fabricante: ");
            fab = ler.nextLine();
        }

        System.out.print("Quantidade a dar entrada: ");
        int qtd = Validacoes.lerInteiro(ler);

        if (qtd <= 0) {
            System.out.println("A quantidade tem de ser maior que zero!");
            return;
        }

        String resultado = cReparacao.gerirEntradaPeca(cod, des, fab, qtd);
        System.out.println(resultado);
    }

    private void menuArquivarReparacao() {
        System.out.println("\n--- ARQUIVAR REPARAÇÃO ---");

        ArrayList<Reparacao> concluidas = cReparacao.obterReparacoesProntasAArquivar();

        if (concluidas.isEmpty()) {
            System.out.println("Não existem reparações no estado 'FINALIZADO' para arquivar.");
            return;
        }

        System.out.println("Lista de Reparações Concluídas:");

        Iterator<Reparacao> itListagem = concluidas.iterator();
        while (itListagem.hasNext()) {
            Reparacao r = itListagem.next();
            System.out.println("ID: " + r.getIdReparacao() + " | Código: " + r.getNumReparacao() + " | Custo: "
                    + r.getCusto() + "euros");
        }

        System.out.print("\nIntroduza o ID da Reparação a arquivar (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0) {
            System.out.println("Operação cancelada.");
            return;
        }

        boolean idValido = false;
        Iterator<Reparacao> it = concluidas.iterator();

        while (it.hasNext() && !idValido) {

            Reparacao r = it.next();
            if (r.getIdReparacao() == idEscolhido) {
                idValido = true;
            }
        }

        if (idValido) {
            cReparacao.arquivarReparacao(idEscolhido);
            System.out.println("Reparação " + idEscolhido + " arquivada com sucesso! O processo está encerrado.");
        } else {
            System.out.println("O ID introduzido não corresponde a uma reparação concluída válida.");
        }
    }

    private void editarQualquerUtilizador() {
        System.out.println("\n--- GERIR UTILIZADORES (MODO GESTOR) ---");

        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        System.out.println("Lista de todos os utilizadores do sistema:");
        Iterator<Utilizador> it = todos.iterator();
        while (it.hasNext()) {
            Utilizador u = it.next();
            System.out.println("ID: " + u.getIdUtilizador() + " | Nome: " + u.getNome() + " | Tipo: " + u.getTipo());
        }

        System.out.print("\nIntroduza o ID do utilizador que deseja alterar (ou 0 para cancelar): ");
        int idAlvo = Validacoes.lerInteiro(ler);

        if (idAlvo == 0)
            return;

        Utilizador utilizadorAlvo = null;
        Iterator<Utilizador> itBusca = todos.iterator();

        while (itBusca.hasNext() && utilizadorAlvo == null) {
            Utilizador u = itBusca.next();
            if (u.getIdUtilizador() == idAlvo) {
                utilizadorAlvo = u;
            }
        }

        if (utilizadorAlvo == null) {
            System.out.println("Não existe nenhum utilizador com o ID " + idAlvo + ".");
            return;
        }

        System.out.println("\n--- A editar: " + utilizadorAlvo.getNome() + " ---");

        System.out.println("Nome atual: " + utilizadorAlvo.getNome());
        System.out.print("Novo Nome: ");
        String novoNome = ler.nextLine();

        System.out.println("Email atual: " + utilizadorAlvo.getEmail());
        System.out.print("Novo Email: ");
        String novoEmail = ler.nextLine();

        String novaPass;
        do {
            System.out.print("Nova Password: ");
            novaPass = ler.nextLine();
            if (novaPass.isEmpty()) {
                System.out.println("A password não pode estar vazia!");
            }
        } while (novaPass.isEmpty());

        boolean sucesso = cUtilizador.atualizarPerfilPeloGestor(idAlvo, novoNome, novoEmail, novaPass,
                utilizadorLogado.getLogin());

        if (sucesso) {
            System.out.println("Dados base do utilizador alterados com sucesso pelo Gestor!");

            if (utilizadorLogado.getIdUtilizador() == idAlvo) {
                utilizadorLogado.setNome(novoNome);
                utilizadorLogado.setEmail(novoEmail);
                utilizadorLogado.setPassword(novaPass);
            }
        } else {
            System.out.println("ERRO: Ocorreu uma falha ao alterar os dados. Verifique se o Email já existe.");
        }
    }

    private void listarReparacoesOrdenadas() {
        System.out.println("\n--- LISTAR REPARAÇÕES ORDENADAS ---");
        System.out.println("Qual o critério de ordenação?");
        System.out.println("1 - Data de Criação");
        System.out.println("2 - Número da Reparação");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha != 1 && escolha != 2) {
            System.out.println("Opção inválida!");
            return;
        }

        System.out.println("\nComo deseja ordenar a lista?");
        System.out.println("1 - Ordem Crescente");
        System.out.println("2 - Ordem Decrescente");
        System.out.print("Escolha: ");
        int opcaoOrdem = Validacoes.lerInteiro(ler);

        boolean ascendente = (opcaoOrdem == 1);

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesOrdenadas(escolha, ascendente);

        if (lista.isEmpty()) {
            System.out.println("Não existem reparações registadas no sistema.");
            return;
        }

        System.out.println("\n--- RESULTADOS ---");
        Iterator<Reparacao> it = lista.iterator();

        int contador = 0;

        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("ID: " + r.getIdReparacao() +
                    " | Número: " + r.getNumReparacao() +
                    " | Data: " + r.getDataCriacao() +
                    " | Estado: " + r.getEstado());

            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out
                        .print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();

                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }

                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }
    }

    private void listarReparacoesNaoFinalizadas() {
        System.out.println("\n--- REPARAÇÕES NÃO FINALIZADAS ---");

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesNaoFinalizadas();

        if (lista.isEmpty()) {
            System.out.println("Não existem reparações pendentes ou em curso no momento.");
            return;
        }

        System.out.println("Lista de processos em andamento:");
        Iterator<Reparacao> it = lista.iterator();

        int contador = 0;

        while (it.hasNext()) {
            Reparacao r = it.next();

            String funcInfo = (r.getIdUtilizador() > 0) ? " | Func. ID: " + r.getIdUtilizador()
                    : " | (Sem Funcionário)";

            System.out.println("ID: " + r.getIdReparacao() +
                    " | Número: " + r.getNumReparacao() +
                    " | Estado: " + r.getEstado() +
                    funcInfo);

            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out.print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();

                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }

                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }
    }

    private void pesquisarReparacoesGestor() {
        System.out.println("\n--- PESQUISAR REPARAÇÕES ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Número da Reparação");
        System.out.println("2 - Por Estado (ex: CRIADO, DECORRER, FINALIZADO)");
        System.out.println("3 - Por Nome do Cliente");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha < 1 || escolha > 3) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar: ");
        String termo = ler.nextLine();

        ArrayList<Reparacao> resultados = cReparacao.pesquisarReparacoes(escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum resultado encontrado para a pesquisa: '" + termo + "'");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Reparacao> it = resultados.iterator();
        while (it.hasNext()) {
            Reparacao r = it.next();

            String nomeApresentar = "Sem Cliente Atribuído";

            if (r.getIdUtilizador() > 0) {
                Utilizador clienteEncontrado = cUtilizador.obterUtilizadorPorId(r.getIdUtilizador());
                if (clienteEncontrado != null) {
                    nomeApresentar = clienteEncontrado.getNome();
                }
            }

            System.out.println("ID: " + r.getIdReparacao() +
                    " | Número: " + r.getNumReparacao() +
                    " | Cliente ID: " + r.getIdUtilizador() +
                    " | Cliente: " + nomeApresentar +
                    " | Estado: " + r.getEstado() +
                    " | Data: " + r.getDataCriacao());

        }
    }

    private void pesquisarEquipamentosGestor() {
        System.out.println("\n--- PESQUISAR EQUIPAMENTOS ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Marca");
        System.out.println("2 - Por Código de Modelo");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha < 1 || escolha > 2) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar: ");
        String termo = ler.nextLine();

        ArrayList<Equipamento> resultados = cEquipamento.pesquisarEquipamentos(escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum equipamento encontrado para a pesquisa: '" + termo + "'");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Equipamento> it = resultados.iterator();
        while (it.hasNext()) {
            Equipamento eq = it.next();
            System.out.println("ID: " + eq.getIdEquipamento() +
                    " | Marca: " + eq.getMarca() +
                    " | Modelo: " + eq.getModelo() +
                    " | SKU: " + eq.getSku() +
                    " | ID do Dono: " + eq.getIdUtilizador());
        }
    }

    private void pesquisarUtilizadoresGestor() {
        System.out.println("\n--- PESQUISAR UTILIZADORES ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Nome");
        System.out.println("2 - Por Username");
        System.out.println("3 - Por Tipo (ex: GESTOR, CLIENTE, FUNCIONARIO)");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha < 1 || escolha > 3) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar (pesquisa parcial ativada): ");
        String termo = ler.nextLine();

        ArrayList<Utilizador> resultados = cUtilizador.pesquisarUtilizadores(escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum utilizador encontrado para a pesquisa: '" + termo + "'");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Utilizador> it = resultados.iterator();
        while (it.hasNext()) {
            Utilizador u = it.next();
            System.out.println("ID: " + u.getIdUtilizador() +
                    " | Nome: " + u.getNome() +
                    " | Username: " + u.getLogin() +
                    " | Tipo: " + u.getTipo() +
                    " | Estado: " + u.getEstado());
        }
    }

    private void pesquisarReparacoesPorIntervaloTempo() {
        System.out.println("\n--- PESQUISAR REPARAÇÕES POR DATA ---");
        System.out.print("Introduza a Data Inicial (formato YYYY-MM-DD): ");
        String dataInicio = ler.nextLine();

        System.out.print("Introduza a Data Final (formato YYYY-MM-DD): ");
        String dataFim = ler.nextLine();

        ArrayList<Reparacao> resultados = cReparacao.pesquisarReparacoesPorData(dataInicio, dataFim);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum pedido de reparação submetido entre " + dataInicio + " e " + dataFim + ".");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Reparacao> it = resultados.iterator();
        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("ID: " + r.getIdReparacao() +
                    " | Número: " + r.getNumReparacao() +
                    " | Data de Criação: " + r.getDataCriacao() +
                    " | Estado: " + r.getEstado());
        }
    }

    private void verNotificacoes() {
        int opcao = -1;

        while (opcao != 0) {
            int id = utilizadorLogado.getIdUtilizador();

            int nRegisto = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.REGISTO);
            int nReparacao = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.REPARACAO);
            int nStock = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.STOCK);
            int nPrazo = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.PRAZO);
            int nRejeicao = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.REJEICAO);
            int nRemocao = cNotificacao.contarNaoLidasPorCategoria(id, CategoriaNotificacao.REMOCAO);

            System.out.println("\n--- DASHBOARD DE NOTIFICAÇÕES ---");
            System.out.println("1 - Registos Novos (" + nRegisto + " novas)");
            System.out.println("2 - Novas Reparações (" + nReparacao + " novas)");
            System.out.println("3 - Alertas de Stock (" + nStock + " novas)");
            System.out.println("4 - Prazos Ultrapassados (" + nPrazo + " novas)");
            System.out.println("5 - Reparações Rejeitadas (" + nRejeicao + " novas)");
            System.out.println("6 - Remoções de Conta (" + nRemocao + " novas)");
            System.out.println("0 - Voltar");
            System.out.print("Escolha uma categoria para ler: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    listarMensagensDaCategoria(CategoriaNotificacao.REGISTO);
                    break;
                case 2:
                    listarMensagensDaCategoria(CategoriaNotificacao.REPARACAO);
                    break;
                case 3:
                    listarMensagensDaCategoria(CategoriaNotificacao.STOCK);
                    break;
                case 4:
                    listarMensagensDaCategoria(CategoriaNotificacao.PRAZO);
                    break;
                case 5:
                    listarMensagensDaCategoria(CategoriaNotificacao.REJEICAO);
                    break;
                case 6:
                    listarMensagensDaCategoria(CategoriaNotificacao.REMOCAO);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        }
    }

    private void listarMensagensDaCategoria(CategoriaNotificacao categoria) {
        System.out.println("\n--- MENSAGENS DE " + categoria.name() + " ---");

        ArrayList<Notificacao> lista = cNotificacao.obterNotificacoesPorCategoria(utilizadorLogado.getIdUtilizador(),
                categoria);

        if (lista.isEmpty()) {
            System.out.println("Não tens nenhuma notificação no teu histórico para esta categoria.");
            return;
        }

        Iterator<Notificacao> it = lista.iterator();
        int contador = 0;

        while (it.hasNext()) {
            Notificacao n = it.next();
            System.out.println("[" + n.getDataCriacao() + "] (" + n.getEstado() + ") - " + n.getMensagem());

            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out.print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();

                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }

                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }

        cNotificacao.marcarComoLidasPorCategoria(utilizadorLogado.getIdUtilizador(), categoria);
        System.out.println("\nAs tuas notificações pendentes de " + categoria.name() + " foram marcadas como lidas!");
    }

    private void menuLogs() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- LOGS ---");
            System.out.println("1 - Listar Todos os Logs");
            System.out.println("2 - Pesquisar Logs por Username");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");
            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    ArrayList<Log> todos = cUtilizador.listarTodosLogs();
                    mostrarListaLogs(todos);
                    break;
                case 2:
                    System.out.print("Introduza o username (login) a pesquisar: ");
                    String user = ler.nextLine();
                    ArrayList<Log> filtrados = cUtilizador.pesquisarLogsPorUtilizador(user);
                    mostrarListaLogs(filtrados);
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void mostrarListaLogs(ArrayList<Log> lista) {
        if (lista.isEmpty()) {
            System.out.println("\nNão foram encontrados registos de log na base de dados.");
            return;
        }

        System.out.println("\n--- REGISTO DE AÇÕES ---");
        Iterator<Log> it = lista.iterator();
        int contador = 0;

        while (it.hasNext()) {
            Log l = it.next();
            System.out.println(
                    l.getData() + " " + l.getHora() + " | User: " + l.getUtilizador() + " | Ação: " + l.getAcao());

            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out.print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();

                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }

                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }
    }

    private void alterarEstadoContas() {
        System.out.println("\n--- ATIVAR / INATIVAR CONTAS ---");

        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();

        if (todos.isEmpty()) {
            System.out.println("Não existem utilizadores registados.");
            return;
        }

        Iterator<Utilizador> it = todos.iterator();
        int contador = 0;

        while (it.hasNext()) {
            Utilizador u = it.next();
            System.out.println("ID: " + u.getIdUtilizador() + " | Username: " + u.getLogin() +
                    " | Tipo: " + u.getTipo() + " | Estado Atual: " + u.getEstado());
            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out.print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();
                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }
                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }

        System.out.print("\nIntroduza o ID do utilizador a modificar (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0) {
            return;
        }

        if (idEscolhido == utilizadorLogado.getIdUtilizador()) {
            System.out.println("Não podes inativar a tua própria sessão atual!");
            return;
        }

        Utilizador userEscolhido = null;
        Iterator<Utilizador> itBusca = todos.iterator();

        while (itBusca.hasNext() && userEscolhido == null) {
            Utilizador u = itBusca.next();
            if (u.getIdUtilizador() == idEscolhido) {
                userEscolhido = u;
            }
        }

        if (userEscolhido == null) {
            System.out.println("Não foi encontrado nenhum utilizador com esse ID.");
            return;
        }

        if (userEscolhido.getEstado() == EstadoUtilizador.ATIVO) {
            System.out.println("\nO utilizador " + userEscolhido.getLogin() + " já se encontra ATIVO.");
            System.out.println("1 - Mudar para INATIVO");
            System.out.println("0 - Cancelar");
            System.out.print("Escolha: ");

            int escolhaEstado = Validacoes.lerInteiro(ler);

            if (escolhaEstado == 1) {
                cUtilizador.mudarEstadoConta(idEscolhido, EstadoUtilizador.INATIVO,
                        utilizadorLogado.getLogin(),
                        "Inativou a conta do utilizador ID: " + idEscolhido);
                System.out.println("A conta do utilizador " + idEscolhido + " está agora INATIVA.");
            } else {
                System.out.println("Operação cancelada.");
            }

        } else {
            System.out.println("\nO utilizador " + userEscolhido.getLogin() + " encontra-se atualmente: "
                    + userEscolhido.getEstado());
            System.out.println("1 - Mudar para ATIVO");
            System.out.println("0 - Cancelar");
            System.out.print("Escolha: ");

            int escolhaEstado = Validacoes.lerInteiro(ler);

            if (escolhaEstado == 1) {
                cUtilizador.mudarEstadoConta(idEscolhido, EstadoUtilizador.ATIVO,
                        utilizadorLogado.getLogin(),
                        "Ativou a conta do utilizador ID: " + idEscolhido);
                System.out.println("A conta do utilizador " + idEscolhido + " está agora ATIVA.");
            } else {
                System.out.println("Operação cancelada.");
            }
        }
    }

    private void gerirPedidosRemocao() {
        System.out.println("\n--- PEDIDOS DE REMOÇÃO DE CONTA ---");

        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        Iterator<Utilizador> itListagem = todos.iterator();
        boolean existemPedidos = false;

        while (itListagem.hasNext()) {
            Utilizador u = itListagem.next();
            if (u.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO) {
                System.out.println(
                        "ID: " + u.getIdUtilizador() + " | Username: " + u.getLogin() + " | Tipo: " + u.getTipo());
                existemPedidos = true;
            }
        }

        if (!existemPedidos) {
            System.out.println("Não há nenhum pedido de remoção pendente no momento.");
            return;
        }

        System.out.print("\nIntroduza o ID do utilizador a gerir (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0) {
            return;
        }

        boolean idRemocaoValido = false;
        Iterator<Utilizador> itVerificaRemocao = todos.iterator();
        while (itVerificaRemocao.hasNext()) {
            Utilizador uVerifica = itVerificaRemocao.next();
            if (uVerifica.getIdUtilizador() == idEscolhido
                    && uVerifica.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO) {
                idRemocaoValido = true;
            }
        }

        if (!idRemocaoValido) {
            System.out.println("O ID introduzido não corresponde a nenhum pedido de remoção pendente.");
            return;
        }

        System.out.println("\nO que desejas fazer com este pedido?");
        System.out.println("1 - ACEITAR (Apagar dados pessoais e remover)");
        System.out.println("2 - RECUSAR (Devolver a conta ao estado ATIVO)");
        System.out.print("Escolha: ");
        int decisao = Validacoes.lerInteiro(ler);

        if (decisao == 1) {
            cNotificacao.gerarNotificacao(idEscolhido,
                    "O teu pedido de remoção foi aceite. Os teus dados foram apagados e a conta removida.",
                    CategoriaNotificacao.GERAL);
            cUtilizador.apagarDadosPessoais(idEscolhido, utilizadorLogado.getLogin());

            System.out.println("Conta removida com sucesso!");

        } else if (decisao == 2) {
            cUtilizador.mudarEstadoConta(idEscolhido, EstadoUtilizador.ATIVO,
                    utilizadorLogado.getLogin(),
                    "Recusou o pedido de remoção da conta ID: " + idEscolhido);
            cNotificacao.gerarNotificacao(idEscolhido,
                    "O teu pedido de remoção foi recusado pelo Gestor. A tua conta continua ativa.",
                    CategoriaNotificacao.GERAL);

            System.out.println("Pedido recusado. A conta voltou ao estado ATIVO.");

        } else {
            System.out.println("Opção inválida!");
        }
    }

    private void solicitarRemocao() {
        System.out.println("\n--- SOLICITAR REMOÇÃO DE CONTA ---");

        ArrayList<Utilizador> todos = cUtilizador.obterTodosUtilizadores();
        Iterator<Utilizador> it = todos.iterator();
        int gestoresAtivos = 0;

        while (it.hasNext()) {
            Utilizador u = it.next();
            if (u.getTipo() == TipoUtilizador.GESTOR && u.getEstado() == EstadoUtilizador.ATIVO) {
                gestoresAtivos++;
            }
        }

        if (gestoresAtivos <= 1) {
            System.out.println("És o único Gestor ATIVO no sistema!");
            System.out.println(
                    "Para abandonares o sistema, tens de aprovar a conta de outro Gestor primeiro para que haja pelo menos um gestor no sistema.");
            return;
        }

        System.out.println("\n--- SOLICITAR REMOÇÃO DE CONTA ---");
        System.out.println("Ao solicitar a remoção, a tua conta será removida do sistema.");
        System.out.println("Os teus dados pessoais serão apagados de forma irreversível.");
        System.out.print("Tens a certeza que queres apagar a conta? (S/N): ");
        String confirma = ler.nextLine();

        if (confirma.equalsIgnoreCase("S")) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Gestor solicitou a remoção da conta.");

            cNotificacao.gerarNotificacaoParaGestores(
                    "O gestor '" + utilizadorLogado.getLogin() + " solicitou a remoção da sua conta.",
                    CategoriaNotificacao.REMOCAO);

            System.out.println("Pedido submetido com sucesso! A tua sessão vai ser terminada.");

            utilizadorLogado = null;

        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void consultarTodasReparacoes() {
        ArrayList<Reparacao> todas = cReparacao.obterTodasReparacoes();

        if (todas.isEmpty()) {
            System.out.println("\nNão existem reparações registadas no sistema.");
            System.out.print("Pressione [ENTER] para voltar ao menu...");
            ler.nextLine();
            return;
        }

        System.out.println("\n--- ESTADO GERAL DE REPARAÇÕES ---");
        int contador = 0;
        java.util.Iterator<Reparacao> it = todas.iterator();

        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("Processo Nº: " + r.getNumReparacao() + " | ESTADO: [" + r.getEstado() + "]");
            contador++;

            if (contador == 10 && it.hasNext()) {
                System.out.print("\nMostrados 10 registos. Pressione [ENTER] para ver mais ou digite '0' para sair: ");
                String acao = ler.nextLine();
                if (acao.equals("0")) {
                    System.out.println("Listagem interrompida.");
                    break;
                }
                contador = 0;
                System.out.println("\n--- PRÓXIMA PÁGINA ---");
            }
        }

        System.out.print("\nFim da lista. Pressione [ENTER] para voltar ao menu...");
        ler.nextLine();
    }

}
