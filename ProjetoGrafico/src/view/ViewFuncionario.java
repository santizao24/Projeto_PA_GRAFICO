package view;

import java.util.Scanner;

import util.Validacoes;

import Enums.EstadoReparacao;

import java.util.Iterator;
import java.util.ArrayList;

import controlador.ControladorEquipamento;
import controlador.ControladorNotificacao;
import controlador.ControladorReparacao;
import controlador.ControladorUtilizador;
import model.Notificacao;
import model.Peca;
import model.Reparacao;
import model.Utilizador;

import Enums.EstadoUtilizador;
import Enums.CategoriaNotificacao;

/**
 * Classe responsável pela interface de interação (consola) com um Utilizador do
 * tipo Funcionário.
 * Apresenta o menu específico para funcionários, permitindo aceitar/rejeitar
 * reparações,
 * registar peças, testes de operacionalidade e concluir reparações.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ViewFuncionario {

    private Utilizador utilizadorLogado;
    private Scanner ler;
    private ControladorUtilizador cUtilizador;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    /**
     * Construtor da ViewFuncionario.
     *
     * @param aUtilizador   utilizador (Funcionário) atualmente autenticado
     * @param aScanner      objeto Scanner para leitura de entradas do utilizador
     * @param aCUtilizador  controlador de utilizadores
     * @param aCEquipamento controlador de equipamentos
     * @param aCReparacao   controlador de reparações
     * @param aCNotificacao controlador de notificações
     */
    public ViewFuncionario(Utilizador aUtilizador, Scanner aScanner, ControladorUtilizador aCUtilizador,
            ControladorEquipamento aCEquipamento, ControladorReparacao aCReparacao,
            ControladorNotificacao aCNotificacao) {
        utilizadorLogado = aUtilizador;
        ler = aScanner;
        cUtilizador = aCUtilizador;
        cReparacao = aCReparacao;
        cNotificacao = aCNotificacao;
    }

    /**
     * Apresenta o menu principal de Funcionário e processa as opções selecionadas.
     * O ciclo mantém-se até o utilizador escolher a opção de voltar (logout).
     */
    public void mostrarMenu() {
        int opcao = -1;
        while (opcao != 0) {
            if (utilizadorLogado == null)
                return;

            System.out.println("\n--- MENU FUNCIONÁRIO ---");
            int naoLidas = cNotificacao.contarNaoLidas(utilizadorLogado.getIdUtilizador());
            if (naoLidas > 0) {
                System.out.println("\n*** TENS " + naoLidas + " NOTIFICAÇÃO(ÕES) NÃO LIDA(S)! ***");
            }

            System.out.println("1 - Ver Pedidos Novos (Aceitar/Rejeitar)");
            System.out.println("2 - Trabalhar em Reparação Iniciada");
            System.out.println("3 - O Meu Perfil");
            System.out.println("4 - Listar as Minhas Reparações Ordenadas");
            System.out.println("5 - Pesquisar as Minhas Reparações");
            System.out.println("6 - Ver notificações");
            System.out.println("7 - Solicitar Remoção de Conta");
            System.out.println("0 - Voltar (Logout)");
            System.out.print("Escolha: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    gerirPedidosAtribuidos();
                    break;
                case 2:
                    listarReparacoesEmCurso();
                    break;
                case 3:
                    editarMeuPerfil();
                    break;
                case 4:
                    listarMinhasReparacoesAtribuidas();
                    break;
                case 5:
                    pesquisarMinhasReparacoesAtribuidas();
                    break;
                case 6:
                    verNotificacoes();
                    break;
                case 7:
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

    private void gerirPedidosAtribuidos() {
        ArrayList<Reparacao> atribuidos = cReparacao.listarReparacoesPorEstado(utilizadorLogado.getIdUtilizador(),
                EstadoReparacao.ACEITE.name());

        if (atribuidos.isEmpty()) {
            System.out.println("\nNão tens novos pedidos de reparação atribuídos no momento.");
            return;
        }

        System.out.println("\n--- Novos Pedidos Atribuídos ---");
        Iterator<Reparacao> it = atribuidos.iterator();
        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("ID: " + r.getIdReparacao() + " | Código: " + r.getNumReparacao()
                    + " | Equipamento ID: " + r.getIdEquipamento());
        }

        System.out.print("\nInsira o ID da Reparação a gerir (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0)
            return;

        System.out.println("Desejas assumir esta reparação?");
        System.out.println("1 - Aceitar (Iniciar reparação)");
        System.out.println("2 - Rejeitar (Devolver ao Gestor)");
        System.out.print("Escolha: ");
        int acao = Validacoes.lerInteiro(ler);

        if (acao == 1) {
            cReparacao.gerirEstadoReparacao(idEscolhido, utilizadorLogado.getIdUtilizador(),
                    EstadoReparacao.DECORRER.name(), utilizadorLogado.getLogin());
            System.out.println("Reparação iniciada!");
        } else if (acao == 2) {
            cReparacao.rejeitarReparacaoPorFuncionario(idEscolhido, utilizadorLogado.getIdUtilizador(),
                    utilizadorLogado.getLogin());
            System.out.println("Reparação rejeitada e devolvida ao Gestor.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    private void listarReparacoesEmCurso() {
        ArrayList<Reparacao> emCurso = cReparacao.listarReparacoesPorEstado(utilizadorLogado.getIdUtilizador(),
                EstadoReparacao.DECORRER.name());

        if (emCurso.isEmpty()) {
            System.out.println("\nNão tens reparações em curso no momento.");
            return;
        }

        System.out.println("\n--- As Minhas Reparações Em Curso ---");
        Iterator<Reparacao> it = emCurso.iterator();

        int contador = 0;

        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("ID: " + r.getIdReparacao() + " | Código: " + r.getNumReparacao() + " | Data Início: "
                    + r.getDataCriacao());

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

        System.out.print("\nInsira o ID da Reparação para trabalhar (ou 0 para cancelar): ");
        int idEscolhido = Validacoes.lerInteiro(ler);

        if (idEscolhido == 0)
            return;

        Reparacao repSelecionada = null;
        Iterator<Reparacao> itBusca = emCurso.iterator();
        while (itBusca.hasNext()) {
            Reparacao r = itBusca.next();
            if (r.getIdReparacao() == idEscolhido) {
                repSelecionada = r;
            }
        }

        if (repSelecionada != null) {
            subMenuTrabalharReparacao(repSelecionada);
        } else {
            System.out.println("ID não encontrado.");
        }
    }

    private void inserirPecaNaReparacao(int idReparacao) {
        ArrayList<Peca> pecas = cReparacao.listarPecasComStock();
        if (pecas.isEmpty()) {
            System.out.println("Não há peças com stock disponível no armazém.");
            return;
        }

        System.out.println("\n--- Peças em Stock ---");
        Iterator<Peca> it = pecas.iterator();
        while (it.hasNext()) {
            Peca p = it.next();
            System.out.println(
                    "Cód: " + p.getCodigoInterno() + " | " + p.getDesignacao() + " | Em Stock: " + p.getQuantidade());
        }

        System.out.print("\nInsira o Código Interno da Peça: ");
        String codPeca = ler.nextLine();

        int qtd;
        do {
            System.out.print("Quantidade a usar: ");
            qtd = Validacoes.lerInteiro(ler);
            if (qtd <= 0) {
                System.out.println("A quantidade tem de ser maior que zero!");
            }
        } while (qtd <= 0);

        boolean sucesso = cReparacao.registarPecaUsada(idReparacao, codPeca, qtd, utilizadorLogado.getLogin());

        if (sucesso) {
            System.out.println("Peça registada na reparação com sucesso!");
        } else {
            System.out.println("O código da peça não existe ou não há stock suficiente no armazém!");
        }
    }

    private void registarTesteOperacionalidade(int idReparacao) {
        System.out.println("\n--- NOVO TESTE DE OPERACIONALIDADE ---");
        System.out.print("Designação do Teste (ex: Teste de Bateria): ");
        String designacao = ler.nextLine();
        System.out.print("Descrição / Resultado: ");
        String descricao = ler.nextLine();

        cReparacao.registarTeste(idReparacao, designacao, descricao, utilizadorLogado.getLogin());
    }

    private void concluirReparacaoFinal(Reparacao rep) {
        System.out.println("\n--- FECHAR PROCESSO ---");

        double custo;
        do {
            System.out.print("Custo Final da Reparação (€): ");
            custo = Validacoes.lerDouble(ler);
            if (custo < 0) {
                System.out.println("O custo não pode ser negativo!");
            }
        } while (custo < 0);

        System.out.print("Observações finais do trabalho: ");
        String obs = ler.nextLine();

        cReparacao.concluirReparacaoFinal(rep, custo, obs, utilizadorLogado.getLogin());
    }

    private void subMenuTrabalharReparacao(Reparacao rep) {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- A TRABALHAR NA REPARAÇÃO: " + rep.getNumReparacao() + " ---");
            System.out.println("1 - Inserir Peça Usada");
            System.out.println("2 - Registar Teste de Operacionalidade");
            System.out.println("3 - CONCLUIR REPARAÇÃO");
            System.out.println("0 - Voltar");
            System.out.print("Escolha: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    inserirPecaNaReparacao(rep.getIdReparacao());
                    break;
                case 2:
                    registarTesteOperacionalidade(rep.getIdReparacao());
                    break;
                case 3:
                    concluirReparacaoFinal(rep);
                    return;
                case 0:
                    return;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }

    private void editarMeuPerfil() {
        System.out.println("\n--- EDITAR MEU PERFIL ---");
        System.out.println("Por motivos de segurança, o Nome e o NIF não podem ser alterados.");

        String novoEmail;
        do {
            System.out.print("Novo Email: ");
            novoEmail = ler.nextLine();
            if (!Validacoes.emailValido(novoEmail)) {
                System.out.println("Formato de email inválido!");
            }
        } while (!Validacoes.emailValido(novoEmail));

        String novaPass;
        do {
            System.out.print("Nova Password: ");
            novaPass = ler.nextLine();
            if (novaPass.isEmpty()) {
                System.out.println("A password não pode estar vazia!");
            }
        } while (novaPass.isEmpty());

        String novoTelefone;
        do {
            System.out.print("Novo Telefone (9 dígitos): ");
            novoTelefone = ler.nextLine();
            if (!Validacoes.telefoneValido(novoTelefone)) {
                System.out.println("Formato de telefone inválido!");
            }
        } while (!Validacoes.telefoneValido(novoTelefone));

        System.out.print("Nova Morada: ");
        String novaMorada = ler.nextLine();

        boolean sucesso = cUtilizador.atualizarPerfilFuncionario(
                utilizadorLogado.getIdUtilizador(),
                novoEmail,
                novaPass,
                novoTelefone,
                novaMorada,
                utilizadorLogado.getLogin());

        if (sucesso) {
            System.out.println("Perfil atualizado com sucesso!");
        } else {
            System.out.println("Erro ao atualizar o perfil. O email pode já estar em uso.");
        }
    }

    private void listarMinhasReparacoesAtribuidas() {
        System.out.println("\n--- LISTAR OS MEUS PEDIDOS DE REPARAÇÃO ---");
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

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesFuncionarioOrdenadas(utilizadorLogado.getIdUtilizador(),
                escolha, ascendente);

        if (lista.isEmpty()) {
            System.out.println("Não tens reparações atribuídas a ti no sistema.");
            return;
        }

        System.out.println("\n--- AS MINHAS REPARAÇÕES ---");
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

    private void pesquisarMinhasReparacoesAtribuidas() {
        System.out.println("\n--- PESQUISAR OS MEUS PEDIDOS ATRIBUÍDOS ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Número da Reparação");
        System.out.println("2 - Por Estado (ex: DECORRER, FINALIZADO)");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha < 1 || escolha > 2) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar (pesquisa parcial ativada): ");
        String termo = ler.nextLine();

        ArrayList<Reparacao> resultados = cReparacao.pesquisarReparacoesFuncionario(utilizadorLogado.getIdUtilizador(),
                escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum pedido atribuído a ti corresponde à pesquisa: '" + termo + "'");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Reparacao> it = resultados.iterator();
        while (it.hasNext()) {
            Reparacao r = it.next();
            System.out.println("ID: " + r.getIdReparacao() +
                    " | Número: " + r.getNumReparacao() +
                    " | Data: " + r.getDataCriacao() +
                    " | Estado: " + r.getEstado());
        }
    }

    private void verNotificacoes() {
        System.out.println("\n--- AS MINHAS NOTIFICAÇÕES ---");

        ArrayList<Notificacao> lista = cNotificacao.obterNotificacoes(utilizadorLogado.getIdUtilizador());

        if (lista.isEmpty()) {
            System.out.println("Não tens nenhuma notificação no teu histórico.");
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

        cNotificacao.marcarComoLidas(utilizadorLogado.getIdUtilizador());
        System.out.println("\nAs tuas notificações pendentes foram marcadas como lidas!");
    }

    private void solicitarRemocao() {
        System.out.println("\n--- SOLICITAR REMOÇÃO DE CONTA ---");
        System.out.println("Ao solicitar a remoção, a tua conta será removida do sistema.");
        System.out.println("Os teus dados pessoais serão apagados de forma irreversível.");
        System.out.print("Tens a certeza que queres apagar a conta? (S/N): ");
        String confirma = ler.nextLine();

        if (confirma.equalsIgnoreCase("S")) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Funcionário solicitou a remoção da conta.");

            cNotificacao.gerarNotificacaoParaGestores(
                    "O funcionário '" + utilizadorLogado.getLogin() + " solicitou a remoção da sua conta.",
                    CategoriaNotificacao.REMOCAO);

            System.out.println("Pedido submetido com sucesso! A tua sessão vai ser terminada.");

            utilizadorLogado = null;

        } else {
            System.out.println("Operação cancelada.");
        }
    }

}
