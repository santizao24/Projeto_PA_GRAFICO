package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Utilizador;
import Enums.EstadoUtilizador;

/**
 * Painel de autenticação do sistema de gestão de oficina.
 * Permite ao utilizador inserir as credenciais (username e password)
 * para aceder ao sistema. Após autenticação, verifica notificações
 * pendentes e apresenta um alerta ao utilizador.
 *
 * @author Santiago e Hugo
 * @version 1.0
 */
public class PainelLogin extends JPanel implements ActionListener {

    private Aplicacao aplicacao;
    private JTextField campoUsername;
    private JPasswordField campoPassword;
    private JButton btnEntrar;
    private JButton btnRegistar;
    private JButton btnConfigBD;

    /**
     * Constrói o painel de login.
     *
     * @param aplicacao referência para a aplicação principal
     */
    public PainelLogin(Aplicacao aplicacao) {
        this.aplicacao = aplicacao;
        setLayout(new BorderLayout());

        JPanel painelCabecalho = new JPanel(new GridLayout(4, 1));

        JPanel painelTitulo = new JPanel(new FlowLayout());
        JLabel titulo = new JLabel("Sistema de Gestão de Oficina - Autenticação");
        painelTitulo.add(titulo);
        painelCabecalho.add(painelTitulo);

        JPanel painelUsername = new JPanel(new FlowLayout());
        painelUsername.add(new JLabel("Username:"));
        campoUsername = new JTextField(20);
        campoUsername.setToolTipText("Introduza o seu nome de utilizador");
        painelUsername.add(campoUsername);
        painelCabecalho.add(painelUsername);

        JPanel painelPassword = new JPanel(new FlowLayout());
        painelPassword.add(new JLabel("Password:"));
        campoPassword = new JPasswordField(20);
        campoPassword.setToolTipText("Introduza a sua palavra-passe");
        painelPassword.add(campoPassword);
        painelCabecalho.add(painelPassword);

        JPanel painelBotoes = new JPanel(new FlowLayout());
        btnEntrar = new JButton("Entrar");
        btnEntrar.setToolTipText("Efetuar login no sistema");
        btnEntrar.addActionListener(this);
        btnRegistar = new JButton("Registar");
        btnRegistar.setToolTipText("Criar uma nova conta no sistema");
        btnRegistar.addActionListener(this);
        btnConfigBD = new JButton("Configurar BD");
        btnConfigBD.setToolTipText("Configurar os parâmetros de acesso à base de dados");
        btnConfigBD.addActionListener(this);
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnRegistar);
        painelBotoes.add(btnConfigBD);
        painelCabecalho.add(painelBotoes);

        JPanel painelCentral = new JPanel(new FlowLayout());
        painelCentral.add(painelCabecalho);
        add(painelCentral, BorderLayout.CENTER);
    }

    /**
     * Processa os eventos dos botões Entrar, Registar e Configurar BD.
     * No caso de login bem-sucedido, verifica notificações pendentes.
     *
     * @param e evento de ação gerado
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btnEntrar)) {
            String user = campoUsername.getText();
            String pass = new String(campoPassword.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                Utilitarios.mostrarErro(this, "Preencha o username e a password!");
                return;
            }

            try {
                Utilizador utilizador = aplicacao.getControladorUtilizador().efetuarLogin(user, pass);

                if (utilizador != null) {
                    if (utilizador.getEstado() == EstadoUtilizador.ATIVO) {
                        aplicacao.getControladorUtilizador().registarInicioSessao(user);

                        int naoLidas = aplicacao.getControladorNotificacao()
                                .contarNaoLidas(utilizador.getIdUtilizador());
                        if (naoLidas > 0) {
                            JOptionPane.showMessageDialog(this,
                                    "Tens " + naoLidas + " notificação(ões) não lida(s)!",
                                    "Notificações Pendentes",
                                    JOptionPane.WARNING_MESSAGE);
                        }

                        aplicacao.autenticar(utilizador);
                        limparCampos();
                    } else if (utilizador.getEstado() == EstadoUtilizador.INATIVO) {
                        Utilitarios.mostrarInfo(this, "A tua conta encontra-se INATIVA. Contacta um gestor.");
                    } else if (utilizador.getEstado() == EstadoUtilizador.AGUARDA_REMOCAO) {
                        Utilitarios.mostrarInfo(this,
                                "A tua conta está a aguardar remoção. Contacta um gestor se queres reverter.");
                    } else if (utilizador.getEstado() == EstadoUtilizador.REMOVIDA) {
                        Utilitarios.mostrarInfo(this, "Esta conta foi removida do sistema.");
                    } else {
                        Utilitarios.mostrarInfo(this,
                                "Conta com estado desconhecido: " + utilizador.getEstado());
                    }
                } else {
                    Utilitarios.mostrarErro(this, "Credenciais inválidas.");
                }
            } catch (Exception ex) {
                Utilitarios.mostrarErro(this,
                        "Erro ao conectar à Base de Dados!\n" + ex.getMessage());
            }
        } else if (e.getSource().equals(btnRegistar)) {
            aplicacao.mostrarPainel("registo");
        } else if (e.getSource().equals(btnConfigBD)) {
            DialogoConfigBD dialogo = new DialogoConfigBD(aplicacao);
            dialogo.setVisible(true);
        }
    }

    private void limparCampos() {
        campoUsername.setText("");
        campoPassword.setText("");
    }
}
