package view;

import java.util.Scanner;
import java.util.Iterator;
import java.util.ArrayList;

import util.Validacoes;

import controlador.ControladorEquipamento;
import controlador.ControladorNotificacao;
import controlador.ControladorReparacao;
import controlador.ControladorUtilizador;
import model.Equipamento;
import model.Notificacao;
import model.Reparacao;
import model.Utilizador;

import Enums.EstadoUtilizador;
import Enums.CategoriaNotificacao;

/**
 * Classe responsável pela interface de interação (consola) com um Utilizador do
 * tipo Cliente.
 * Apresenta o menu específico para clientes, permitindo gerir equipamentos,
 * pedidos de reparação,
 * notificações e o perfil pessoal.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class ViewCliente {

    private Utilizador utilizadorLogado;
    private Scanner ler;
    private ControladorUtilizador cUtilizador;
    private ControladorEquipamento cEquipamento;
    private ControladorReparacao cReparacao;
    private ControladorNotificacao cNotificacao;

    /**
     * Construtor da ViewCliente.
     *
     * @param aUtilizador   utilizador (Cliente) atualmente autenticado
     * @param aScanner      objeto Scanner para leitura de entradas do utilizador
     * @param aCUtilizador  controlador de utilizadores
     * @param aCEquipamento controlador de equipamentos
     * @param aCReparacao   controlador de reparações
     * @param aCNotificacao controlador de notificações
     */
    public ViewCliente(Utilizador aUtilizador, Scanner aScanner, ControladorUtilizador aCUtilizador,
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
     * Apresenta o menu principal de Cliente e processa as opções selecionadas.
     * O ciclo mantém-se até o utilizador escolher a opção de voltar (logout).
     */
    public void mostrarMenu() {
        int opcao = -1;
        while (opcao != 0) {
            if (utilizadorLogado == null)
                return;

            System.out.println("\n--- MENU CLIENTE ---");
            int naoLidas = cNotificacao.contarNaoLidas(utilizadorLogado.getIdUtilizador());
            if (naoLidas > 0) {
                System.out.println("\n*** TENS " + naoLidas + " NOTIFICAÇÃO(ÕES) NÃO LIDA(S)! ***");
            }

            System.out.println("1 - Inserir Equipamento");
            System.out.println("2 - Pedir Reparação de Equipamento");
            System.out.println("3 - O Meu Perfil");
            System.out.println("4 - Listar Meus Pedidos de Reparação");
            System.out.println("5 - Pesquisar Meus Pedidos de Reparação");
            System.out.println("6 - Listar Meus Equipamentos");
            System.out.println("7 - Pesquisar Meus Equipamentos");
            System.out.println("8 - Ver notificações");
            System.out.println("9 - Solicitar Remoção de Conta");
            System.out.println("10 - Consultar estado das minhas reparações");
            System.out.println("0 - Voltar (Logout)");
            System.out.print("Escolha: ");

            opcao = util.Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    inserirNovoEquipamento();
                    break;
                case 2:
                    pedirNovaReparacao();
                    break;
                case 3:
                    editarMeuPerfil();
                    break;
                case 4:
                    listarMinhasReparacoesOrdenadas();
                    break;
                case 5:
                    pesquisarMinhasReparacoes();
                    break;
                case 6:
                    listarMeusEquipamentosOrdenados();
                    break;
                case 7:
                    pesquisarMeusEquipamentos();
                    break;
                case 8:
                    verNotificacoes();
                    break;
                case 9:
                    solicitarRemocao();
                    break;
                case 10:
                    consultarMinhasReparacoes();
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

        boolean sucesso = cUtilizador.atualizarPerfilCliente(
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

    private void inserirNovoEquipamento() {
        System.out.print("Marca: ");
        String marca = ler.nextLine();

        System.out.print("Modelo: ");
        String codModelo = ler.nextLine();

        if (cEquipamento.modeloExiste(codModelo)) {
            System.out.println(
                    "ERRO: Já existe um equipamento com o código de modelo '" + codModelo + "'. Inserção bloqueada.");
            return;
        }

        int codSKU = cEquipamento.gerarSkuUnico();
        System.out.println("Código SKU Gerado automaticamente: " + codSKU + ".");

        System.out.print("Data Fabrico (YYYY-MM-DD): ");
        String dataFab = ler.nextLine();

        System.out.print("Lote: ");
        String lote = ler.nextLine();

        cEquipamento.registarEquipamento(utilizadorLogado, marca, codModelo, codSKU, dataFab, lote,
                utilizadorLogado.getLogin());
        System.out.println("Equipamento registado com sucesso! (SKU: " + codSKU + ")");
    }

    private void pedirNovaReparacao() {
        ArrayList<Equipamento> meusEquipamentos = cEquipamento.listarEquipamentos(utilizadorLogado.getIdUtilizador());

        if (meusEquipamentos.isEmpty()) {
            System.out.println("Não tens equipamentos registados. Regista um primeiro.");
            return;
        }

        System.out.println("\n--- Os Meus Equipamentos ---");
        Iterator<Equipamento> it = meusEquipamentos.iterator();
        while (it.hasNext()) {
            Equipamento eq = it.next();
            System.out.println(
                    "ID: " + eq.getIdEquipamento() + " | Marca: " + eq.getMarca() + " | Modelo: " + eq.getModelo());
        }

        System.out.print("Insira o ID do equipamento a reparar (ou 0 para cancelar): ");
        int idEq = Validacoes.lerInteiro(ler);

        if (idEq == 0) {
            return;
        }

        boolean pertence = false;
        Iterator<Equipamento> itVerifica = meusEquipamentos.iterator();
        while (itVerifica.hasNext()) {
            Equipamento eqVerifica = itVerifica.next();
            if (eqVerifica.getIdEquipamento() == idEq) {
                pertence = true;
            }
        }

        if (!pertence) {
            System.out.println("Esse equipamento não te pertence ou o ID é inválido!");
            return;
        }

        cReparacao.registarNovoPedido(idEq, utilizadorLogado.getLogin());
        System.out.println("Pedido de reparação submetido com sucesso!");
    }

    private void listarMinhasReparacoesOrdenadas() {
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

        ArrayList<Reparacao> lista = cReparacao.listarReparacoesClienteOrdenadas(utilizadorLogado.getIdUtilizador(),
                escolha, ascendente);

        if (lista.isEmpty()) {
            System.out.println("Não tens reparações registadas no sistema.");
            return;
        }

        System.out.println("\n--- OS MEUS PEDIDOS ---");
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

    private void pesquisarMinhasReparacoes() {
        System.out.println("\n--- PESQUISAR OS MEUS PEDIDOS ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Número da Reparação");
        System.out.println("2 - Por Estado (ex: CRIADO, FINALIZADO)");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha != 1 && escolha != 2) {
            System.out.println("Opção inválida!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar (pesquisa parcial ativada): ");
        String termo = ler.nextLine();

        ArrayList<Reparacao> resultados = cReparacao.pesquisarReparacoesCliente(utilizadorLogado.getIdUtilizador(),
                escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum pedido encontrado para a pesquisa: '" + termo + "'");
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

    private void listarMeusEquipamentosOrdenados() {
        System.out.println("\n--- LISTAR OS MEUS EQUIPAMENTOS ---");
        System.out.println("Qual o critério de ordenação?");
        System.out.println("1 - Por Marca");
        System.out.println("2 - Por Código de Modelo");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha != 1 && escolha != 2) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.println("\nComo deseja ordenar a lista?");
        System.out.println("1 - Ordem Crescente");
        System.out.println("2 - Ordem Decrescente");
        System.out.print("Escolha: ");
        int opcaoOrdem = Validacoes.lerInteiro(ler);

        boolean ascendente = (opcaoOrdem == 1);

        ArrayList<Equipamento> lista = cEquipamento
                .listarEquipamentosClienteOrdenados(utilizadorLogado.getIdUtilizador(), escolha, ascendente);

        if (lista.isEmpty()) {
            System.out.println("Não tens equipamentos registados.");
            return;
        }

        System.out.println("\n--- OS MEUS EQUIPAMENTOS ---");
        Iterator<Equipamento> it = lista.iterator();

        int contador = 0;

        while (it.hasNext()) {
            Equipamento eq = it.next();
            System.out.println("ID: " + eq.getIdEquipamento() +
                    " | Marca: " + eq.getMarca() +
                    " | Modelo: " + eq.getModelo() +
                    " | SKU: " + eq.getSku());

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

    private void pesquisarMeusEquipamentos() {
        System.out.println("\n--- PESQUISAR OS MEUS EQUIPAMENTOS ---");
        System.out.println("Qual o critério de pesquisa?");
        System.out.println("1 - Por Marca");
        System.out.println("2 - Por Código de Modelo");
        System.out.print("Escolha: ");
        int escolha = Validacoes.lerInteiro(ler);

        if (escolha < 1 || escolha > 2) {
            System.out.println("Critério inválido!");
            return;
        }

        System.out.print("Introduza o termo a pesquisar (pesquisa parcial ativada): ");
        String termo = ler.nextLine();

        ArrayList<Equipamento> resultados = cEquipamento
                .pesquisarEquipamentosCliente(utilizadorLogado.getIdUtilizador(), escolha, termo);

        if (resultados.isEmpty()) {
            System.out.println("Nenhum equipamento encontrado na tua conta para a pesquisa: '" + termo + "'");
            return;
        }

        System.out.println("\n--- RESULTADOS DA PESQUISA ---");
        Iterator<Equipamento> it = resultados.iterator();
        while (it.hasNext()) {
            Equipamento eq = it.next();
            System.out.println("ID: " + eq.getIdEquipamento() +
                    " | Marca: " + eq.getMarca() +
                    " | Modelo: " + eq.getModelo() +
                    " | SKU: " + eq.getSku());
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
        String certeza = ler.nextLine();

        if (certeza.equalsIgnoreCase("S")) {
            cUtilizador.mudarEstadoConta(utilizadorLogado.getIdUtilizador(), EstadoUtilizador.AGUARDA_REMOCAO,
                    utilizadorLogado.getLogin(), "Cliente solicitou a remoção da conta.");

            cNotificacao.gerarNotificacaoParaGestores(
                    "O Cliente '" + utilizadorLogado.getLogin() + " solicitou a remoção da sua conta.",
                    CategoriaNotificacao.REMOCAO);

            System.out.println("Pedido submetido com sucesso! A tua sessão vai ser terminada.");

            utilizadorLogado = null;

        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void consultarMinhasReparacoes() {
        ArrayList<Reparacao> minhas = cReparacao.obterMinhasReparacoes(utilizadorLogado.getIdUtilizador());

        if (minhas.isEmpty()) {
            System.out.println("\nNão tens nenhuma reparação registada no sistema.");
            System.out.print("Pressione [ENTER] para voltar ao menu...");
            ler.nextLine();
            return;
        }

        System.out.println("\n--- ESTADO DAS MINHAS REPARAÇÕES ---");
        int contador = 0;
        java.util.Iterator<Reparacao> it = minhas.iterator();

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
