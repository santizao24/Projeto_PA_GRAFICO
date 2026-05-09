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
 * Gere a navegação entre painéis (login, registo, menus) usando CardLayout,
 * e mantém referências centralizadas aos controladores do sistema.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class AplicacaoGUI extends JFrame implements ActionListener {

    private ControladorUtilizador cUtilizador;
    private ControladorReparacao cReparacao;
    private ControladorEquipamento cEquipamento;
    private ControladorNotificacao cNotificacao;

    private CardLayout cardLayout;
    private JPanel painelPrincipal;
    private PainelLogin painelLogin;
    private PainelRegisto painelRegisto;
    private PainelCliente painelCliente;
    private PainelFuncionario painelFuncionario;
    private PainelGestor painelGestor;

    private JMenuItem menuConfigBD, menuSair, menuSobre;
    private JLabel barraEstado;

    /**
     * Constrói a janela principal da aplicação.
     */
    public AplicacaoGUI() {
        super("Sistema de Gestão de Oficina");
        cUtilizador = new ControladorUtilizador();
        cReparacao = new ControladorReparacao();
        cEquipamento = new ControladorEquipamento();
        cNotificacao = new ControladorNotificacao();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 650);
        setLocationRelativeTo(null);

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu menuFicheiro = new JMenu("Ficheiro");
        menuConfigBD = new JMenuItem("Configurar BD");
        menuConfigBD.setToolTipText("Configurar ligação à base de dados");
        menuConfigBD.addActionListener(this);
        menuSair = new JMenuItem("Sair");
        menuSair.setToolTipText("Encerrar a aplicação");
        menuSair.addActionListener(this);
        menuFicheiro.add(menuConfigBD);
        menuFicheiro.addSeparator();
        menuFicheiro.add(menuSair);
        JMenu menuAjuda = new JMenu("Ajuda");
        menuSobre = new JMenuItem("Sobre");
        menuSobre.setToolTipText("Informações sobre a aplicação");
        menuSobre.addActionListener(this);
        menuAjuda.add(menuSobre);
        menuBar.add(menuFicheiro);
        menuBar.add(menuAjuda);
        setJMenuBar(menuBar);

        // Painel principal com CardLayout
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        painelLogin = new PainelLogin(this);
        painelRegisto = new PainelRegisto(this);
        painelCliente = new PainelCliente(this);
        painelFuncionario = new PainelFuncionario(this);
        painelGestor = new PainelGestor(this);

        painelPrincipal.add(painelLogin, "login");
        painelPrincipal.add(painelRegisto, "registo");
        painelPrincipal.add(painelCliente, "cliente");
        painelPrincipal.add(painelFuncionario, "funcionario");
        painelPrincipal.add(painelGestor, "gestor");

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);

        // Barra de estado
        barraEstado = new JLabel(" Bem-vindo ao Sistema de Gestão de Oficina");
        barraEstado.setBorder(BorderFactory.createEtchedBorder());
        getContentPane().add(barraEstado, BorderLayout.SOUTH);

        cardLayout.show(painelPrincipal, "login");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menuConfigBD) {
            DialogoConfigBD d = new DialogoConfigBD(this);
            d.setVisible(true);
        } else if (e.getSource() == menuSair) {
            System.exit(0);
        } else if (e.getSource() == menuSobre) {
            JOptionPane.showMessageDialog(this,
                    "Sistema de Gestão de Oficina\nVersão 2.0 (GUI)\nAutores: Santiago e Hugo",
                    "Sobre", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** Mostra um painel pelo nome. */
    public void mostrarPainel(String nome) {
        cardLayout.show(painelPrincipal, nome);
    }

    /** Autentica o utilizador e redireciona para o painel correto. */
    public void autenticar(Utilizador u) {
        // Verificar reparações atrasadas
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

    /** Termina a sessão e volta ao login. */
    public void terminarSessao() {
        barraEstado.setText(" Sessão terminada.");
        mostrarPainel("login");
    }

    public ControladorUtilizador getControladorUtilizador() {
        return cUtilizador;
    }

    public ControladorReparacao getControladorReparacao() {
        return cReparacao;
    }

    public ControladorEquipamento getControladorEquipamento() {
        return cEquipamento;
    }

    public ControladorNotificacao getControladorNotificacao() {
        return cNotificacao;
    }

    /**
     * Ponto de entrada da aplicação gráfica.
     * Verifica se o ficheiro configbd.txt existe e se há gestores no sistema.
     */
    public static void main(String[] args) {
        // Aplicar FlatLaf para aspeto visual moderno (recomendado pelo professor)
        FlatDarculaLaf.setup();

        SwingUtilities.invokeLater(() -> {
            AplicacaoGUI app = new AplicacaoGUI();
            app.setVisible(true);

            // Verificar configbd.txt
            File configFile = new File("configbd.txt");
            if (!configFile.exists()) {
                JOptionPane.showMessageDialog(app,
                        "Ficheiro 'configbd.txt' não encontrado!\nConfigure a base de dados.",
                        "Configuração Necessária", JOptionPane.WARNING_MESSAGE);
                DialogoConfigBD d = new DialogoConfigBD(app);
                d.setVisible(true);
            }

            // Verificar se existe gestor
            try {
                if (!app.getControladorUtilizador().existeGestor()) {
                    JOptionPane.showMessageDialog(app,
                            "Não existe nenhum Gestor. Crie o primeiro Gestor.",
                            "Primeiro Acesso", JOptionPane.INFORMATION_MESSAGE);
                    criarPrimeiroGestor(app);
                }
            } catch (Exception ex) {
                // BD pode não estar configurada ainda
            }
        });
    }

    private static void criarPrimeiroGestor(AplicacaoGUI app) {
        JTextField cNome = new JTextField();
        JTextField cEmail = new JTextField();
        JTextField cUser = new JTextField();
        JPasswordField cPass = new JPasswordField();
        Object[] campos = { "Nome:", cNome, "Email:", cEmail, "Username:", cUser, "Password:", cPass };
        int r = JOptionPane.showConfirmDialog(app, campos, "Criar Primeiro Gestor", JOptionPane.OK_CANCEL_OPTION);
        if (r == JOptionPane.OK_OPTION) {
            boolean ok = app.getControladorUtilizador().registarGestor(
                    cNome.getText().trim(), cEmail.getText().trim(),
                    cUser.getText().trim(), new String(cPass.getPassword()));
            if (ok)
                Utilitarios.mostrarSucesso(app, "Gestor criado com sucesso!");
            else
                Utilitarios.mostrarErro(app, "Erro ao criar gestor!");
        }
    }
}
