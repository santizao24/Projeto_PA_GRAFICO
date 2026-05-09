package view;

import java.util.Scanner;
import java.util.InputMismatchException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;

import Enums.EstadoUtilizador;
import Enums.TipoUtilizador;
import model.Utilizador;

import util.Validacoes;

/**
 * Classe principal que inicia o sistema de gestão de oficina.
 * Gere o menu de acesso inicial (login/registo/configuração da base de dados)
 * e o reencaminhamento para os menus específicos de cada tipo de utilizador
 * (Cliente, Funcionário ou Gestor). Também monitoriza o tempo de execução.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class Aplicacao {
    private Utilizador utilizadorLogado;
    private controlador.ControladorUtilizador cUtilizador = new controlador.ControladorUtilizador();
    private controlador.ControladorEquipamento cEquipamento = new controlador.ControladorEquipamento();
    private controlador.ControladorReparacao cReparacao = new controlador.ControladorReparacao();
    private controlador.ControladorNotificacao cNotificacao = new controlador.ControladorNotificacao();
    private Scanner ler = new Scanner(System.in);

    private long inicio;
    private String ultimoNomeLogado;

    /**
     * Ponto de entrada do programa.
     * Instancia a aplicação e invoca o método iniciar().
     *
     * @param args argumentos da linha de comandos (não utilizados)
     */
    public static void main(String[] args) {
        Aplicacao app = new Aplicacao();
        app.iniciar();
    }

    private void iniciar() {
        inicio = System.currentTimeMillis();
        mostrarInfoExecucao();
        verificarPrimeiraExecucao();
        menuAcesso();
        calcularTempoExecucao();
        atualizarInfoExecucao();
    }

    private void menuAcesso() {
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- SISTEMA DE GESTÃO DE OFICINA ---");
            System.out.println("1 - Configurar Base de Dados");
            System.out.println("2 - Login");
            System.out.println("3 - Registo");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = Validacoes.lerInteiro(ler);

            switch (opcao) {
                case 1:
                    configurarBaseDados();
                    break;
                case 2:
                    fazerLogin();
                    break;
                case 3:
                    fazerRegisto();
                    break;
                case 0:
                    if (ultimoNomeLogado != null) {
                        System.out.println("Adeus " + ultimoNomeLogado);
                    }
                    System.out.println("A sair...");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private void fazerLogin() {
        System.out.print("Username: ");
        String user = ler.nextLine();
        System.out.print("Password: ");
        String pass = ler.nextLine();

        utilizadorLogado = cUtilizador.efetuarLogin(user, pass);

        if (utilizadorLogado != null) {
            if (utilizadorLogado.getEstado() == EstadoUtilizador.ATIVO) {
                System.out.println(
                        "\nBem-vindo, " + utilizadorLogado.getNome() + " (" + utilizadorLogado.getTipo() + ")");

                ultimoNomeLogado = utilizadorLogado.getNome();
                cUtilizador.registarInicioSessao(user);

                direcionarMenu();

            } else if (utilizadorLogado.getEstado() == EstadoUtilizador.PENDENTE) {
                System.out.println("Conta aguardando ativação pelo Gestor.");
            } else if (utilizadorLogado.getEstado() == EstadoUtilizador.REJEITADO) {
                System.out.println("Conta com registo Recusado!");
            } else if (utilizadorLogado.getEstado() == EstadoUtilizador.INATIVO) {
                System.out.println("A tua conta encontra-se INATIVA. Contacta um gestor.");
            } else if (utilizadorLogado.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO) {
                System.out.println("A tua conta está a aguardar remoção. Contacta um gestor se queres reverter.");
            } else if (utilizadorLogado.getEstado() == EstadoUtilizador.REMOVIDA) {
                System.out.println("Esta conta foi removida do sistema.");
            } else {
                System.out.println("Conta com estado desconhecido: " + utilizadorLogado.getEstado());
            }
        } else {
            System.out.println("Credenciais inválidas.");
        }
    }

    private void fazerRegisto() {
        System.out.println("\n--- REGISTO DE UTILIZADOR ---");

        String nome;
        do {
            System.out.print("Nome: ");
            nome = ler.nextLine();
            if (nome.isEmpty()) {
                System.out.println("O nome não pode estar vazio!");
            }
        } while (nome.isEmpty());

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

        System.out.println("Tipo de Utilizador:");
        System.out.println("1 - Funcionario");
        System.out.println("2 - Cliente");
        System.out.print("Escolha: ");
        int tmp;
        try {
            tmp = ler.nextInt();
            ler.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Entrada inválida!");
            ler.nextLine();
            return;
        }

        switch (tmp) {
            case 1:
                String nifF;
                do {
                    System.out.print("Introduza o NIF (9 dígitos): ");
                    nifF = ler.nextLine();
                    if (!Validacoes.nifValido(nifF)) {
                        System.out.println("O NIF tem de ter exatamente 9 dígitos numéricos!");
                    }
                } while (!Validacoes.nifValido(nifF));
                String telF;
                do {
                    System.out.print("Introduza o Telefone (9 dígitos, começa por 2, 3 ou 9): ");
                    telF = ler.nextLine();
                    if (!Validacoes.telefoneValido(telF)) {
                        System.out.println("Formato de telefone inválido!");
                    }
                } while (!Validacoes.telefoneValido(telF));
                System.out.print("Morada: ");
                String morF = ler.nextLine();
                int esp;
                do {
                    System.out.print("Especialização (1 a 5): ");
                    try {
                        esp = ler.nextInt();
                        ler.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Entrada inválida! Introduza um número.");
                        ler.nextLine();
                        esp = 0;
                    }
                    if (esp < 1 || esp > 5) {
                        System.out.println("A especialização tem de estar entre 1 e 5!");
                    }
                } while (esp < 1 || esp > 5);
                System.out.print("Data Início (YYYY-MM-DD): ");
                String dataF = ler.nextLine();

                boolean sucessoF = cUtilizador.registarFuncionario(nome, email, user, pass, nifF, telF, morF, esp,
                        dataF);
                if (sucessoF) {
                    System.out.println("Registo efetuado! Aguarde aprovação do Gestor.");
                } else {
                    System.out.println("Erro no registo! O username ou email especificado já se encontra em uso.");
                }
                break;
            case 2:
                String nifC;
                do {
                    System.out.print("Introduza o NIF (9 dígitos): ");
                    nifC = ler.nextLine();
                    if (!Validacoes.nifValido(nifC)) {
                        System.out.println("O NIF tem de ter exatamente 9 dígitos numéricos!");
                    }
                } while (!Validacoes.nifValido(nifC));
                String telC;
                do {
                    System.out.print("Introduza o Telefone (9 dígitos, começa por 2, 3 ou 9): ");
                    telC = ler.nextLine();
                    if (!Validacoes.telefoneValido(telC)) {
                        System.out.println("Formato de telefone inválido!");
                    }
                } while (!Validacoes.telefoneValido(telC));
                System.out.print("Morada: ");
                String morC = ler.nextLine();
                System.out.print("Setor Atividade: ");
                String setor = ler.nextLine();
                String escalao;
                do {
                    System.out.print("Escalão (A, B, C ou D): ");
                    escalao = ler.nextLine().toUpperCase();
                    if (!escalao.equals("A") && !escalao.equals("B") && !escalao.equals("C") && !escalao.equals("D")) {
                        System.out.println("Escalão inválido! Deve ser A, B, C ou D.");
                    }
                } while (!escalao.equals("A") && !escalao.equals("B") && !escalao.equals("C") && !escalao.equals("D"));

                boolean sucessoC = cUtilizador.registarCliente(nome, email, user, pass, nifC, telC, morC, setor,
                        escalao);
                if (sucessoC) {
                    System.out.println("Registo efetuado! Aguarde aprovação do Gestor.");
                } else {
                    System.out.println("Erro no registo! O username ou email especificado já se encontra em uso.");
                }
                break;
            default:
                System.out.println("Opção inválida!");
                break;
        }
    }

    private void direcionarMenu() {
        TipoUtilizador tipo = utilizadorLogado.getTipo();

        if (tipo == TipoUtilizador.GESTOR) {
            ViewGestor menuG = new ViewGestor(utilizadorLogado, ler, cUtilizador, cEquipamento, cReparacao,
                    cNotificacao);
            menuG.mostrarMenu();
        } else if (tipo == TipoUtilizador.CLIENTE) {
            ViewCliente menuC = new ViewCliente(utilizadorLogado, ler, cUtilizador, cEquipamento, cReparacao,
                    cNotificacao);
            menuC.mostrarMenu();
        } else if (tipo == TipoUtilizador.FUNCIONARIO) {
            ViewFuncionario menuF = new ViewFuncionario(utilizadorLogado, ler, cUtilizador, cEquipamento, cReparacao,
                    cNotificacao);
            menuF.mostrarMenu();
        }
    }

    private void verificarPrimeiraExecucao() {

        File ficheiroConfig = new File("configbd.txt");
        if (!ficheiroConfig.exists()) {
            System.out.println("\n--- PRIMEIRA EXECUÇÃO DETETADA ---");
            System.out.println("O ficheiro de configuração da base de dados não foi encontrado.");
            System.out.println("É necessário configurar a ligação à base de dados antes de continuar.\n");
            configurarBaseDados();
            return;
        }

        if (!cUtilizador.existeGestor()) {
            System.out.println("NÃO HÁ REGISTO DE NENHUM GESTOR NA BASE DE DADOS");
            System.out.println("Criação obrigatória do 1º Gestor do Sistema");

            String nome;
            do {
                System.out.print("Nome do Gestor: ");
                nome = ler.nextLine();
                if (nome.isEmpty()) {
                    System.out.println("O nome não pode estar vazio!");
                }
            } while (nome.isEmpty());

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

            boolean sucessoG = cUtilizador.registarGestor(nome, email, user, pass);

            if (sucessoG) {
                System.out.println("\nGestor principal criado e ativado com sucesso!");
                System.out.println("Já pode efetuar o Login para entrar no sistema.\n");
            } else {
                System.out.println(
                        "\nErro ao criar o gestor principal! (Username ou email já em uso). Tente novamente.\n");
            }
        }
    }

    private void calcularTempoExecucao() {
        System.out.println("\n--- A ENCERRAR O SISTEMA ---");
        long fim = System.currentTimeMillis();
        long tempo = fim - inicio;

        SimpleDateFormat formatData = new SimpleDateFormat("EEEE; yyyy-MM-dd HH:mm:ss",
                new java.util.Locale("pt", "PT"));
        String inicioFormatado = formatData.format(new java.util.Date(inicio));
        String fimFormatado = formatData.format(new java.util.Date(fim));

        long horas = (tempo / (1000 * 60 * 60)) % 24;
        long minutos = (tempo / (1000 * 60)) % 60;
        long segundos = (tempo / 1000) % 60;

        System.out.println("Inicio do processo: " + inicioFormatado);
        System.out.println("Fim do processo:    " + fimFormatado);
        System.out.println("Tempo de execução:  " + tempo + " Milissegundos (" + segundos + " Segundos; " + minutos
                + " Minutos; " + horas + " Horas)");
    }

    private void mostrarInfoExecucao() {
        File ficheiro = new File("info_sistema.dat");
        if (ficheiro.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
                String ultimaExecucao = br.readLine();
                String totalExecucoes = br.readLine();
                System.out.println("\n--- INFORMAÇÃO DO SISTEMA ---");
                System.out.println("Última execução:     " + ultimaExecucao);
                System.out.println("Total de execuções:  " + totalExecucoes);
            } catch (Exception e) {
                System.out.println("Não foi possível ler informações de execução anteriores.");
            }
        } else {
            System.out.println("\n--- Primeira execução do sistema! ---");
        }
    }

    private void atualizarInfoExecucao() {
        int totalExecucoes = 1;
        File ficheiro = new File("info_sistema.dat");

        if (ficheiro.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(ficheiro))) {
                br.readLine();
                String totalStr = br.readLine();
                if (totalStr != null) {
                    try {
                        totalExecucoes = Integer.parseInt(totalStr.trim()) + 1;
                    } catch (NumberFormatException e) {
                        totalExecucoes = 1;
                    }
                }
            } catch (Exception e) {
            }
        }

        SimpleDateFormat formatData = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dataAtual = formatData.format(new java.util.Date());

        try (FileWriter fw = new FileWriter(ficheiro)) {
            fw.write(dataAtual + "\n");
            fw.write(String.valueOf(totalExecucoes) + "\n");
        } catch (Exception e) {
            System.out.println("Erro ao guardar informações de execução: " + e.getMessage());
        }
    }

    private void configurarBaseDados() {
        System.out.println("\n--- CONFIGURAÇÃO DA BASE DE DADOS ---");
        System.out.print("IP do Servidor (ex: localhost ou 127.0.0.1): ");
        String ip = ler.nextLine();

        System.out.print("Porto (ex: 3306): ");
        String porto = ler.nextLine();

        System.out.print("Nome da Base de Dados: ");
        String bd = ler.nextLine();

        System.out.print("Username: ");
        String user = ler.nextLine();

        System.out.print("Password: ");
        String password = ler.nextLine();

        String url = "jdbc:mysql://" + ip + ":" + porto + "/" + bd;

        Properties prop = new Properties();

        try (FileOutputStream fos = new FileOutputStream("configbd.txt")) {
            prop.setProperty("url", url);
            prop.setProperty("user", user);
            prop.setProperty("password", password);
            prop.store(fos, "Configuracoes da Base de Dados");
            System.out.println("Parâmetros de acesso guardados com sucesso no ficheiro 'configbd.txt'!");

            verificarPrimeiraExecucao();

        } catch (Exception e) {
            System.out.println("Erro ao gravar as configurações: " + e.getMessage());
        }
    }

}