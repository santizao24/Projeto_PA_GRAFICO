package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import com.formdev.flatlaf.FlatDarculaLaf;

import controlador.ControladorUtilizador;
import controlador.ControladorReparacao;
import controlador.ControladorEquipamento;
import controlador.ControladorNotificacao;
import model.Utilizador;
import Enums.TipoUtilizador;

/**
 * Janela principal da aplicação gráfica do Sistema de Gestão de Oficina.
 * Gere a navegação entre painéis (login, registo, menus) usando
 * remove/add/revalidate/repaint no painel principal,
 * e mantém referências centralizadas aos controladores do sistema.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class Aplicacao extends JFrame implements ActionListener {

    private ControladorUtilizador cUtilizador;
    private ControladorReparacao cReparacao;
    private ControladorEquipamento cEquipamento;
    private ControladorNotificacao cNotificacao;

    private JPanel painelPrincipal;
    private JPanel painelAtual;
    private PainelLogin painelLogin;
    private PainelRegisto painelRegisto;
    private PainelCliente painelCliente;
    private PainelFuncionario painelFuncionario;
    private PainelGestor painelGestor;

    private JMenuItem menuConfigBD, menuSair, menuSobre;
    private JLabel barraEstado;

    /**
     * Ponto de entrada da aplicação gráfica.
     * Arranca a aplicação inicializando o Look and Feel.
     */
    public static void main(String[] args) {
        FlatDarculaLaf.setup();
        Aplicacao app = new Aplicacao();
        app.iniciar();
    }

    /**
     * Constrói a janela principal da aplicação.
     */
    public Aplicacao() {
        super("Sistema de Gestão de Oficina");
        cUtilizador = new ControladorUtilizador();
        cReparacao = new ControladorReparacao();
        cEquipamento = new ControladorEquipamento();
        cNotificacao = new ControladorNotificacao();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuFicheiro = new JMenu("Ficheiro");
        menuConfigBD = new JMenuItem("Configurar BD");
        menuConfigBD.addActionListener(this);
        menuSair = new JMenuItem("Sair");
        menuSair.addActionListener(this);
        menuFicheiro.add(menuConfigBD);
        menuFicheiro.addSeparator();
        menuFicheiro.add(menuSair);
        JMenu menuAjuda = new JMenu("Ajuda");
        menuSobre = new JMenuItem("Sobre");
        menuSobre.addActionListener(this);
        menuAjuda.add(menuSobre);
        menuBar.add(menuFicheiro);
        menuBar.add(menuAjuda);
        setJMenuBar(menuBar);

        painelPrincipal = new JPanel(new BorderLayout());

        painelLogin = new PainelLogin(this);
        painelRegisto = new PainelRegisto(this);
        painelCliente = new PainelCliente(this);
        painelFuncionario = new PainelFuncionario(this);
        painelGestor = new PainelGestor(this);

        Container cont = getContentPane();
        cont.setLayout(new BorderLayout());
        cont.add(painelPrincipal, BorderLayout.CENTER);

        barraEstado = new JLabel(" Bem-vindo ao Sistema de Gestão de Oficina");
        barraEstado.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        cont.add(barraEstado, BorderLayout.SOUTH);

        painelAtual = painelLogin;
        painelPrincipal.add(painelAtual, BorderLayout.CENTER);
    }

    /**
     * Processa os eventos da barra de menus superior.
     *
     * @param e evento de ação gerado pelos itens do menu
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(menuConfigBD)) {
            DialogoConfigBD d = new DialogoConfigBD(this);
            d.setVisible(true);
        } else if (e.getSource().equals(menuSair)) {
            System.exit(0);
        } else if (e.getSource().equals(menuSobre)) {
            JOptionPane.showMessageDialog(this,
                    "Sistema de Gestão de Oficina\nVersão 2.0 (GUI)\nAutores: Santiago e Hugo",
                    "Sobre", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Mostra um painel pelo nome, removendo o anterior e adicionando o novo.
     * Usa remove/add seguido de revalidate() e repaint().
     *
     * @param nome identificador do painel a mostrar
     */
    public void mostrarPainel(String nome) {
        JPanel novoPainel;
        switch (nome) {
            case "login":
                novoPainel = painelLogin;
                break;
            case "registo":
                novoPainel = painelRegisto;
                break;
            case "cliente":
                novoPainel = painelCliente;
                break;
            case "funcionario":
                novoPainel = painelFuncionario;
                break;
            case "gestor":
                novoPainel = painelGestor;
                break;
            default:
                novoPainel = painelLogin;
                break;
        }
        painelPrincipal.remove(painelAtual);
        painelAtual = novoPainel;
        painelPrincipal.add(painelAtual, BorderLayout.CENTER);
        painelPrincipal.revalidate();
        painelPrincipal.repaint();
    }

    /**
     * Autentica o utilizador e redireciona para o painel correto
     * (Cliente, Funcionário ou Gestor).
     *
     * @param u o utilizador autenticado
     */
    public void autenticar(Utilizador u) {
        cReparacao.verificarReparacoesAtrasadas();

        barraEstado.setText(" Sessão: " + u.getLogin() + " | Tipo: " + u.getTipo());

        if (u.getTipo() == TipoUtilizador.CLIENTE) {
            painelCliente.setUtilizadorLogado(u);
            mostrarPainel("cliente");
        } else if (u.getTipo() == TipoUtilizador.FUNCIONARIO) {
            painelFuncionario.setUtilizadorLogado(u);
            mostrarPainel("funcionario");
        } else if (u.getTipo() == TipoUtilizador.GESTOR) {
            painelGestor.setUtilizadorLogado(u);
            mostrarPainel("gestor");
        }
    }

    /**
     * Termina a sessão do utilizador atual e volta ao painel de login.
     */
    public void terminarSessao() {
        barraEstado.setText(" Sessão terminada.");
        mostrarPainel("login");
    }

    /**
     * Devolve a instância do Controlador de Utilizadores.
     *
     * @return controlador de utilizadores
     */
    public ControladorUtilizador getControladorUtilizador() {
        return cUtilizador;
    }

    /**
     * Devolve a instância do Controlador de Reparações.
     *
     * @return controlador de reparações
     */
    public ControladorReparacao getControladorReparacao() {
        return cReparacao;
    }

    /**
     * Devolve a instância do Controlador de Equipamentos.
     *
     * @return controlador de equipamentos
     */
    public ControladorEquipamento getControladorEquipamento() {
        return cEquipamento;
    }

    /**
     * Devolve a instância do Controlador de Notificações.
     *
     * @return controlador de notificações
     */
    public ControladorNotificacao getControladorNotificacao() {
        return cNotificacao;
    }

    /**
     * Inicia a aplicação gráfica, tornando a janela principal visível.
     * Verifica a existência da base de dados e a necessidade de criar um Gestor
     * inicial.
     */
    public void iniciar() {
        setVisible(true);

        File configFile = new File("configbd.txt");
        if (!configFile.exists()) {
            JOptionPane.showMessageDialog(this,
                    "Ficheiro 'configbd.txt' não encontrado!\nConfigure a base de dados.",
                    "Configuração Necessária", JOptionPane.WARNING_MESSAGE);
            DialogoConfigBD d = new DialogoConfigBD(this);
            d.setVisible(true);
        }

        try {
            if (!getControladorUtilizador().existeGestor()) {
                JOptionPane.showMessageDialog(this,
                        "Não existe nenhum Gestor. Crie o primeiro Gestor.",
                        "Primeiro Acesso", JOptionPane.INFORMATION_MESSAGE);
                criarPrimeiroGestor(this);
            }
        } catch (Exception ex) {
        }
    }

    /**
     * Inicia o diálogo para forçar a criação do primeiro gestor do sistema.
     *
     * @param app a instância principal da aplicação para servir de pai ao diálogo
     */
    private static void criarPrimeiroGestor(Aplicacao app) {
        JTextField cNome = new JTextField();
        JTextField cEmail = new JTextField();
        JTextField cUser = new JTextField();
        JPasswordField cPass = new JPasswordField();
        Object[] campos = { "Nome:", cNome, "Email:", cEmail, "Username:", cUser, "Password:", cPass };
        int r = JOptionPane.showConfirmDialog(app, campos, "Criar Primeiro Gestor", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = app.getControladorUtilizador().registarGestor(
                    cNome.getText(), cEmail.getText(),
                    cUser.getText(), new String(cPass.getPassword()));
            if (ok)
                Utilitarios.mostrarSucesso(app, "Gestor criado com sucesso!");
            else
                Utilitarios.mostrarErro(app, "Erro ao criar gestor!");
        }
    }
}
